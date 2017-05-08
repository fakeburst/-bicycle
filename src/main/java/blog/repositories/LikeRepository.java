package blog.repositories;

import blog.models.Likes;
import blog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Oleg on 5/8/2017.
 */
public interface LikeRepository extends JpaRepository<Likes,Integer>{


    @Query("SELECT count (DISTINCT l.author) FROM Likes l WHERE l.post= :post GROUP BY l.post")
    Integer likes(@Param("post") Post post);

}
