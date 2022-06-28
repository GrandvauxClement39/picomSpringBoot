package fr.picom.picomspring.initialize;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import fr.picom.picomspring.dao.*;
import fr.picom.picomspring.model.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private final AdAreaDao adAreaDao;

    private static Random random = new Random();
    private static FakeValuesService fakeValuesService = new FakeValuesService(new Locale("fr-FR"), new RandomService());
    private static Faker faker = new Faker(new Locale("fr-FR"));

    @Override
    public void run(String... args) throws Exception {
        if (timeIntervalDAO.findAll().size() < 10){
            addTimeInterval();
            addCountryAndCity();
            addRoleAndUser();
            addAreaAndStop();
            addAnnouncement();
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

    public void addRoleAndUser(){
        List<City> cityList = cityDAO.findAll();
        Role admin = new Role("Admin");
        roleDAO.save(admin);
        Role customer = new Role("Customer");
        roleDAO.save(customer);
        User adminUser = new User();
        adminUser.setRole(admin);
        adminUser.setCity(cityList.get(random.nextInt(cityList.size())));
        adminUser.setEmail("admin@admin.com");
        adminUser.setPassword("Admin123");
        adminUser.setFirstName("Admin");
        adminUser.setLastName("Admin");
        adminUser.setCompanyName("AdminCompany");
        adminUser.setNumSiret("15986542359428");
        adminUser.setPhoneNumber("0629168943");
        adminUser.setPostalCode("39210");
        adminUser.setRoadName("rue de l'admin");
        adminUser.setVerified(true);
        userDAO.save(adminUser);

        for (int i =0; i<20; i++){
            User user = new User();
            user.setRole(customer);
            user.setCity(cityList.get(random.nextInt(cityList.size())));
            user.setEmail(fakeValuesService.letterify("?????@gmail.com"));
            user.setPassword(faker.internet().password(4, 8));
            user.setFirstName(faker.name().firstName());
            user.setLastName(faker.name().lastName());
            user.setCompanyName(faker.company().name());
          //  System.out.println("-------------TEST--"+faker.number().randomNumber(1000000, true)+ ""+faker.number().randomNumber(1000000, true));
            user.setNumSiret("516846598425"+i);
            user.setPhoneNumber(faker.phoneNumber().phoneNumber());
            user.setPostalCode(faker.address().zipCode());
            user.setRoadName(faker.address().streetName());
            user.setVerified(true);
            userDAO.save(user);
        }
    }

    public void addAreaAndStop(){
        Area centreVille = new Area();
        centreVille.setName("Centre-ville");
        centreVille.setPrice(2.80);
        areaDAO.save(centreVille);

        Area gare = new Area();
        gare.setName("Gare");
        gare.setPrice(2.60);
        areaDAO.save(gare);

        Area ballainvilliers = new Area();
        ballainvilliers.setName("Ballainvilliers");
        ballainvilliers.setPrice(2.30);
        areaDAO.save(ballainvilliers);

        Area chamalieresMairie = new Area();
        chamalieresMairie.setName("Chamalières Mairie");
        chamalieresMairie.setPrice(2.10);
        areaDAO.save(chamalieresMairie);

        Area chataigneraie = new Area();
        chataigneraie.setName("Châtaigneraie");
        chataigneraie.setPrice(1.80);
        areaDAO.save(chataigneraie);

        for (int i=1; i<6; i++){
            Stop stopCentre = new Stop();
            stopCentre.setName("centre-ville-"+i);
            stopCentre.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
            stopCentre.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
            stopCentre.setArea(centreVille);
            stopCentre.setAdressIp(faker.internet().ipV4Address());
            stopDAO.save(stopCentre);
        }

        Stop stopGare1 = new Stop();
        stopGare1.setName("Gare SNCF (quai 1)");
        stopGare1.setArea(gare);
        stopGare1.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        stopGare1.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        stopGare1.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(stopGare1);

        Stop stopGare2 = new Stop();
        stopGare2.setName("Gare SNCF (quai 2)");
        stopGare2.setArea(gare);
        stopGare2.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        stopGare2.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        stopGare2.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(stopGare2);

        Stop stopGare3 = new Stop();
        stopGare3.setName("Gare SNCF (quai 3)");
        stopGare3.setArea(gare);
        stopGare3.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        stopGare3.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        stopGare3.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(stopGare3);

        Stop stopGare4 = new Stop();
        stopGare4.setName("Gare SNCF (quai 4)");
        stopGare4.setArea(gare);
        stopGare4.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        stopGare4.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        stopGare4.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(stopGare4);

        Stop stopGare8 = new Stop();
        stopGare8.setName("Gare SNCF (quai 8)");
        stopGare8.setArea(gare);
        stopGare8.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        stopGare8.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        stopGare8.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(stopGare8);

        Stop stopGare9 = new Stop();
        stopGare9.setName("Gare SNCF (quai 9)");
        stopGare9.setArea(gare);
        stopGare9.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        stopGare9.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        stopGare9.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(stopGare9);

        Stop esplanade = new Stop();
        esplanade.setName("Esplanade");
        esplanade.setArea(gare);
        esplanade.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        esplanade.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        esplanade.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(esplanade);

        Stop carnot = new Stop();
        carnot.setName("Carnot");
        carnot.setArea(gare);
        carnot.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        carnot.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        carnot.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(carnot);

        Stop fleury = new Stop();
        fleury.setName("Fleury");
        fleury.setArea(gare);
        fleury.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        fleury.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        fleury.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(fleury);

        Stop cartoucherie = new Stop();
        cartoucherie.setName("Cartoucherie");
        cartoucherie.setArea(gare);
        cartoucherie.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        cartoucherie.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        cartoucherie.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(cartoucherie);

        Stop vertaizon = new Stop();
        vertaizon.setName("Vertaizon");
        vertaizon.setArea(gare);
        vertaizon.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        vertaizon.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        vertaizon.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(vertaizon);

        Stop hotelVille = new Stop();
        hotelVille.setName("Hôtel de ville");
        hotelVille.setArea(ballainvilliers);
        hotelVille.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        hotelVille.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        hotelVille.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(hotelVille);

        Stop gaillard = new Stop();
        gaillard.setName("Gaillard");
        gaillard.setArea(ballainvilliers);
        gaillard.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        gaillard.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        gaillard.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(gaillard);

        Stop jaude = new Stop();
        jaude.setName("Jaude");
        jaude.setArea(ballainvilliers);
        jaude.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        jaude.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        jaude.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(jaude);

        Stop lagarlaye = new Stop();
        lagarlaye.setName("Lagarlaye");
        lagarlaye.setArea(ballainvilliers);
        lagarlaye.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        lagarlaye.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        lagarlaye.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(lagarlaye);

        Stop ballaivilliers = new Stop();
        ballaivilliers.setName("Ballaivilliers");
        ballaivilliers.setArea(ballainvilliers);
        ballaivilliers.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        ballaivilliers.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        ballaivilliers.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(ballaivilliers);

        Stop michelDeHospital = new Stop();
        michelDeHospital.setName("Michel de l Hospital");
        michelDeHospital.setArea(ballainvilliers);
        michelDeHospital.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        michelDeHospital.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        michelDeHospital.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(michelDeHospital);

        Stop plRoyale = new Stop();
        plRoyale.setName("Pl. Royale");
        plRoyale.setArea(ballainvilliers);
        plRoyale.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        plRoyale.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        plRoyale.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(plRoyale);

        Stop julesGuesde = new Stop();
        julesGuesde.setName("Jules Guesde");
        julesGuesde.setArea(chamalieresMairie);
        julesGuesde.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        julesGuesde.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        julesGuesde.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(julesGuesde);

        Stop stAndre = new Stop();
        stAndre.setName("St André");
        stAndre.setArea(chamalieresMairie);
        stAndre.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        stAndre.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        stAndre.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(stAndre);

        Stop chamalieresMairieStop = new Stop();
        chamalieresMairieStop.setName("Chamalières Mairie");
        chamalieresMairieStop.setArea(chamalieresMairie);
        chamalieresMairieStop.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        chamalieresMairieStop.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        chamalieresMairieStop.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(chamalieresMairieStop);

        Stop parcMontjoly = new Stop();
        parcMontjoly.setName("Parc Montjoly");
        parcMontjoly.setArea(chamalieresMairie);
        parcMontjoly.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        parcMontjoly.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        parcMontjoly.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(parcMontjoly);

        Stop europe = new Stop();
        europe.setName("Europe");
        europe.setArea(chamalieresMairie);
        europe.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        europe.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        europe.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(europe);

        Stop fontmaureEurope = new Stop();
        fontmaureEurope.setName("Fontmaure Europe");
        fontmaureEurope.setArea(chamalieresMairie);
        fontmaureEurope.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        fontmaureEurope.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        fontmaureEurope.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(fontmaureEurope);

        Stop teilhardDeChardin = new Stop();
        teilhardDeChardin.setName("Teilhard de Chardin");
        teilhardDeChardin.setArea(chamalieresMairie);
        teilhardDeChardin.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        teilhardDeChardin.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        teilhardDeChardin.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(teilhardDeChardin);

        Stop chataigneraieStop = new Stop();
        chataigneraieStop.setName("Châtaigneraie");
        chataigneraieStop.setArea(chataigneraie);
        chataigneraieStop.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        chataigneraieStop.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        chataigneraieStop.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(chataigneraieStop);

        Stop chaussades = new Stop();
        chaussades.setName("Chaussades");
        chaussades.setArea(chataigneraie);
        chaussades.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        chaussades.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        chaussades.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(chaussades);

        Stop ChapelleDeAgneau = new Stop();
        ChapelleDeAgneau.setName("Chapelle de l'agneau");
        ChapelleDeAgneau.setArea(chataigneraie);
        ChapelleDeAgneau.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        ChapelleDeAgneau.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        ChapelleDeAgneau.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(ChapelleDeAgneau);

        Stop lesRoches = new Stop();
        lesRoches.setName("Les Roches");
        lesRoches.setArea(chataigneraie);
        lesRoches.setLatitude(Double.parseDouble(faker.address().latitude().replace(',','.')));
        lesRoches.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));
        lesRoches.setAdressIp(faker.internet().ipV4Address());
        stopDAO.save(lesRoches);
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

        for (int i =1; i<50; i++){
            Ad ad = new Ad();
            ad.setCreatedAt(LocalDate.now());
            ad.setStartAt(LocalDate.now().plusDays(i));
            ad.setNumDaysOfDiffusion(i);
            ad.setTitle("Annonce num "+i);
            ad.setUser(userList.get(random.nextInt(userList.size())));
            if (i%2 == 0){
                ad.setImage("https://thumbor.5euros.com/unsafe/fit-in/630x354/filters:quality(90):no_upscale()/uploads/media/picture/2020-06-26/4dd0bf2f-3ab7-42c9-ab10-dd0899d11ba7.png");
            }else {
                ad.setText(faker.lorem().paragraph(5));
            }
            List<AdArea> adAreaList = new ArrayList<>();
            AdArea adArea = new AdArea();
            adArea.setAd(ad);
            adArea.setArea(areaList.get(random.nextInt(areaList.size())));
            List<TimeInterval> timeIntervalList1 = new ArrayList<>();
            timeIntervalList1.add(timeIntervalList.get(random.nextInt(timeIntervalList.size())));
            timeIntervalList1.add(timeIntervalList.get(random.nextInt(timeIntervalList.size())));
            adArea.setTimeIntervalList(timeIntervalList1);
            ad.setAdAreaList(adAreaList);
            adDAO.save(ad);
            adAreaDao.save(adArea);
        }
    }
}
