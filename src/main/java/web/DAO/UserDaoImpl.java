package web.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    public User findByUsername(String login){
        Query query = entityManager.createQuery("select u from User u where u.username = :login");
        query.setParameter("login", login);
        return (User) query.getSingleResult();
    }

    @Override
    @Transactional
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        entityManager.remove(getUser(id));
    }
    @Override
    @Transactional
    public List<User> getAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

}