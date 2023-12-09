package com.fmi.demo.exposition.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmi.demo.BaseTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureWebMvc
@ActiveProfiles(value = "test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ComponentScan(basePackageClasses = {KeycloakSecurityComponents.class , KeycloakSpringBootConfigResolver.class})
public class DemoResourceTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


//    @Test
//    @Sql("/sql/data.sql")
//    void methodTestExample() throws Exception{
////        var getResult = mockMvc.perform(MockMvcRequestBuilders
////                .get("/exemplu/path/test"))
////                .andExpect(status().isOk())
////                .andDo(print()).andReturn();
////        String getContent = getResult.getResponse().getContentAsString();
//        //Campaign campaign = objectMapper.readValue(getContent, Campaign.class);
//        assertThat("campaign.getDescritpion()")
//                .isEqualTo("campaign.getDescritpion()");    }


}
