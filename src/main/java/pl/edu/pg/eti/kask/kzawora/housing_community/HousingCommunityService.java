package pl.edu.pg.eti.kask.kzawora.housing_community;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.Manager;
import pl.edu.pg.eti.kask.kzawora.real_estate.RealEstateService;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;
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
public class HousingCommunityService {
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

    @Getter
    @Setter
    @Inject
    private RealEstateService realEstateService;

    public HousingCommunityService() {
    }

    public synchronized List<Manager> findAllManagers() {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(Manager.Queries.FIND_ALL, Manager.class).getResultList();
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized List<HousingCommunity> findAllHousingCommunities() {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(HousingCommunity.Queries.FIND_ALL, HousingCommunity.class).getResultList();
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized List<HousingCommunity> findAllHousingCommunities(int offset, int limit) {
        if (securityContext.isUserInRole(User.Roles.USER)) {

            return em.createNamedQuery(HousingCommunity.Queries.FIND_ALL, HousingCommunity.class)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized long countHousingCommunities() {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.createNamedQuery(HousingCommunity.Queries.COUNT, Long.class).getSingleResult();
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized HousingCommunity findHousingCommunity(int id) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.find(HousingCommunity.class, id);
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    public synchronized Manager findManager(int id) {
        if (securityContext.isUserInRole(User.Roles.USER)) {
            return em.find(Manager.class, id);
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    @Transactional
    public synchronized void saveHousingCommunity(HousingCommunity housingCommunity) {
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {

            if (housingCommunity.getId() == null) {
                List<RealEstate> realEstates = housingCommunity.getRealEstates();
                housingCommunity.setRealEstates(null);
                em.persist(housingCommunity);
                housingCommunity.setRealEstates(realEstates);
            } else {
                em.merge(housingCommunity);
            }
        } else {
            throw new AccessControlException("Access denied");
        }
    }

    @Transactional
    public void removeHousingCommunity(HousingCommunity housingCommunity) {
        if (securityContext.isUserInRole(User.Roles.ADMIN)) {
            em.remove(em.merge(housingCommunity));
        } else {
            throw new AccessControlException("Access denied");
        }
    }
}
