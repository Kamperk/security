package web.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.DAO.UserDao;
import web.model.Role;
import web.model.User;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void editUser(User user) {
        userDao.editUser(user);
    }

    @Override
    public void saveUser(User user) {
        String crypto = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(crypto);
        user.setRoles(Collections.singleton(new Role(2L, "USER")));
        userDao.saveUser(user);
    }

    @Override
    public User findByUsername(String login) {
        return userDao.findByUsername(login);
    }

    @Override
    public void removeUser(long id ) {
        userDao.removeUser(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException(String.format("Пользователь с именем %s не найден", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

}