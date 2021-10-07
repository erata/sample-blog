package blog.controller;

import blog.BlogApplication;
import blog.exception.RecordNotFoundException;
import blog.service.PostService;

import blog.service.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/posts")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(BlogApplication.class);
    private final PostService postService;

    public PostController(PostService service) {
        this.postService = service;
    }

    @GetMapping
    public ResponseEntity<Page<PostListItemView>> getAllPosts(@RequestParam(value = "page", defaultValue="0", required = false)
                                                              Integer page,
                                                              @RequestParam(value = "size", defaultValue="20", required = false)
                                                              Integer size,
                                                              @RequestParam(value = "category", defaultValue = "", required = false)
                                                              String category,
                                                              @RequestParam(value = "search", defaultValue = "", required = false)
                                                              String searchText
                                                              ) {
        Page<PostListItemView> posts = postService.getAllPosts(category, searchText, page, size);
        logger.debug("getAllPosts(): Found " + posts.getContent().size() + " Posts");

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping(value ="/user/{id}")
    public ResponseEntity<Page<PostListItemView>> getAllUserPosts(@RequestParam(value = "page", defaultValue="0", required = false)
                                                                  Integer page,
                                                                  @RequestParam(value = "size", defaultValue="20", required = false)
                                                                  Integer size)
    {
        Page<PostListItemView> posts = postService.getAllUserPosts( page, size);
        logger.debug("getAllPosts(): Found " + posts.getContent().size() + " Posts");

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


  /*
    @GetMapping(value ="/category/{name}", produces = "application/json")
    public ResponseEntity<Page<PostListItemView>> getCategoryPosts(@RequestParam(value = "page", defaultValue="0", required = false)
                                                                   Integer page,
                                                                   @RequestParam(value = "size", defaultValue="20", required = false)
                                                                   Integer size,
                                                                   @NotBlank
                                                                   @PathVariable("name") String name
    ) {
        Page<PostListItemView> posts = service.getCategoryPosts(StringDecoder.decodee(name), page, size);

        logger.debug("getCategoryPosts(): Found " + posts.getContent().size() + " Posts");
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @GetMapping(value ="/search", produces = "application/json")
    public ResponseEntity<Page<PostListItemView>> getSearchPosts(@RequestParam(value = "page", defaultValue="0", required = false)
                                                                 Integer page,
                                                                 @RequestParam(value = "size", defaultValue="20", required = false)
                                                                 Integer size,
                                                                 @RequestParam(value = "search")
                                                                 @NotBlank String search
    ) {
        Page<PostListItemView> posts = service.getSearchPosts(StringDecoder.decodee(search), page, size);

        logger.debug("getSearchPosts(): Found " + posts.getContent().size() + " Posts");
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }*/


    @GetMapping(value ="/{id}")
    public ResponseEntity<PostDetailsView> getPostById(@PathVariable("id") String id)
            throws RecordNotFoundException {
        PostDetailsView postDetailsView = postService.getPostById(id);

        return new ResponseEntity<>(postDetailsView, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PostDetailsView> createPost(Authentication authentication, @Valid @RequestBody PostDto post){
        PostDetailsView created = postService.createPost(authentication, post);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PostMapping(value ="/{id}/comments")
    public ResponseEntity<PostDetailsView> createPostComment(@PathVariable("id") String id,
                                                             @Valid @RequestBody CommentDto commentDto){
        PostDetailsView created = postService.createPostComment(id, commentDto);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PutMapping(value ="/{id}")
    public ResponseEntity<PostDetailsView> updatePost(@PathVariable("id") String id,
                                                      @Valid @RequestBody PostUpdateDto post)
            throws RecordNotFoundException {
      PostDetailsView updated = postService.updatePost(id, post);

      return new ResponseEntity<>(updated, HttpStatus.OK);
    }

   /* @PutMapping("/{id}/status")
    public ResponseEntity<PostDetailsView> updatePostStatus(@PathVariable("id") Integer id,
                                                            @RequestParam("isPublished") Boolean isPublished)
            throws RecordNotFoundException {
        PostDetailsView updated = postService.updatePost(id, isPublished);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }*/

    @PutMapping("/status")
    public HttpStatus updatePostsStatusesByIds(@RequestBody PostsStatusDto postsStatus)
            throws RecordNotFoundException {

        postService.updatePostsStatus(postsStatus);

        return HttpStatus.OK;
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePostById(@PathVariable("id") String id)
            throws RecordNotFoundException {
        postService.deletePostById(id);

        return HttpStatus.OK;
    }

    @DeleteMapping()
    public HttpStatus deletePostsByIds(@RequestParam("ids") String[] ids)
            throws RecordNotFoundException {
        postService.deletePosts(List.of(ids));

        return HttpStatus.OK;
    }

   /* @DeleteMapping()
    public HttpStatus deletePostsByIds(@RequestBody Map<String, Integer[]> map)
            throws RecordNotFoundException {
        postService.deletePosts(List.of(map.get("ids")));

        return HttpStatus.OK;
    }*/

}