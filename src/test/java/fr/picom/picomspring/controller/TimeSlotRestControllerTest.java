package fr.picom.picomspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.picom.picomspring.model.TimeInterval;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimeSlotRestControllerTest {

    //add to component need to use @Autowire in test
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private final static String PATH = "http://localhost:8280/api/timeInterval";

    @Test
    @Order(1)
    public void TestAddingTimeSlot() throws Exception{
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.setTimeSlot("21-22");
        timeInterval.setCoefMulti(5.2);
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .content(objectMapper.writeValueAsString(timeInterval))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                //   .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("tes"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.timeSlot").value("21-22"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.coefMulti").value(5.2))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }
}
