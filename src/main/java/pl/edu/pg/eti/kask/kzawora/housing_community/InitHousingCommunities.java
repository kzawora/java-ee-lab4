package pl.edu.pg.eti.kask.kzawora.housing_community;

import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.Manager;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Address;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InitHousingCommunities {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
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
}
