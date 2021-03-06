package blog.services;

import blog.models.User;
import blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@Primary
public class UserServiceJpa implements UserService {
    @Autowired
    private UserRepository userRepo;
    @Override
    public List<User> findAll() {
        return this.userRepo.findAll();
    }
    @Override
    public User findById(Long id) {
        return this.userRepo.findOne(id);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepo.findByUsername(username);
    }

    @Override
    public User create(User user) {
        return this.userRepo.save(user);
    }

    @Override
     public List<User> findByEmail_id(String email_id) {
        return this.userRepo.findByEmail_id(email_id);
            }
    @Override
    public User edit(User user) {
        return this.userRepo.save(user);
    }
    @Override
    public void deleteById(Long id) {
        this.userRepo.delete(id);
    }
}
