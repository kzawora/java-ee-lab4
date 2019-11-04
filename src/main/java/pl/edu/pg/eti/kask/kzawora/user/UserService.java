package pl.edu.pg.eti.kask.kzawora.user;

import pl.edu.pg.eti.kask.kzawora.user.model.PersonalData;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager em;

    public UserService() {
    }

    @Transactional
    public void init() {
        List<User> users = new ArrayList<>();
        users.add(new User(new PersonalData("Nowak", "Jan", "1211111111"), "jan1.nowak@nowak.com", "raz1dwatrzy"));
        users.add(new User(new PersonalData("Kowalski", "Janek", "4111111111"), "jan2.nowak@nowak.com", "razdwa2trzy"));
        users.add(new User(new PersonalData("Nowakowski", "Janko", "5111111111"), "jan3.nowak@nowak.com", "razdwatrzy3"));
        users.add(new User(new PersonalData("Kowal", "Jaś", "1111111611"), "jan4.nowak@nowak.com", "raz1dwa2trzy"));
        users.add(new User(new PersonalData("Nowy", "Jasiek", "1111131111"), "jan5.nowak@nowak.com", "raz1dwa2trzy3"));
        users.add(new User(new PersonalData("Nowikowski", "Janek", "4111111111"), "jan6.nowak@nowak.com", "razdwa4trzy3"));
        users.add(new User(new PersonalData("Ryszard", "Janko", "1116111111"), "jan7.nowak@nowak.com", "razdwat4rzy"));
        users.add(new User(new PersonalData("Kowalewski", "Jaś", "1111111111"), "jan8.nowak@nowak.com", "ra4zd1w2at3rzy"));
        for (User u : users) {
            em.persist(u);
        }

    }

    public synchronized List<User> findAllUsers() {
        return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();

    }

    public synchronized List<User> findAllUsers(int offset, int limit) {
        return em.createNamedQuery(User.Queries.FIND_ALL, User.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public synchronized long countUsers() {
        return em.createNamedQuery(User.Queries.COUNT, Long.class).getSingleResult();
    }

    public synchronized User findUser(int id) {
        return em.find(User.class, id);
    }

    @Transactional
    public synchronized void saveUser(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            em.merge(user);
        }
    }

    @Transactional
    public void removeUser(User user) {
        em.remove(em.merge(user));
    }
}
