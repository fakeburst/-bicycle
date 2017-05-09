package blog.models;

import javax.persistence.*;

/**
 * Created by Oleg on 5/8/2017.
 */
@Entity
@Table(name = "likes")
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Post post;

    @OneToOne
    private User author;



    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    public Likes(Post post , User user){
        this.post= post;
        this.author = user;

    }



    public Likes(){}
}
