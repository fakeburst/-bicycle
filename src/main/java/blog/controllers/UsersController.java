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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.UUID;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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

    @RequestMapping(value = "/users/verify", params = {"id"})
    public String verify(@RequestParam("id") String id) {
        User user = userService.findByEmail_id(id).get(0);
        user.setVerified(true);
        httpSession.setAttribute("currentUser", user);
        return "users/verify";
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
        String email_id = UUID.randomUUID().toString();
        userService.create(new User(register.getUsername(), register.getFullName() ,register.getPassword(), register.getEmail(), email_id, false));
        sendEmail(register.getEmail(), email_id);
        notifyService.addInfoMessage("Verify your account now");
        return "redirect:/";
    }

    private void sendEmail(String email,String id){
        final String username = "lolguy322@gmail.com";
        final String password = "1504Dsnrjdcmrbq!";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lolguy322@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n" + "http://localhost:8080/users/verify?id=" + id);

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/users/logout")
    public String logout(){
        httpSession.removeAttribute("currentUser");
        notifyService.addInfoMessage("Logged Out");
        return "redirect:/";
    };
}