package blog.controllers;

import blog.models.Likes;
import blog.models.Post;
import blog.models.User;
import blog.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import blog.services.PostService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;
    @RequestMapping("/")
    public String index(Model model) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        List<Post> latest5Posts = postService.findLatest5();
        model.addAttribute("latest5posts", latest5Posts);
        List<Post> latest3Posts = latest5Posts.stream()
                .limit(3).collect(Collectors.toList());
        model.addAttribute("latest3posts", latest3Posts);

        List<Post> postList = postRepository.test();

        model.addAttribute("postList" , postList);
        System.out.print(postList.get(0).getBody()+"---------------------------------------------------");
        System.out.print(postList.get(0).getRoute()+"---------------------------------------------------");
        if(currentUser != null)
            model.addAttribute("currentUser", currentUser.getUsername());
        else
            model.addAttribute("currentUser", "");
        return "index";
    }
}