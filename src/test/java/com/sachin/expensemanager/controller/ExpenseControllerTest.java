//package com.sachin.expensemanager.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//public class ExpenseControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testGetAllExpenses() throws Exception{
//        String token = login("admin@gmail.com", "12345");
//
//        mockMvc.perform(
//                        MockMvcRequestBuilders.get("/api/expenses/optimized")
//                                .header("Authorization", "Bearer " + token)
//                )
//                .andExpect(MockMvcResultMatchers.status().isOk());    }
//
//    protected String login(String email, String password) throws Exception {
//
//        MvcResult result = mockMvc.perform(post("/auth/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("""
//                                {
//                                  "email": "%s",
//                                  "password": "%s"
//                                }
//                                """.formatted(email, password)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        return objectMapper.readTree(result.getResponse().getContentAsString())
//                .get("token").asText();
//    }
//}
