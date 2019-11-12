package pl.edu.pg.eti.kask.kzawora.user;

import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.AccessControlException;
import java.util.List;

@Stateless
public class UserService {
    @PersistenceContext
    private EntityManager em;

    /**
     * Nasty hack. SecurityContext can not be injected when legacy domain in WildFly is used.
     * <p>
     * Using ExternalContext here causes REST and Servlet won't work.
     * <p>
     * Using REST SecurityContext causes JSF won't work.
     * <p>
     * Using SessionContext requires making this bean EJB.
     */
    @Inject
    private HttpServletRequest securityContext;

    public UserService() {
    }


    public synchronized List<User> findAllUsers() {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            if (securityContext.isUserInRole(User.Roles.ADMIN)) {
                return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();
            } else {
                String callerUsername = securityContext.getUserPrincipal().toString();
                return List.of(findUser(callerUsername));
            }
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized List<User> findAllUsers(int offset, int limit) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            if (securityContext.isUserInRole(User.Roles.ADMIN)) {
                return em.createNamedQuery(User.Queries.FIND_ALL, User.class)
                        .setFirstResult(offset)
                        .setMaxResults(limit)
                        .getResultList();
            } else {
                String callerUsername = securityContext.getUserPrincipal().toString();
                return List.of(findUser(callerUsername));
            }
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized long countUsers() {
        return em.createNamedQuery(User.Queries.COUNT, Long.class).getSingleResult();
    }

    public synchronized User findUser(int id) {
        String callerUsername = securityContext.getUserPrincipal().toString();
        if (securityContext.isUserInRole(User.Roles.USER)) {
            if (findUser(callerUsername).getId() == id || securityContext.isUserInRole(User.Roles.ADMIN))
                return em.find(User.class, id);
            else
                throw new AccessControlException("Access denied");
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized User findUser(String login) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(User.Queries.FIND_BY_NAME, User.class).setParameter("login", login).getSingleResult();
        } else {
            throw new AccessControlException("Access denied");
        }
    }


    @Transactional
    public synchronized void saveUser(User user) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            String callerUsername = securityContext.getUserPrincipal().toString();
            if (user.getId() == null) {
                if (securityContext.isUserInRole(User.Roles.ADMIN)) {
                    em.persist(user);
                } else {
                    throw new AccessControlException("Access denied");
                }
            } else {
                if (callerUsername.equals(user.getLogin())) {
                    User u = findUser(callerUsername);
                    if (u.getRoles().containsAll(user.getRoles()))
                        em.merge(user);
                    else
                        throw new AccessControlException("Access denied");
                } else if (securityContext.isUserInRole(User.Roles.ADMIN)) {
                    em.merge(user);
                } else {
                    throw new AccessControlException("Access denied");
                }
            }
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    @Transactional
    public void removeUser(User user) {
        String callerUsername = securityContext.getUserPrincipal().toString();
        if (securityContext.isUserInRole(User.Roles.ADMIN) && !callerUsername.equals(user.getLogin())) {
            em.remove(em.merge(user));
        } else {
            throw new AccessControlException("Access denied");
        }
    }
}
