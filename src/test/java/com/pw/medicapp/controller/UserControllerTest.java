package com.pw.medicapp.controller;

import com.pw.medicapp.model.User;
import com.pw.medicapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void shouldReturnAllUsers() throws Exception {
        // Arrange
        User user1 = new User();
        user1.setFiscalCode("RSSMRA80A01H501U");
        user1.setFirstName("Mario");
        user1.setLastName("Rossi");

        User user2 = new User();
        user2.setFiscalCode("VRDLGU90B02A001F");
        user2.setFirstName("Luigi");
        user2.setLastName("Verdi");

        List<User> mockUsers = Arrays.asList(user1, user2);

        Mockito.when(userService.getAllUsers()).thenReturn(mockUsers);

        // Act & Assert
        mockMvc.perform(get("/api/user/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].fiscalCode").value("RSSMRA80A01H501U"))
                .andExpect(jsonPath("$[0].firstName").value("Mario"))
                .andExpect(jsonPath("$[1].fiscalCode").value("VRDLGU90B02A001F"))
                .andExpect(jsonPath("$[1].firstName").value("Luigi"));
    }
}
