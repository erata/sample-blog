package blog.service;

import blog.exception.RecordNotFoundException;
import blog.model.*;
import blog.repository.CategoryRepository;
import blog.repository.PostRepository;
import blog.repository.UserRepository;
import blog.service.dto.*;
import blog.util.NullAwareBeanUtilsBean;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;

    }

    @Transactional(readOnly = true)
    public Page<PostListItemView> getAllPosts(String category, String searchText, Integer page, Integer size)
    {
        Pageable paging = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Post> pageablePostList = null;

        if(searchText.isBlank() && category.isBlank()) {
            pageablePostList = postRepository.findAllByIsPublishedTrue(paging);
        } else if(!searchText.isBlank()){
            pageablePostList = postRepository.findByIsPublishedTrueAndTitleContainingIgnoreCase(searchText.trim(), paging);
        } else if(!category.isBlank()){
            pageablePostList = postRepository.findByIsPublishedTrueAndCategories_NameIgnoreCase(category, paging);
        } else {
            pageablePostList = postRepository.findByIsPublishedTrueAndCategories_NameIgnoreCaseAndTitleContainingIgnoreCase(category, searchText, paging);
        }

        List<PostListItemView> postListItemViewList = getPostListItems(pageablePostList);

        return new PageImpl<PostListItemView>(postListItemViewList, paging, pageablePostList.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<PostListItemView> getAllUserPosts(Integer page, Integer size)
    {
        Pageable paging = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Post> pageablePostList = postRepository.findAll(paging);
        List<PostListItemView> postListItemViewList = getPostListItems(pageablePostList);

        return new PageImpl<PostListItemView>(postListItemViewList, paging, pageablePostList.getTotalElements());
    }

    /*
    @Transactional(readOnly = true)
    public Page<PostListItemView> getCategoryPosts(String category, Integer page, Integer size)
    {
        Pageable paging = PageRequest.of(page,size, Sort.by("createdAt").descending());
        Page<Post> pageablePostList = postRepository.findByCategories_NameIgnoreCase(category, paging);

        List<PostListItemView> postListItemViewList = getPostListItems(pageablePostList);

        return new PageImpl<PostListItemView>(postListItemViewList, paging, pageablePostList.getTotalElements());
    }

    @Transactional(readOnly = true)
    public Page<PostListItemView> getSearchPosts(String searchText, Integer page, Integer size)
    {
        Pageable paging = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Post> pageablePostList = postRepository.findByTitleContainingIgnoreCase(searchText, paging);

        List<PostListItemView> postListItemViewList = getPostListItems(pageablePostList);

        return new PageImpl<PostListItemView>(postListItemViewList, paging, pageablePostList.getTotalElements());
    }*/

    @Transactional(readOnly = true)
     public PostDetailsView getPostById(String id) throws RecordNotFoundException
    {
        Optional<Post> postOptional = postRepository.findById(id);

        if(postOptional.isPresent()) {
            Post post = postOptional.get();
            return getPostDetailsView(post);
        } else {
            throw new RecordNotFoundException("No post record exist for id = " + id);
        }
    }

    public PostDetailsView createPost(Authentication authentication, PostDto postDto)
    {
        Post post = new Post();
        Integer generatedId = Integer.valueOf(RandomStringUtils.random(8, false, true));
        //post.setId(generatedId);

        BeanUtils.copyProperties(postDto, post, "categories");

        Optional<User> user = userRepository.findByUsername(authentication.getName());
        post.setUser(user.get());
        addCategories(post, postDto.getCategories());

        post = postRepository.save(post);

        return getPostDetailsView(post);
    }

    public PostDetailsView createPostComment(String id, CommentDto commentDto)
    {
        Optional<Post> posts = postRepository.findById(id);

        if (posts.isPresent()) {
            Post post = posts.get();

            Comment comment = new Comment(commentDto.getName(), commentDto.getEmail(), commentDto.getContent());
            post.addComment(comment);

            post = postRepository.saveAndFlush(post);

            return getPostDetailsView(post);

        } else {
        throw new RecordNotFoundException("No post record exist for id = " + id);
        }


    }

    public <T> List<T> listDifference(List<T> first, List<T> second) {
        List<T> toReturn = new ArrayList<>(first);
        toReturn.removeAll(second);
        return toReturn;
    }

    public PostDetailsView updatePost(String id, PostUpdateDto postDto) throws RecordNotFoundException {
        Optional<Post> posts = postRepository.findById(id);

        if (posts.isPresent()) {
            Post post = posts.get();
            NullAwareBeanUtilsBean.copyNonNullAndIgnoredProperties(postDto, post, "categories");
           /* post.setTitle(postDto.getTitle());
            post.setContent(postDto.getContent());
            post.setIsPublished(postDto.getIsPublished());*/

           if(postDto.getCategories() != null) {
               post.removeAllCategories();
               addCategories(post, postDto.getCategories());
           }

            post = postRepository.save(post);

            return getPostDetailsView(post);
        } else {
            throw new RecordNotFoundException("No post record exist for id = " + id);
        }
    }

    private void addCategories(Post post, List<String> categories) {
        for(String categoryName: categories) {
            Optional<Category> categoryOptional = categoryRepository.findByName(categoryName);
            if(categoryOptional.isPresent()) {
                post.addCategory(categoryOptional.get());
            } else {
                post.addCategory(new Category(categoryName));
            }
        }
    }

   /* public PostDetailsView updatePost(Integer id, Boolean isPublished) throws RecordNotFoundException {
        Optional<Post> posts = postRepository.findById(id);

        if (posts.isPresent()) {
            Post post = posts.get();
            post.setIsPublished(isPublished);

            post = postRepository.save(post);

            return getPostDetailsView(post);
        } else {
            throw new RecordNotFoundException("No post record exist for id = " + id);
        }
    }*/

    public void deletePostById(String id) throws RecordNotFoundException
    {
        Optional<Post> post = postRepository.findById(id);

        if(post.isPresent())
        {
            postRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No post record exist for id = " + id);
        }
    }

    public void updatePostsStatus(PostsStatusDto postsStatus) throws RecordNotFoundException
    {
        int result = postRepository.updatePostStatus(postsStatus.getIds(), postsStatus.getIsPublished());
    }

    public void deletePosts(List<String> ids) throws RecordNotFoundException
    {
        postRepository.deletePostByIdIn(ids);
    }

    private List<PostListItemView> getPostListItems(Page<Post> postList) {
        List<PostListItemView> postListItemViewList = new ArrayList<>();

        postList.getContent().forEach(postItem-> {
            PostListItemView postListItemView = new PostListItemView();

            BeanUtils.copyProperties(postItem, postListItemView, "user" , "comments", "categories");
            postListItemView.setUserName(postItem.getUser().getName());
            postListItemView.setCommentCount(postItem.getComments().size());

            List<String> categoryList = postItem.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList());
            postListItemView.setCategories(categoryList);

            postListItemViewList.add(postListItemView);
        });
        return postListItemViewList;
    }

    private PostDetailsView getPostDetailsView(Post post) {
        PostDetailsView postDetailsView = new PostDetailsView();

        BeanUtils.copyProperties(post, postDetailsView, "user" , "comments", "categories");
        postDetailsView.setUsername(post.getUser().getName());

        List<CommentDto> commentDtoList = new ArrayList<>();
        post.getComments().forEach(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);

            commentDtoList.add(commentDto);
        });
        postDetailsView.setComments(commentDtoList);

        List<String> categoryList = post.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList());
        postDetailsView.setCategories(categoryList);

        return postDetailsView;
    }

}