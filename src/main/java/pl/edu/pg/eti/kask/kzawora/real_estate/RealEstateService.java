package pl.edu.pg.eti.kask.kzawora.real_estate;

import pl.edu.pg.eti.kask.kzawora.real_estate.model.Address;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Developer;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;
import pl.edu.pg.eti.kask.kzawora.user.UserService;
import pl.edu.pg.eti.kask.kzawora.user.model.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RealEstateService {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserService userService;

    public RealEstateService() {

    }

    @Transactional
    public void init() {
        Developer d1 = new Developer("ROBYG");
        Developer d2 = new Developer("Polnord");
        Developer d3 = new Developer("Dekpol");
        RealEstate re1 = new RealEstate(new Address("Myśliwska 24A/1", "80-126", "Gdańsk"), 43.90, LocalDate.of(2012, 01, 21), null, null);
        RealEstate re2 = new RealEstate(new Address("Myśliwska 24B/2", "80-126", "Gdańsk"), 36.10, LocalDate.of(2011, 05, 16), null, null);
        RealEstate re3 = new RealEstate(new Address("Myśliwska 24C/3", "80-126", "Gdańsk"), 43.70, LocalDate.of(2015, 10, 01), null, null);
        RealEstate re4 = new RealEstate(new Address("Myśliwska 24D/4", "80-126", "Gdańsk"), 55.90, LocalDate.of(2013, 11, 13), null, null);
        RealEstate re5 = new RealEstate(new Address("Myśliwska 26A/5", "80-126", "Gdańsk"), 76.50, LocalDate.of(2014, 10, 11), null, null);
        RealEstate re6 = new RealEstate(new Address("Myśliwska 26B/6", "80-126", "Gdańsk"), 36.30, LocalDate.of(2010, 12, 15), null, null);
        RealEstate re7 = new RealEstate(new Address("Myśliwska 26C/7", "80-126", "Gdańsk"), 46.90, LocalDate.of(2009, 05, 22), null, null);
        RealEstate re8 = new RealEstate(new Address("Jabłoniowa 18A/8", "80-204", "Gdańsk"), 56.10, LocalDate.of(2002, 07, 30), null, null);
        RealEstate re9 = new RealEstate(new Address("Jabłoniowa 18B/9", "80-204", "Gdańsk"), 36.10, LocalDate.of(2005, 10, 12), null, null);
        RealEstate re10 = new RealEstate(new Address("Jabłoniowa 20A/10", "80-204", "Gdańsk"), 45.90, LocalDate.of(2012, 11, 20), null, null);
        RealEstate re11 = new RealEstate(new Address("Jabłoniowa 20B/11", "80-204", "Gdańsk"), 46.30, LocalDate.of(2015, 12, 13), null, null);

        em.persist(d1);
        em.persist(d2);
        em.persist(d3);
        em.persist(re1);
        em.persist(re2);
        em.persist(re3);
        em.persist(re4);
        em.persist(re5);
        em.persist(re6);
        em.persist(re7);
/*
        re1.setDevelopers(List.of(d1));
        re2.setDevelopers(List.of(d2));
        re3.setDevelopers(List.of(d1));
        re4.setDevelopers(List.of(d2));
        re5.setDevelopers(List.of(d3));
        re6.setDevelopers(List.of(d1));
        re7.setDevelopers(List.of(d2));

 */

        List<User> users = userService.findAllUsers();
      //  re1.setUsers(users);
      //  re2.setUsers(users);
        for (User u : users) {
            u.setRealEstates(List.of(re1));
            userService.saveUser(u);
        }

    }

    public synchronized List<Developer> findAllDevelopers() {
        return em.createNamedQuery(Developer.Queries.FIND_ALL, Developer.class).getResultList();
    }

    public synchronized List<RealEstate> findAllRealEstates() {
        return em.createNamedQuery(RealEstate.Queries.FIND_ALL, RealEstate.class).getResultList();
    }

    public synchronized List<RealEstate> findAllRealEstates(int offset, int limit) {
        return em.createNamedQuery(RealEstate.Queries.FIND_ALL, RealEstate.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public synchronized long countRealEstates() {
        return em.createNamedQuery(RealEstate.Queries.COUNT, Long.class).getSingleResult();
    }


    public synchronized RealEstate findRealEstate(int id) {
        return em.find(RealEstate.class, id);
    }

    public synchronized Developer findDeveloper(int id) {
        return em.find(Developer.class, id);
    }
    @Transactional
    private synchronized void saveDeveloper(Developer developer) {
        if (developer.getId() == null) {
            em.persist(developer);
        } else {
            em.merge(developer);
        }
    }
    @Transactional
    public synchronized void saveRealEstate(RealEstate realEstate) {
        if (realEstate.getId() == null) {
            em.persist(realEstate);
        } else {
            em.merge(realEstate);
        }
    }
    @Transactional
    public void removeRealEstate(RealEstate realEstate) {
        em.remove(em.merge(realEstate));
    }

}
