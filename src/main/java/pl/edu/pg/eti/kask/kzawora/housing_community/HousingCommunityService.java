package pl.edu.pg.eti.kask.kzawora.housing_community;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.Manager;
import pl.edu.pg.eti.kask.kzawora.real_estate.RealEstateService;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Address;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class HousingCommunityService {
    @PersistenceContext
    private EntityManager em;

    @Getter
    @Setter
    @Inject
    private RealEstateService realEstateService;

    public HousingCommunityService() {
    }

    @Transactional
    public void init() {
        List<HousingCommunity> housingCommunities = new ArrayList<>();
        List<Manager> managers = new ArrayList<>();
        managers.add(new Manager("Mieszkaniex", "mieszkaniex@mieszkaniex.com", "123456789"));
        managers.add(new Manager("Zwieszkaniex", "zwieszkaniex@zwieszkaniex.com", "012345678"));
        managers.add(new Manager("Poważny zarządca", "powazny@zarzadca.aniolki.pl", "114545367"));
        housingCommunities.add(new HousingCommunity("WM Myśliwska 24", new Address("Myśliwska 24 A B C D E", "80-126", "Gdańsk"), "123456789"));
        housingCommunities.add(new HousingCommunity("WM Myśliwska 26", new Address("Myśliwska 26 A B C D", "80-126", "Gdańsk"), "123456780"));
        housingCommunities.add(new HousingCommunity("WM Jabłoniowa 18", new Address("Jabłoniowa 18 A B C", "80-204", "Gdańsk"), "123456781"));
        housingCommunities.add(new HousingCommunity("WM Jabłoniowa 20", new Address("Jabłoniowa 20 A B C D E", "80-204", "Gdańsk"), "123456781"));
        for (Manager m : managers) {
            em.persist(m);
        }
        for (HousingCommunity hc : housingCommunities) {
            em.persist(hc);
            hc.setManager(managers.get(0));
        }
    }

    public synchronized List<Manager> findAllManagers() {
        return em.createNamedQuery(Manager.Queries.FIND_ALL, Manager.class).getResultList();
    }

    public synchronized List<HousingCommunity> findAllHousingCommunities() {
        return em.createNamedQuery(HousingCommunity.Queries.FIND_ALL, HousingCommunity.class).getResultList();
    }

    public synchronized List<HousingCommunity> findAllHousingCommunities(int offset, int limit) {
        return em.createNamedQuery(HousingCommunity.Queries.FIND_ALL, HousingCommunity.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public synchronized long countHousingCommunities() {
        return em.createNamedQuery(HousingCommunity.Queries.COUNT, Long.class).getSingleResult();
    }

    public synchronized HousingCommunity findHousingCommunity(int id) {
        return em.find(HousingCommunity.class, id);
    }

    public synchronized Manager findManager(int id) {
        return em.find(Manager.class, id);
    }

    @Transactional
    public synchronized void saveHousingCommunity(HousingCommunity housingCommunity) {
        if (housingCommunity.getId() == null) {
            List<RealEstate> realEstates = housingCommunity.getRealEstates();
            housingCommunity.setRealEstates(null);
            em.persist(housingCommunity);
            housingCommunity.setRealEstates(realEstates);
        } else {
            em.merge(housingCommunity);
        }
    }

    @Transactional
    public void removeHousingCommunity(HousingCommunity housingCommunity) {
        em.remove(em.merge(housingCommunity));
    }
}
