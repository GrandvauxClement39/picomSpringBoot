package fr.picom.picomspring.initialize;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import fr.picom.picomspring.dao.*;
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
}
