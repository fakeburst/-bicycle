package blog.controllers;

import blog.models.Post;
import blog.models.User;
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
    @RequestMapping("/")
    public String index(Model model) {
        User currentUser = (User) httpSession.getAttribute("currentUser");
        List<Post> latest5Posts = postService.findLatest5();
        model.addAttribute("latest5posts", latest5Posts);
        List<Post> latest3Posts = latest5Posts.stream()
                .limit(3).collect(Collectors.toList());
        model.addAttribute("latest3posts", latest3Posts);
        if(currentUser != null)
            model.addAttribute("currentUser", currentUser.getUsername());
        else
            model.addAttribute("currentUser", "");
        return "index";
    }
}