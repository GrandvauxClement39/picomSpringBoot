package fr.picom.picomspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.picom.picomspring.dto.UserDTO;
import fr.picom.picomspring.model.City;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.CityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String PATH = "http://localhost:8280/api/user";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void TestGetAllUsersByAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("admin@admin.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].companyName").value("AdminCompany"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestGetAllUsersByCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestGetUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("admin@admin.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.companyName").value("AdminCompany"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestRegister() throws Exception {
        UserDTO userDTO = new UserDTO("Test", "Test", "testeur@test.com", "Admin123",
                "0606060606", "12345678912345", "Test company","rue du test",
                "69001", "Clermont-Ferrand"
        );

        mockMvc.perform(post("/auth/register")
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn();
    }

    @Test
    public void TestRegisterWithEmailAlreadyUse() throws Exception {
        UserDTO userDTO = new UserDTO("Test", "Test", "admin@admin.com", "Admin123",
                "0606060606", "12345678912345", "Test company","rue du test",
                "69001", "Clermont-Ferrand"
        );

        mockMvc.perform(post("/auth/register")
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Cet Email est déjà utilisé"))
                .andReturn();
    }
}
