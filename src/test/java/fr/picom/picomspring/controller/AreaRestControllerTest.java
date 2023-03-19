package fr.picom.picomspring.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AreaRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String accessToken;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {

        MvcResult result = mockMvc.perform(post("/auth/login")
                .content("{\"email\": \"admin@admin.com\", \"password\": \"Admin123\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        Cookie cookie = result.getResponse().getCookie("picom");
        System.out.println(" ================== COOKIE RECEIVED --> "+cookie);
        JsonNode jsonNode = objectMapper.readTree(responseContent);
      //  accessToken = jsonNode.get("accessToken").asText();
        accessToken = cookie.getValue();
    }
    @Test
    public void TestGetAllArea() throws Exception{
        mockMvc.perform(get("/api/area/")
                        .header("Authorization", "Bearer ".concat(accessToken))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Centre-ville")))
                .andExpect(jsonPath("$[0].stopList[0].name", is("centre-ville-1")));
    }

    @Test
    public void TestGetAreaById() throws Exception{

        mockMvc.perform(get("/api/area/1").header("Authorization", "Bearer ".concat(accessToken))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
