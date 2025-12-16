package com.sachin.expensemanager;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CategoryIntegrationTest extends BaseIntegrationTest {

    @Test
    void userShouldGetCategoriesButNotCreate() throws Exception {

        String token = registerAndLoginUser("user1@test.com", "password");

        mockMvc.perform(get("/api/categories")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/categories")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Food",
                                  "description": "Food expenses"
                                }
                                """))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminShouldCreateCategory() throws Exception {

        String token = loginAdmin("admin@gmail.com", "12345");

        mockMvc.perform(post("/api/categories")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "test",
                                  "description": "test expenses"
                                }
                                """))
                .andExpect(status().isCreated());
    }
}
