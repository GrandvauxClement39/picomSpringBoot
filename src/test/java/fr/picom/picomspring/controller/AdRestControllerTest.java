package fr.picom.picomspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.picom.picomspring.dto.AdAreaDTO;
import fr.picom.picomspring.model.Ad;
import fr.picom.picomspring.model.TimeInterval;
import fr.picom.picomspring.service.AdService;
import fr.picom.picomspring.service.AreaService;
import fr.picom.picomspring.service.TimeIntervalService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AdRestControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdService adService;

    @Autowired
    private TimeIntervalService timeIntervalService;

    @Autowired
    private AreaService areaService;

    private final static String PATH = "http://localhost:8280/api";

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void TestGetAdsByUserId() throws Exception {
        List<Ad> adListOfUser = adService.findAllByUser(1L);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/ads/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(adListOfUser.size()))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestGetAdById() throws Exception {
        Ad ad = adService.findById(1L);
        mockMvc.perform(MockMvcRequestBuilders.get(PATH + "/ad/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(ad.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numDaysOfDiffusion").value(ad.getNumDaysOfDiffusion()))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestCreateAdWithoutFile() throws Exception {

        String title = "Test Title";
        Long userId = 1L;
        String text = "Test Text";
        String localDate = "2023-04-05";
        Integer numDaysOfDiffusion = 5;

        String adAreaDTOString = "[{\"areaId\":1,\"timeIntervalIdList\":[2,5]}, {\"areaId\":2,\"timeIntervalIdList\":[4,8,9]}]";
        mockMvc.perform(MockMvcRequestBuilders.multipart(PATH + "/ad")
                        .param("title", title)
                        .param("userId", String.valueOf(userId))
                        .param("text", text)
                        .param("startAt", localDate)
                        .param("numDaysOfDiffusion", String.valueOf(numDaysOfDiffusion))
                        .param("adAreaDTOList", adAreaDTOString))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numDaysOfDiffusion").value(numDaysOfDiffusion))
                .andExpect(status().isCreated());
    }

    @WithMockUser(roles = "CUSTOMER")
    @Test
    public void TestCreateAdWithFile() throws Exception {
        List<TimeInterval> timeIntervalList = timeIntervalService.findAll();
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg",
                MediaType.IMAGE_JPEG_VALUE, "Hello, World!".getBytes());
        String title = "Test Title with file";
        Long userId = 1L;
        String localDate = "2023-04-05";
        Integer numDaysOfDiffusion = 5;

        List<Long> timeIntervalIdList = new ArrayList<>();
        Long idFirstTimeIntervalSelected = timeIntervalList.get(3).getId();
        timeIntervalIdList.add(idFirstTimeIntervalSelected);
        timeIntervalIdList.add(timeIntervalList.get(12).getId());
        Long idFirstAreaSelected = areaService.findById(2L).getId();
        AdAreaDTO adAreaDTOFirst = new AdAreaDTO(idFirstAreaSelected, timeIntervalIdList);
        AdAreaDTO adAreaDTOSecond = new AdAreaDTO(areaService.findById(1L).getId(), timeIntervalIdList);

        List<AdAreaDTO> adAreaDTOList = new ArrayList<>();
        adAreaDTOList.add(adAreaDTOFirst);
        adAreaDTOList.add(adAreaDTOSecond);

        String adAreaDTOString = objectMapper.writeValueAsString(adAreaDTOList);

        mockMvc.perform(MockMvcRequestBuilders.multipart(PATH + "/ad")
                        .file(file)
                        .param("title", title)
                        .param("userId", String.valueOf(userId))
                        .param("startAt", localDate)
                        .param("numDaysOfDiffusion", String.valueOf(numDaysOfDiffusion))
                        .param("adAreaDTOList", adAreaDTOString))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numDaysOfDiffusion").value(numDaysOfDiffusion))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adAreaList[0].area.id").value(idFirstAreaSelected))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adAreaList[0].timeIntervalList[0].id").value(idFirstTimeIntervalSelected))
                .andExpect(status().isCreated());
    }
}
