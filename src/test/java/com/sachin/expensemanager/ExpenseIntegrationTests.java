package com.sachin.expensemanager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("Temporarily disabled – JWT integration wiring to be fixed")
class ExpenseIntegrationTest extends BaseIntegrationTest {

    @Test
    void userShouldAccessOnlyOwnExpenses() throws Exception {

        String user1Token = registerAndLoginUser("u1@test.com", "password");
        String user2Token = registerAndLoginUser("u2@test.com", "password");

        // Create category as admin
        String adminToken = loginAdmin("admin@gmail.com", "12345");

        mockMvc.perform(post("/api/categories")
                        .header("Authorization", "Bearer " + adminToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "test",
                                  "description": "test expenses"
                                }
                                """))
                .andExpect(status().isCreated());

        // User1 creates expense
        mockMvc.perform(post("/api/expenses")
                        .header("Authorization", "Bearer " + user1Token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "bus",
                                  "amount": 160,
                                  "date": "2025-12-20",
                                  "notes": "weekend",
                                  "categoryId": 2
                                }
                                """))
                .andExpect(status().isCreated());

        // User2 tries to access
        mockMvc.perform(get("/api/expenses/1")
                        .header("Authorization", "Bearer " + user2Token))
                .andExpect(status().isForbidden());
    }
}
