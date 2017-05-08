package blog.forms;

import blog.models.Comment;

import javax.validation.constraints.AssertFalse;
import java.util.Date;
import java.util.List;

/**
 * Created by Oleg on 5/7/2017.
 */
public class CreateComment {

    private String comment;

    private Date date;

    private List<Comment> commentList;

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
