package blog.models;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Entity
@Table(name = "users")
public class User {
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", posts=" + posts +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 30, unique = true)
    private String username;
    @Column(length = 60)
    private String password;
    @Column(length = 100)
    private String fullName;
    @Column(unique=true, nullable = false)
    private String email;
     @Column
     private boolean verified;
     @Column(unique=true, nullable = false)
     private String email_id;
    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<Post>();
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String passwordHash) {
        this.password = password;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public Set<Post> getPosts() {
        return posts;
    }
    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
    public User() {
    }
    public User(String username, String password, String fullName, String email, boolean verified, String email_id, Set<Post> posts) {
                this.username = username;
                this.password = password;
                this.fullName = fullName;
                this.email = email;
                this.verified = verified;
                this.email_id = email_id;
                this.posts = posts;
            }
    public User(String username, String fullName, String password, String email, String email_id, boolean verified) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
                this.email_id = email_id;
                this.verified = verified;
    }

    public String getEmail() {
               return email;
           }

             public void setEmail(String email) {
                this.email = email;
            }

             public boolean isVerified() {
               return verified;
            }

             public void setVerified(boolean verified) {
                this.verified = verified;
           }

             public String getEmail_id() {
               return email_id;
            }

             public void setEmail_id(String email_id) {
               this.email_id = email_id;
            }


}