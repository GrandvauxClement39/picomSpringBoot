package fr.picom.picomspring.initialize;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import fr.picom.picomspring.dao.*;
import fr.picom.picomspring.model.City;
import fr.picom.picomspring.model.Country;
import fr.picom.picomspring.model.TimeInterval;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Random;

@Component
@AllArgsConstructor
public class AddDataInitial implements CommandLineRunner {

    private final AdDAO adDAO;
    private final AreaDAO areaDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final RoleDAO roleDAO;
    private final StopDAO stopDAO;
    private final TimeIntervalDAO timeIntervalDAO;
    private final UserDAO userDAO;

    private static Random random = new Random();
    private static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("fr-FR"), new RandomService());
    private static Faker faker = new Faker(new Locale("fr-FR"));

    @Override
    public void run(String... args) throws Exception {
        if (timeIntervalDAO.findAll().size() < 10){
            addTimeInterval();
        }

    }

    public void addTimeInterval(){
        for (int i=6; i<20; i++){
            TimeInterval timeInterval = new TimeInterval();
            int j = i+1;
            timeInterval.setTimeSlot(i + "-" + j);
            timeInterval.setCoefMulti(1.8);
            timeIntervalDAO.save(timeInterval);
        }
    }

    public void addCountryAndCity(){
        Country france = new Country("France", "+33");
        countryDAO.save(france);
        Country suisse = new Country("Suisse", "+41");
        countryDAO.save(suisse);
        Country belgique = new Country("Belgique", "+32");
        countryDAO.save(belgique);

        City clermont = new City();
        clermont.setCountry(france);
        clermont.setName("Clermont-Ferrand");
        cityDAO.save(clermont);

        City lyon = new City();
        lyon.setCountry(france);
        lyon.setName("Lyon");
        cityDAO.save(lyon);

        City grenoble = new City();
        grenoble.setCountry(france);
        grenoble.setName("Grenoble");
        cityDAO.save(grenoble);

        City geneve = new City();
        geneve.setCountry(suisse);
        geneve.setName("Genève");
        cityDAO.save(geneve);

        City bruxelle = new City();
        bruxelle.setCountry(belgique);
        bruxelle.setName("Bruxelles");
        cityDAO.save(bruxelle);
    }
}
