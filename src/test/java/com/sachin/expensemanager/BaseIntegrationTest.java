package com.sachin.expensemanager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public abstract class BaseIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected TestUserFactory testUserFactory;

    protected String loginAdmin(String email, String password) throws Exception {
        testUserFactory.createAdmin(email, password);
        return loginAndGetToken(email, password);
    }

    protected String registerAndLoginUser(String email, String password) throws Exception {
        testUserFactory.createUser(email, password);
        return loginAndGetToken(email, password);
    }

    protected String loginAndGetToken(String email, String password) throws Exception {

        String loginRequestJson = """
        {
          "email": "%s",
          "password": "%s"
        }
        """.formatted(email, password);

        MvcResult result = mockMvc.perform(
                        post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginRequestJson)
                )
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();

        // Extract token from JSON
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        // Adjust path if your response structure is different
        return rootNode
                .path("data")
                .path("token")
                .asText();
    }
}
