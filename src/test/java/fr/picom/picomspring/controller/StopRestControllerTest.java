package fr.picom.picomspring.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.picom.picomspring.model.Area;
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


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StopRestControllerTest {
    //TODO rewrite all test
    //add to component need to use @Autowire in test
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

    @WithMockUser("spring")
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

    @WithMockUser("spring")
    @Test
    public void TestGetStopByIdSuccess() throws Exception {
        mockMvc.perform(get("/api/stop/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("centre-ville-3")));
    }

    @WithMockUser("spring")
    @Test
    public void TestAddingStop() throws Exception{
        Area area = areaService.finById(1L);
        Stop stop = new Stop();
        stop.setName("Test add stop");
        stop.setAdressIp("127.142.157.98");
        stop.setLatitude(20.787031);
        stop.setLongitude(-12.095555);
        stop.setArea(area);
        mockMvc.perform(post("/api/stop")
                        .content(objectMapper.writeValueAsString(stop))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test add stop"))
                .andExpect(jsonPath("$.adressIp").value("127.142.157.98"))
                .andDo(print());
    }

    @WithMockUser("spring")
    @Test
    public void TestDeleteStop() throws Exception{
        Stop stop = stopService.findByName("centre-ville-3");

        mockMvc.perform(delete("/api/stop/" + stop.getId().toString())
                        .content(objectMapper.writeValueAsString(stop))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
