package fr.picom.picomspring.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import fr.picom.picomspring.service.CountryService;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryRestControllerTest {
    //TODO rewrite all test
    //add to component need to use @Autowire in test
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CountryService countryService;

    private MockMvc mockMvc;

    @Autowired
    private final static String PATH = "http://localhost:8280/api/country";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("spring")
    @Test
    public void TestGetCountriesSuccess() throws Exception{
        mockMvc.perform(get("/api/country/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void TestGetCountriesUnauthorized() throws Exception{
        mockMvc.perform(get("/api/country/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

   /* @Test
    @Order(1)
    public void TestAddingCountry() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post(PATH+"Mongolie/+58"))

             //   .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("tes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Mongolie"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneIndicative").value("+58"))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }*/
}
