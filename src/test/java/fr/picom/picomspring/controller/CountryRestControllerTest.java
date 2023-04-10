package fr.picom.picomspring.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.picom.picomspring.model.Country;
import fr.picom.picomspring.service.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CountryService countryService;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String PATH = "http://localhost:8280/api/country";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestGetCountriesSuccess() throws Exception{
        mockMvc.perform(get(PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("France"));
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestGetCountryByName() throws Exception{
        String nameCountry = "France";
        mockMvc.perform(get(PATH + "/" + nameCountry)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("France"))
                .andExpect(jsonPath("$.phoneIndicative").value("+33"));
    }

    @WithAnonymousUser
    @Test
    public void TestGetCountriesUnauthorized() throws Exception{
        mockMvc.perform(get(PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestAddingCountry() throws Exception{
        Country country = new Country("Mongolie", "+58");
        mockMvc.perform(post(PATH)
                .content(objectMapper.writeValueAsString(country))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(country.getName()))
                .andExpect(jsonPath("$.phoneIndicative").value(country.getPhoneIndicative()))
                .andExpect(status().isCreated())
                .andDo(print());
    }

}
