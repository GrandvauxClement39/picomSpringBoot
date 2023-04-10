package fr.picom.picomspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.picom.picomspring.config.AuthRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAuthRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    public void TestLoginAdminUser() throws Exception {
        AuthRequest authRequest = new AuthRequest("admin@admin.com", "Admin123");
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(authRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andExpect(jsonPath("$.roles.length()", is(2)))
                        .andExpect(jsonPath("$.email", is(authRequest.getEmail())))
                        .andExpect(cookie().exists("picom"))
                        .andReturn();
    }

    @Test
    public void TestLoginCustomerUser() throws Exception {
        AuthRequest authRequest = new AuthRequest("test@test.com", "Admin123");
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(authRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.roles.length()", is(1)))
                .andExpect(jsonPath("$.email", is(authRequest.getEmail())))
                .andExpect(cookie().exists("picom"))
                .andReturn();
    }
}
