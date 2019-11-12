package pl.edu.pg.eti.kask.kzawora.user;

import pl.edu.pg.eti.kask.kzawora.user.model.PersonalData;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static pl.edu.pg.eti.kask.kzawora.user.HashUtils.sha256;

/**
 * CDI bean automatically launched when container starts.
 *
 * @author psysiu
 */
@ApplicationScoped
public class InitUsers {

    /**
     * Injected EntityManager connected to database specified in the persistence.xml.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * This method will automatically called when the ApplicationScoped will be initialized. Using the @PostConstruct
     * annotated method does not secure transactions.
     *
     * @param init
     */
    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        User admin = User.builder()
                .login("admin")
                .password(sha256("admin"))
                .email("admin@admin.com")
                .personalData(new PersonalData("Kowalski", "Jan", "12345678901"))
                .role(User.Roles.ADMIN)
                .role(User.Roles.USER)
                .build();
        User user = User.builder()
                .login("user")
                .password(sha256("user"))
                .personalData(new PersonalData("Kowalsky", "Janek", "12345678901"))
                .email("user@user.com")
                .role(User.Roles.USER)
                .build();

        User user1 = User.builder()
                .login("user1")
                .password(sha256("user1"))
                .personalData(new PersonalData("Kowal", "Yan", "12345678901"))
                .email("user1@user.com")
                .role(User.Roles.USER)
                .build();

        User user2 = User.builder()
                .login("user2")
                .password(sha256("user2"))
                .personalData(new PersonalData("Kovalsky", "Yanek", "12345678901"))
                .email("user2@user.com")
                .role(User.Roles.USER)
                .build();

        User user3 = User.builder()
                .login("user3")
                .password(sha256("user3"))
                .personalData(new PersonalData("Kowalskii", "Marcin", "12345678901"))
                .email("user3@user.com")
                .role(User.Roles.USER)
                .build();

        em.persist(user);
        em.persist(user1);
        em.persist(user2);
        em.persist(user3);
        em.persist(admin);
    }

}
