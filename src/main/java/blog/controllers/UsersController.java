package blog.controllers;

import blog.forms.Login;
import blog.forms.Register;
import blog.models.User;
import blog.services.MessageService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private MessageService notifyService;

    @RequestMapping("/users/login")
    public String login(Login loginForm) {
        return "users/login";
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String loginPage(@Valid Login loginForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            return "users/login";
        }

        User currentUser;
        try {
            currentUser = userService.findByUsername(loginForm.getUsername());
            if(!loginForm.getPassword().equals(currentUser.getPassword())) {
                throw new Exception();
            }
        } catch (Exception e) {
            notifyService.addErrorMessage("Invalid login!");
            return "users/login";
        }

        httpSession.setAttribute("currentUser", currentUser);
        notifyService.addInfoMessage("Login successful");
        return "redirect:/";
    }

    @RequestMapping("/users/register")
    public String register(Register register) {
        return "users/register";
    }

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public String registerPage(@Valid Register register, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            return null;
        }
        userService.create(new User(register.getUsername(), register.getFullName() ,register.getPassword()));
        notifyService.addInfoMessage("Successful registration");
        return "redirect:/";
    }

    @RequestMapping("/users/logout")
    public String logout(){
        httpSession.removeAttribute("currentUser");
        notifyService.addInfoMessage("Logged Out");
        return "redirect:/";
    };
}