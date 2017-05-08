package blog.controllers;

<<<<<<< HEAD
import blog.forms.CreatePost;
import blog.forms.EditPost;
import blog.models.Post;
import blog.models.User;
=======
import blog.forms.CreateComment;
import blog.forms.CreatePost;
import blog.forms.EditPost;
import blog.forms.LikesCount;
import blog.models.Likes;
import blog.models.Post;
import blog.models.Comment;
import blog.models.User;
import blog.repositories.CommentRepository;
import blog.repositories.LikeRepository;
>>>>>>> Likes and comments
import blog.services.MessageService;
import blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.ModelAttribute;
=======
>>>>>>> Likes and comments
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
<<<<<<< HEAD
=======
import java.util.Date;
>>>>>>> Likes and comments
import java.util.List;

@Controller
public class PostsController {
    @Autowired
    private PostService postService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private MessageService notifyService;

<<<<<<< HEAD
    @RequestMapping("/posts/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        Post post = postService.findById(id);
=======
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;


    @RequestMapping("/posts/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        CreateComment createComment = new CreateComment();

        User currentUser = (User) httpSession.getAttribute("currentUser");


        Post post = postService.findById(id);
        List<Comment> commentList = commentRepository.findAllByPostId(id);
>>>>>>> Likes and comments
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
<<<<<<< HEAD
=======
        model.addAttribute("commentList",commentList);
        model.addAttribute("comment",createComment);
>>>>>>> Likes and comments
        model.addAttribute("post", post);
        if(currentUser != null)
            model.addAttribute("currentUser", currentUser.getUsername());
        else
            model.addAttribute("currentUser", "");
        return "posts/view";
    }

<<<<<<< HEAD
=======


>>>>>>> Likes and comments
    @RequestMapping("/posts/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Model model, EditPost editPost) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        Post post = postService.findById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        System.out.println(post.getAuthor().getUsername());
        System.out.println(currentUser.getUsername());
        if (!post.getAuthor().getUsername().equals(currentUser.getUsername())) {
            notifyService.addErrorMessage("Forbidden");
            return "redirect:/";
        }
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @RequestMapping("/posts/delete/{id}")
    public String deletePost(@PathVariable("id") Long id, Model model) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        Post post = postService.findById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        if (!post.getAuthor().getUsername().equals(currentUser.getUsername())) {
            notifyService.addErrorMessage("Forbidden");
            return "redirect:/";
        }
        postService.deleteById(id);
        notifyService.addInfoMessage("Successfully deleted");
        return "redirect:/";
    }

    @RequestMapping(value = "/posts/edit/{id}", method = RequestMethod.PUT)
    public String saveEdit(@PathVariable Long id, @Valid EditPost editPost) {
        System.out.println(editPost.getTitle());
        Post post = postService.findById(id);
        post.setTitle(editPost.getTitle());
        post.setBody(editPost.getBody());
        postService.edit(post);
        notifyService.addInfoMessage("Successfully edited");
        return "redirect:/";
    }
<<<<<<< HEAD
=======
    @RequestMapping(value = "/posts/view/{id}" , method = RequestMethod.POST)
    public String addComment(@PathVariable Long id, CreateComment createComment){
        Comment comment = new Comment();
        Post post = postService.findById(id);
        comment.setComment(createComment.getComment());
        comment.setUser((User) httpSession.getAttribute("currentUser"));
        comment.setPost(post);
        Date date = new Date();
        comment.setDate(date);
        commentRepository.save(comment);
        return "redirect:/";
    }


>>>>>>> Likes and comments

    @RequestMapping("/posts/create")
    public String createPost(CreatePost createPost) {
        return "posts/create";
    }

    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
    public String loginPage(@Valid CreatePost createPost, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            return "posts/create";
        }

        Post newPost = new Post();
        newPost.setTitle(createPost.getTitle());
        newPost.setBody(createPost.getBody());
        newPost.setAuthor((User) httpSession.getAttribute("currentUser"));
        System.out.println(((User) httpSession.getAttribute("currentUser")).getId());
        postService.create(newPost);

        notifyService.addInfoMessage("Successfully created");
        return "redirect:/";
    }

<<<<<<< HEAD
=======


>>>>>>> Likes and comments
    @RequestMapping("/posts")
    public String allPosts(Model model) {
        List<Post> allPosts = postService.findAllStartingFromNew();
        System.out.println(allPosts.size());
        model.addAttribute("allPosts", allPosts);
        User currentUser = (User)httpSession.getAttribute("currentUser");
        if(currentUser != null)
            model.addAttribute("currentUser", currentUser.getUsername());
        else
            model.addAttribute("currentUser", "");
<<<<<<< HEAD
        return "posts/all";};
=======
        return "posts/all";}


    @RequestMapping(value = "/posts/like/{id}" , method = RequestMethod.GET )
    public String addLike(@PathVariable Long id, Model model){
        User user = (User) httpSession.getAttribute("currentUser");
        Post post = postService.findById(id);
        Likes like = new Likes(post,user);
        likeRepository.save(like);
        System.out.print(likeRepository.likes(post)+"-----------------------------------------------");
        LikesCount likesCount = new LikesCount(likeRepository.likes(post));

        System.out.println(likesCount.getLikes()+"LIKES:+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        model.addAttribute("likesCount",likesCount);
        return "redirect:/";
    }
>>>>>>> Likes and comments
}