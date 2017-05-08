package blog.repositories;

import blog.models.Comment;
import blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by Oleg on 5/7/2017.
 */
public interface CommentRepository extends JpaRepository <Comment,Integer> {


    /*@Query("SELECT c FROM Comment c WHERE c.post_id = :post_id")
    List<Comment> findAllCommentsById(@Param("post_id") Post post_id);*/

    List<Comment> findAllByPostId(Long id);

}
