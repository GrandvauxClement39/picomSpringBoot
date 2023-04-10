package fr.picom.picomspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.service.StopService;
import fr.picom.picomspring.service.impl.PiPointServiceImpl;
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

import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PiPointRestControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private final static String PATH = "http://localhost:8280/pi-point";

    private final static String ADDRESS_IP = "96.183.38.130";

    @Autowired
    private PiPointServiceImpl piPointService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void TestGetStopByAddressIp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/stop?addressIp=" + ADDRESS_IP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gare SNCF (quai 1)"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.addressIp").value(ADDRESS_IP))
                .andExpect(status().isOk());
    }

    @Test
    public void TestGetAdsByAddressIpAndTimeSlot() throws Exception {

        List<Ad> adList = piPointService.findAdByIpStopAndTimeInterval(ADDRESS_IP);

        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/ads?addressIp=" + ADDRESS_IP)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(adList.size()))
                .andExpect(status().isOk());
    }
}
