package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.MockConfig.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @TestConfiguration
    static class MockConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    @BeforeEach
    void setup() {
        Mockito.reset(userService);
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        List<UserEntity> mockUsers = List.of(
                new UserEntity(1L, "John", "Wick", "1234567890123", LocalDate.of(1989, 2, 1),
                        "Manager", "Admin", LocalDate.of(2025, 1, 1), null,
                        "john.wick@example.com", "0812345678"),
                new UserEntity(2L, "Tony", "Stark", "9876543210987", LocalDate.of(1975, 5, 29),
                        "CEO", "Admin", LocalDate.of(2025, 1, 1), null,
                        "tony@stark.com", "0899999999")
        );

        Mockito.when(userService.getAllUsers()).thenReturn(mockUsers);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }
}
