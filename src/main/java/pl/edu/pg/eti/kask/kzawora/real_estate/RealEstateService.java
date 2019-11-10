package pl.edu.pg.eti.kask.kzawora.real_estate;

import pl.edu.pg.eti.kask.kzawora.real_estate.model.Developer;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.security.AccessControlException;
import java.util.List;

@ApplicationScoped
public class RealEstateService {
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


    public RealEstateService() {
    }


    public synchronized List<Developer> findAllDevelopers() {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(Developer.Queries.FIND_ALL, Developer.class).getResultList();
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized List<RealEstate> findAllRealEstates() {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(RealEstate.Queries.FIND_ALL, RealEstate.class).getResultList();
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized List<RealEstate> findByDeveloper(Developer d) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(RealEstate.Queries.FIND_BY_DEVELOPER, RealEstate.class).setParameter("developer", d).getResultList();
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized List<RealEstate> findAllRealEstates(int offset, int limit) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(RealEstate.Queries.FIND_ALL, RealEstate.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized long countRealEstates() {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(RealEstate.Queries.COUNT, Long.class).getSingleResult();
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized RealEstate findRealEstate(int id) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.find(RealEstate.class, id);
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized Developer findDeveloper(int id) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.find(Developer.class, id);
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    @Transactional
    private synchronized void saveDeveloper(Developer developer) {
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {
            if (developer.getId() == null) {
                em.persist(developer);
            } else {
                em.merge(developer);
            }
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    @Transactional
    public synchronized void saveRealEstate(RealEstate realEstate) {
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {
            if (realEstate.getId() == null) {
                em.persist(realEstate);
            } else {
                em.merge(realEstate);
            }
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    @Transactional
    public void removeRealEstate(RealEstate realEstate) {
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {
            em.remove(em.merge(realEstate));
        } else {
            throw new AccessControlException("Access denied");
        }
    }
}
