package blog.repository;

import blog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post, String> {
    Page<Post> findAllByIsPublishedTrue(Pageable pageable);
    // Category based posts
    Page<Post> findByIsPublishedTrueAndCategories_NameIgnoreCase(String category, Pageable pageable);

    // Search term based posts
    Page<Post> findByIsPublishedTrueAndTitleContainingIgnoreCase(String title, Pageable pageable);

    // Category and Search term based posts
    Page<Post> findByIsPublishedTrueAndCategories_NameIgnoreCaseAndTitleContainingIgnoreCase(String category, String title, Pageable pageable);

    void deletePostByIdIn(List<String> ids);

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.isPublished = :status WHERE p.id in :ids")
    int updatePostStatus(@Param("ids") List<String> ids, @Param("status") boolean status);

    /*@Modifying
    @Query("update Post p set p.isPublished= :isPublished where p.id in :ids")
    @Transactional
    void updatePostsStatus(@Param("ids") List<Integer> ids, @Param("isPublished") Boolean status);*/
}