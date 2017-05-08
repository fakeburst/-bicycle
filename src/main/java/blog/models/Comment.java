package blog.models;
import blog.models.Post;
import blog.models.User;

import javafx.geometry.Pos;
import org.hibernate.annotations.Type;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;



    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public Post getPost() {
        return post;
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPost(Post postId) {
        this.post = postId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment(Long id, String comment, User author, Post post ){
        this.id = id;
        this.comment = comment;
        this.user = author;
        this.post = post;
    }
    public Comment(){}
}