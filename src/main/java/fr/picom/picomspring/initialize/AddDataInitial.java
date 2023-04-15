package fr.picom.picomspring.initialize;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import fr.picom.picomspring.dao.*;
import fr.picom.picomspring.model.*;
import fr.picom.picomspring.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

@Component
@RequiredArgsConstructor
public class AddDataInitial implements CommandLineRunner {

    private final AdDAO adDAO;
    private final AreaDAO areaDAO;
    private final CityDAO cityDAO;
    private final CountryDAO countryDAO;
    private final RoleDAO roleDAO;
    private final StopDAO stopDAO;
    private final TimeIntervalDAO timeIntervalDAO;
    private final UserDAO userDAO;
    private final AdAreaDao adAreaDao;

    private final Environment environment;

    private final FilesStorageService filesStorageService;

    private static Random random = new Random();
    private static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("fr-FR"), new RandomService());
    private static Faker faker = new Faker(new Locale("fr-FR"));

    @Value("${app.adminUser.email}")
    private String adminEmail;

    @Value("${app.adminUser.password}")
    private String adminPassword;

    @Value("${app.testCustomer.email}")
    private String customerEmail;

    @Value("${app.testCustomer.password}")
    private String customerPassword;

    @PostConstruct
    private void init() {
        // Initialiser les champs finals à partir des valeurs injectées par @Value
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.customerEmail = customerEmail;
        this.customerPassword = customerPassword;
    }

    // Méthode utilitaire pour vérifier si le profil "production" est actif
    private boolean isProductionProfileActive() {
        String[] profiles = environment.getActiveProfiles();
        for (String profile : profiles) {
            if ("production".equalsIgnoreCase(profile)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void run(String... args) throws Exception {
        if (isProductionProfileActive()){
            if (timeIntervalDAO.findAll().size() < 10){
                addTimeInterval();
                addCountryAndCity();
                addRoleAndUser(true);
                addAreaAndStop();
            }
        } else {
            if (timeIntervalDAO.findAll().size() < 10){
                addTimeInterval();
                addCountryAndCity();
                addRoleAndUser(false);
                addAreaAndStop();
                addAnnouncement();
            }
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

    public void addRoleAndUser(boolean isProductionMode){
        List<City> cityList = cityDAO.findAll();
        Role admin = new Role(ERole.ROLE_ADMIN);
        roleDAO.save(admin);
        Role customer = new Role(ERole.ROLE_CUSTOMER);
        roleDAO.save(customer);
        Set<Role> rolesSetAdmin = new HashSet<>();
        rolesSetAdmin.add(admin);
        rolesSetAdmin.add(customer);
        Set<Role> rolesSetCustomer = new HashSet<>();
        rolesSetCustomer.add(customer);

        User adminUser = new User();
        adminUser.setRoles(rolesSetAdmin);
        adminUser.setCity(cityList.get(random.nextInt(cityList.size())));
        adminUser.setEmail(adminEmail);
        adminUser.setPassword(new BCryptPasswordEncoder().encode(adminPassword));
        adminUser.setFirstName("Admin");
        adminUser.setLastName("Admin");
        adminUser.setCompanyName("AdminCompany");
        adminUser.setNumSiret("15986542359428");
        adminUser.setPhoneNumber("0629168943");
        adminUser.setPostalCode("39210");
        adminUser.setRoadName("rue de l'admin");
        adminUser.setVerified(true);
        userDAO.save(adminUser);

        if (!isProductionMode){
            User testCustomer = new User();
            testCustomer.setRoles(rolesSetCustomer);
            testCustomer.setCity(cityList.get(random.nextInt(cityList.size())));
            testCustomer.setEmail(customerEmail);
            testCustomer.setPassword(new BCryptPasswordEncoder().encode(customerPassword));
            testCustomer.setFirstName("Customer");
            testCustomer.setLastName("Test");
            testCustomer.setCompanyName("AdminCompany");
            testCustomer.setNumSiret("15986542359428");
            testCustomer.setPhoneNumber("0629168943");
            testCustomer.setPostalCode("39210");
            testCustomer.setRoadName("rue de l'admin");
            testCustomer.setVerified(true);
            userDAO.save(testCustomer);

            for (int i =0; i<20; i++){
                User user = new User();
                user.setRoles(rolesSetCustomer);
                user.setCity(cityList.get(random.nextInt(cityList.size())));
                String email = fakeValuesService.letterify("?????@gmail.com");
                user.setEmail(email);
                String password = faker.internet().password(4, 8);
                user.setPassword(new BCryptPasswordEncoder().encode(password));
                user.setFirstName(faker.name().firstName());
                user.setLastName(faker.name().lastName());
                user.setCompanyName(faker.company().name());
                user.setNumSiret("516846598425"+i);
                user.setPhoneNumber(faker.phoneNumber().phoneNumber());
                user.setPostalCode(faker.address().zipCode());
                user.setRoadName(faker.address().streetName());
                user.setVerified(true);
                userDAO.save(user);
            }
        }

    }

    public void generateStop(Area area, String name){
        Stop stop = new Stop();
        stop.setName(name);
        stop.setArea(area);
        stop.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        stop.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        stop.setAddressIp(faker.internet().ipV4Address());
        stopDAO.save(stop);
    }

    public Area generateArea(String name, Double price){
        Area area = new Area();
        area.setName(name);
        area.setPrice(price);
        areaDAO.save(area);
        return area;
    }

    public void addAreaAndStop(){
        Area centreVille = generateArea("Centre-ville", 2.80);

        Area gare = generateArea("Gare", 2.60);

        Area ballainvilliers = generateArea("Ballainvilliers", 2.30);

        Area chamalieresMairie = generateArea("Chamalières Mairie", 2.10);

        Area chataigneraie = generateArea("Châtaigneraie", 1.80);

        for (int i=1; i<6; i++){
            generateStop(centreVille, "centre-ville-" + i);
        }

        Stop stopGare1 = new Stop();
        stopGare1.setName("Gare SNCF (quai 1)");
        stopGare1.setArea(gare);
        stopGare1.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        stopGare1.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        stopGare1.setAddressIp("96.183.38.130");
        stopDAO.save(stopGare1);

        generateStop(gare, "Gare SNCF (quai 2)");

        generateStop(gare, "Gare SNCF (quai 3)");

        generateStop(gare, "Gare SNCF (quai 4)");

        generateStop(gare, "Gare SNCF (quai 8)");

        generateStop(gare, "Gare SNCF (quai 9)");

        generateStop(gare, "Esplanade");

        generateStop(gare, "Carnot");

        generateStop(gare, "Fleury");

        generateStop(gare, "Cartoucherie");

        generateStop(gare, "Vertaizon");

        generateStop(ballainvilliers, "Hôtel de ville");

        generateStop(ballainvilliers, "Gaillard");

        generateStop(ballainvilliers, "Jaude");

        generateStop(ballainvilliers, "Lagarlaye");

        generateStop(ballainvilliers, "Ballaivilliers");

        generateStop(ballainvilliers, "Michel de l Hospital");

        generateStop(ballainvilliers, "Pl. Royale");

        generateStop(chamalieresMairie, "Jules Guesde");

        generateStop(chamalieresMairie, "St André");

        generateStop(chamalieresMairie, "Chamalières Mairie");

        generateStop(chamalieresMairie, "Parc Montjoly");

        generateStop(chamalieresMairie, "Europe");

        generateStop(chamalieresMairie, "Fontmaure Europe");

        generateStop(chamalieresMairie, "Teilhard de Chardin");

        generateStop(chataigneraie, "Châtaigneraie");

        generateStop(chataigneraie, "Chaussades");

        generateStop(chataigneraie, "Chapelle de l'agneau");

        generateStop(chataigneraie, "Les Roches");
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

    public void addAnnouncement(){
        List<TimeInterval> timeIntervalList = timeIntervalDAO.findAll();
        List<Area> areaList = areaDAO.findAll();
        List<User> userList = userDAO.findAll();
        List<String> imageList = filesStorageService.loadAll().map(path -> path.getFileName().toString()).toList();

        for (int i = 1; i < 150; i++){
            Ad ad = new Ad();
            LocalDate createdDate = LocalDate.now().minusDays(random.nextInt(7));
            ad.setCreatedAt(createdDate);
            ad.setStartAt(createdDate.plusDays(random.nextInt(10)));
            ad.setNumDaysOfDiffusion(random.nextInt(14));
            ad.setTitle("Annonce num " + i);
            ad.setUser(userList.get(random.nextInt(userList.size() - 1)));
            if (i % 2 == 0){
                ad.setImage(imageList.get(random.nextInt(imageList.size() - 1)));
            }else {
                ad.setText(faker.lorem().paragraph(10));
            }

            List<AdArea> adAreaList = new ArrayList<>();
            AdArea adArea = new AdArea();
            adArea.setAd(ad);
            adArea.setArea(areaList.get(random.nextInt(areaList.size() - 1)));

            List<TimeInterval> timeIntervalList1 = new ArrayList<>();
            int randomIndex = random.nextInt(timeIntervalList.size() - 2);
            timeIntervalList1.add(timeIntervalList.get(randomIndex));
            timeIntervalList1.add(timeIntervalList.get(randomIndex + 1));

            adArea.setTimeIntervalList(timeIntervalList1);
            ad.setAdAreaList(adAreaList);
            adDAO.save(ad);
            adAreaDao.save(adArea);
        }
    }
}
