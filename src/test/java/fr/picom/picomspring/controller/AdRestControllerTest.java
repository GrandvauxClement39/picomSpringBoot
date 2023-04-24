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
import java.util.Random;

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
        // Define static value
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg",
                MediaType.IMAGE_JPEG_VALUE, "Hello, World!".getBytes());
        String title = "Test Title with file";
        Long userId = 1L;
        String localDate = "2023-04-05";
        Integer numDaysOfDiffusion = 5;
        // Get Time interval available for an area and select two time interval for adArea
        Random random = new Random();
        Long idAreaSelected = 1L;
        List<TimeInterval> timeListAvailableArea = timeIntervalService.getTimeIntervalAvailableForArea(idAreaSelected);
        List<Long> timeIntervalIdList = new ArrayList<>();
        int randomFirst = random.nextInt(timeListAvailableArea.size() - 2);
        Long idFirstTimeIntervalSelected = timeListAvailableArea.get(randomFirst).getId();
        timeIntervalIdList.add(idFirstTimeIntervalSelected);
        timeIntervalIdList.add(timeListAvailableArea.get(randomFirst + 1).getId());
        // Create final list of object adAreaDTO contain ad selected and their time slot
        AdAreaDTO adAreaDTOFirst = new AdAreaDTO(idAreaSelected, timeIntervalIdList);
        List<AdAreaDTO> adAreaDTOList = new ArrayList<>();
        adAreaDTOList.add(adAreaDTOFirst);

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.adAreaList[0].area.id").value(idAreaSelected))
                .andExpect(MockMvcResultMatchers.jsonPath("$.adAreaList[0].timeIntervalList[0].id").value(idFirstTimeIntervalSelected))
                .andExpect(status().isCreated());
    }
}
