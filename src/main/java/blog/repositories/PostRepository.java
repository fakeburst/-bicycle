package blog.repositories;

import blog.models.Likes;
import blog.models.Post;
import blog.models.User;
import javafx.geometry.Pos;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.author ORDER BY p.date DESC")
    List<Post> findLatest5Posts(Pageable pageable);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.author ORDER BY p.date DESC")
    List<Post> findLatest();

   /* @Query("SELECT p from Post p left JOIN fetch p.like GROUP BY p.post order by count (p.author)")
    List<Post> findTopRated();*/

   @Query("select p from Post p LEFT join FETCH p.likesList k group by k.post order by count (k.author) DESC ")
    List<Post> test();



}