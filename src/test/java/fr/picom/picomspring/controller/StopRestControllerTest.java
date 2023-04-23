package fr.picom.picomspring.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.picom.picomspring.model.Stop;
import fr.picom.picomspring.service.AreaService;
import fr.picom.picomspring.service.StopService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import fr.picom.picomspring.dto.StopDTO;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StopRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AreaService areaService;

    @Autowired
    private StopService stopService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestGetStopSuccess() throws Exception{
        mockMvc.perform(get("/api/stop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("centre-ville-1")));
    }

    @Test
    public void TestGetStopUnauthorized() throws Exception{
        mockMvc.perform(get("/api/stop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestGetStopByIdSuccess() throws Exception {
        mockMvc.perform(get("/api/stop/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("centre-ville-3")));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void TestAddingStop() throws Exception{

        StopDTO stop = new StopDTO(null, "Test add stop", 20.787031, -12.095555, 1L, "127.142.157.98");
        mockMvc.perform(post("/api/stop")
                        .content(objectMapper.writeValueAsString(stop))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test add stop"))
                .andExpect(jsonPath("$.addressIp").value("127.142.157.98"))
                .andDo(print());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void TestUpdatingStop() throws Exception{
        Stop stopToUpdate = stopService.findById(1L);

        StopDTO stop = new StopDTO(stopToUpdate.getId(), "Test update stop", 20.787031, -12.095555, stopToUpdate.getArea().getId(), "127.112.157.99");
        mockMvc.perform(post("/api/stop")
                        .content(objectMapper.writeValueAsString(stop))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(stop.getName()))
                .andExpect(jsonPath("$.addressIp").value(stop.getAddressIp()))
                .andDo(print());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void TestDeleteStop() throws Exception{
        Stop stop = stopService.findByName("centre-ville-3");

        mockMvc.perform(delete("/api/stop/" + stop.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
