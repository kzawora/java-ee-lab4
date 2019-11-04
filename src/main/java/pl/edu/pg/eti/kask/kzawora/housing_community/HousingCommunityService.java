package pl.edu.pg.eti.kask.kzawora.housing_community;

import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.HousingCommunity;
import pl.edu.pg.eti.kask.kzawora.housing_community.model.Manager;
import pl.edu.pg.eti.kask.kzawora.real_estate.RealEstateService;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.Address;
import pl.edu.pg.eti.kask.kzawora.real_estate.model.RealEstate;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class HousingCommunityService {

    private final List<HousingCommunity> housingCommunities = new ArrayList<>();

    private final List<Manager> managers = new ArrayList<>();

    @Getter
    @Setter
    @Inject
    private RealEstateService realEstateService;

    public HousingCommunityService() {
    }

    @PostConstruct
    public void init() {
        managers.add(new Manager(1, "Mieszkaniex", "mieszkaniex@mieszkaniex.com", "123456789"));
        managers.add(new Manager(2, "Zwieszkaniex", "zwieszkaniex@zwieszkaniex.com", "012345678"));
        managers.add(new Manager(3, "Poważny zarządca", "powazny@zarzadca.aniolki.pl", "114545367"));
        housingCommunities.add(new HousingCommunity(1, "WM Myśliwska 24", managers.get(0), new Address("Myśliwska 24 A B C D E", "80-126", "Gdańsk"), "123456789"));
        housingCommunities.add(new HousingCommunity(2, "WM Myśliwska 26", managers.get(1), new Address("Myśliwska 26 A B C D", "80-126", "Gdańsk"), "123456780"));
        housingCommunities.add(new HousingCommunity(3, "WM Jabłoniowa 18", managers.get(2), new Address("Jabłoniowa 18 A B C", "80-204", "Gdańsk"), "123456781"));
        housingCommunities.add(new HousingCommunity(4, "WM Jabłoniowa 20", managers.get(2), new Address("Jabłoniowa 20 A B C D E", "80-204", "Gdańsk"), "123456781"));
        List<RealEstate> realEstates = realEstateService.findAllRealEstates();
        int counter = 0;
        for (RealEstate realEstate : realEstates) {
            realEstate.setHousingCommunity(housingCommunities.get(counter % 4));
            counter++;
            realEstateService.saveRealEstate(realEstate);
        }
    }

    public synchronized List<Manager> findAllManagers() {
        return managers.stream()
                .map(Manager::new)
                .collect(Collectors.toList());
    }

    public synchronized List<HousingCommunity> findAllHousingCommunities() {
        return housingCommunities.stream()
                .map(HousingCommunity::new)
                .collect(Collectors.toList());
    }

    public synchronized List<HousingCommunity> findAllHousingCommunities(int offset, int limit) {
        return housingCommunities.stream()
                .skip(offset)
                .limit(limit)
                .map(HousingCommunity::new)
                .collect(Collectors.toList());
    }

    public synchronized int countHousingCommunities() {
        return housingCommunities.size();
    }

    public synchronized HousingCommunity findHousingCommunity(int id) {
        return housingCommunities.stream()
                .filter(housingCommunity -> housingCommunity.getId() == id)
                .findFirst().map(HousingCommunity::new)
                .orElse(null);
    }

    public synchronized Manager findManager(int id) {
        return managers.stream()
                .filter(manager -> manager.getId() == id)
                .findFirst().map(Manager::new)
                .orElse(null);
    }

    public synchronized void saveHousingCommunity(HousingCommunity housingCommunity) {
        for (RealEstate realEstate : realEstateService.findAllRealEstates()) {
            if (realEstate != null && realEstate.getHousingCommunity() != null && realEstate.getHousingCommunity().getId() == housingCommunity.getId()) {
                realEstate.setHousingCommunity(housingCommunity);
                realEstateService.saveRealEstate(realEstate);
            }
        }
        if (housingCommunity.getId() != 0) {
            housingCommunities.removeIf(housingCommunity1 -> housingCommunity1.getId() == housingCommunity.getId());
            housingCommunities.add(new HousingCommunity(housingCommunity));
        } else {
            housingCommunity.setId(housingCommunities.stream()
                    .mapToInt(HousingCommunity::getId)
                    .max()
                    .orElse(0) + 1);
            housingCommunities.add(new HousingCommunity(housingCommunity));
        }
        housingCommunities.sort(Comparator.comparingInt(HousingCommunity::getId));
    }

    public void removeHousingCommunity(HousingCommunity housingCommunity) {
        for (RealEstate realEstate : realEstateService.findAllRealEstates()) {
            if (realEstate != null && realEstate.getHousingCommunity() != null && realEstate.getHousingCommunity().getId() == housingCommunity.getId()) {
                realEstate.setHousingCommunity(null);
                realEstateService.saveRealEstate(realEstate);
            }
        }
        housingCommunities.removeIf(housingCommunity1 -> housingCommunity1.equals(housingCommunity));
    }
}
