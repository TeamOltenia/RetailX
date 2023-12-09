//package com.fmi.demo.exposition.resource;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fmi.demo.BaseTest;
//import com.fmi.demo.exposition.exceptions.ErrorDetails;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//@AutoConfigureWebMvc
//@ActiveProfiles(value = "test")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
////@ComponentScan(basePackageClasses = {KeycloakSecurityComponents.class , KeycloakSpringBootConfigResolver.class})
//public class CampaignResourceTest extends BaseTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private CampaignJPARepository campaignJPARepository;
//
//    @Autowired
//    private CampaingJPAMapper campaingJPAMapper;
//
//
//    @Test
//    @Sql("/sql/data.sql")
//    void createCampaignSucces() throws Exception {
//
//
//        Campaign campaign = getNewCampaign();
//
//        String content = objectMapper.writeValueAsString(campaign);
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/v1/campaign")
//                        .content(content)
//                        .contentType("application/json"))
//                .andExpect(status().isCreated())
//                .andDo(print()).andReturn();
//        String getContent = getResult.getResponse().getContentAsString();
//        Campaign resultCampaign = objectMapper.readValue(getContent, Campaign.class);
//
//        compareResultCampaigns(campaign, resultCampaign);
//    }
//
//
//    @Test
//    @Sql("/sql/data.sql")
//    void createCampaignFailEmtyField() throws Exception {
//
//
//        Campaign campaign = getNewCampaign();
//        campaign.setDescritpion(null);
//
//        String content = objectMapper.writeValueAsString(campaign);
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .post("/api/v1/campaign")
//                        .content(content)
//                        .contentType("application/json"))
//                .andExpect(status().isBadRequest())
//                .andDo(print()).andReturn();
//
//        ErrorDetails errorDetail = objectMapper.readValue(getResult.getResponse().getContentAsString(),ErrorDetails.class);
//        assertThat(errorDetail.getErrorKey()).isEqualTo("error_empty_field");
//    }
//    //71c48dac-18d1-4ce8-93ad-f60f511382f3
//
//    @Test
//    @Sql("/sql/data.sql")
//    void getCampaignByIdSucces() throws Exception {
//
//
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/v1/campaign/71c48dac-18d1-4ce8-93ad-f60f511382f3")
//                        .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andDo(print()).andReturn();
//        String getContent = getResult.getResponse().getContentAsString();
//        Campaign resultCampaign = objectMapper.readValue(getContent, Campaign.class);
//
//        compareResultCampaigns(campaingJPAMapper.toDomain(campaignJPARepository.findById("71c48dac-18d1-4ce8-93ad-f60f511382f3").get()),
//                resultCampaign);
//    }
//
//    @Test
//    @Sql("/sql/data.sql")
//    void getCampaignByIdFaildObjectNotFound() throws Exception {
//
//
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/v1/campaign/ ")
//                        .contentType("application/json"))
//                .andExpect(status().isBadRequest())
//                .andDo(print()).andReturn();
//        String getContent = getResult.getResponse().getContentAsString();
//        ErrorDetails errorDetails = objectMapper.readValue(getContent, ErrorDetails.class);
//        assertThat(errorDetails.getErrorKey()).isEqualTo("error_object_not_found");
//    }
//
//    @Test
//    @Sql("/sql/data.sql")
//    void getCampaignAllSucces() throws Exception {
//
//
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/v1/campaign/all")
//                        .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andDo(print()).andReturn();
//        String getContent = getResult.getResponse().getContentAsString();
//        List<Campaign> resultCampaign = objectMapper.readValue(getContent, List.class);
//
//        assertThat(resultCampaign.size()).isEqualTo(2);
//    }
//
//
//    @Test
//    @Sql("/sql/data.sql")
//    void deleteCampaignByIdSucces() throws Exception {
//
//
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/api/v1/campaign/71c48dac-18d1-4ce8-93ad-f60f511382f3")
//                        .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//        Optional<CampaignJPA> campaignJPA = campaignJPARepository.findById("71c48dac-18d1-4ce8-93ad-f60f511382f3");
//        assertThat(campaignJPA.isEmpty()).isEqualTo(true);
//    }
//
//    @Test
//    @Sql("/sql/data.sql")
//    void deleteCampaignByIdFaildObjectNotFound() throws Exception {
//
//
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/api/v1/campaign/ ")
//                        .contentType("application/json"))
//                .andExpect(status().isBadRequest())
//                .andDo(print()).andReturn();
//        String getContent = getResult.getResponse().getContentAsString();
//        ErrorDetails errorDetails = objectMapper.readValue(getContent, ErrorDetails.class);
//        assertThat(errorDetails.getErrorKey()).isEqualTo("error_object_not_found");
//    }
//
//    @Test
//    @Sql("/sql/data.sql")
//    void updateCampaignSucces() throws Exception {
//
//
//        Campaign campaign = getNewCampaign();
//        campaign.setId("71c48dac-18d1-4ce8-93ad-f60f511382f3");
//
//        String content = objectMapper.writeValueAsString(campaign);
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/v1/campaign/71c48dac-18d1-4ce8-93ad-f60f511382f3")
//                        .content(content)
//                        .contentType("application/json"))
//                .andExpect(status().isOk())
//                .andDo(print()).andReturn();
//        String getContent = getResult.getResponse().getContentAsString();
//        Campaign resultCampaign = objectMapper.readValue(getContent, Campaign.class);
//
//        compareResultCampaigns(campaign, resultCampaign);
//    }
//
//    @Test
//    @Sql("/sql/data.sql")
//    void updateCampaignFailInvalidId() throws Exception {
//
//
//        Campaign campaign = getNewCampaign();
//        campaign.setId(null);
//
//        String content = objectMapper.writeValueAsString(campaign);
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/v1/campaign/71c48dac-18d1-4ce8-93ad-f60f511382f3")
//                        .content(content)
//                        .contentType("application/json"))
//                .andExpect(status().isBadRequest())
//                .andDo(print()).andReturn();
//        String getContent = getResult.getResponse().getContentAsString();
//        ErrorDetails errorDetails = objectMapper.readValue(getContent, ErrorDetails.class);
//        assertThat(errorDetails.getErrorKey()).isEqualTo("error_invalid_id");
//    }
//
//    @Test
//    @Sql("/sql/data.sql")
//    void updateCampaignFailEmptyField() throws Exception {
//
//
//        Campaign campaign = getNewCampaign();
//        campaign.setId("71c48dac-18d1-4ce8-93ad-f60f511382f3");
//        campaign.setDescritpion(null);
//
//        String content = objectMapper.writeValueAsString(campaign);
//        var getResult = mockMvc.perform(MockMvcRequestBuilders
//                        .put("/api/v1/campaign/71c48dac-18d1-4ce8-93ad-f60f511382f3")
//                        .content(content)
//                        .contentType("application/json"))
//                .andExpect(status().isBadRequest())
//                .andDo(print()).andReturn();
//        String getContent = getResult.getResponse().getContentAsString();
//        ErrorDetails errorDetails = objectMapper.readValue(getContent, ErrorDetails.class);
//        assertThat(errorDetails.getErrorKey()).isEqualTo("error_empty_field");
//    }
//
//
//
//    private void compareResultCampaigns(Campaign campaign, Campaign resultCampaign) {
//        assertThat(resultCampaign.getCampaignGoal()).isEqualTo(campaign.getCampaignGoal());
//        assertThat(resultCampaign.getDescritpion()).isEqualTo(campaign.getDescritpion());
//        assertThat(resultCampaign.getCurrentAmmount()).isEqualTo(campaign.getCurrentAmmount());
//    }
//
//    private Campaign getNewCampaign() {
//        Campaign campaign= Campaign.builder().
//                descritpion("test")
//                .currentAmmount(155.2)
//                .campaignGoal(22.2)
//                .build();
//        return campaign;
//    }
//
//
//}
//
//
//
