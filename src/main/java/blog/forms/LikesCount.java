package blog.forms;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by Oleg on 5/8/2017.
 */
public class LikesCount {

    private Integer likes;

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
    public LikesCount(Integer likes){
        this.likes=likes;
    }
}
