package pl.edu.pg.eti.kask.kzawora.real_estate;

import pl.edu.pg.eti.kask.kzawora.real_estate.model.Address;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Developer;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class InitRealEstates {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
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

        re1.setDevelopers(List.of(d1));
        re2.setDevelopers(List.of(d2));
        re3.setDevelopers(List.of(d1));
        re4.setDevelopers(List.of(d2));
        re5.setDevelopers(List.of(d3));
        re6.setDevelopers(List.of(d1));
        re7.setDevelopers(List.of(d2));

        re1.setHousingCommunity(null);
        re2.setHousingCommunity(null);
        re3.setHousingCommunity(null);
        re4.setHousingCommunity(null);
        re5.setHousingCommunity(null);
        re6.setHousingCommunity(null);
        re7.setHousingCommunity(null);
    }

}
