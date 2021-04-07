package web.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import web.model.User;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    User getUser(long id);
    List<User> getAll();
    void editUser(User user);
    boolean saveUser(User user);
    void removeUser(long id);
}