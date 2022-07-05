package fr.picom.picomspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CountryRestControllerTest {
    //TODO rewrite all test
    //add to component need to use @Autowire in test
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private final static String PATH = "http://localhost:8280/countries/";

  /*  @Test
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
