package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    private static final String URL = "/users";

    @Test
    @DisplayName("Calling /users should return userlistpage and the model should contain a collection of users")
    void listPage() throws Exception {
        var users = List.of(
                new User("Henk", "henk@email.com", "wachtwoord123"),
                new User("Timo", "timo@email.nl", "zomer2020")
        );
        Mockito.when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name(UserController.USER_LISTPAGE))
                .andExpect(model().attribute("users", users))
                .andDo(print());
    }

    @Test
    @DisplayName("Calling /users/1 should return usereditpage and the model should have that one user")
    void editPage() throws Exception {
        var barry = new User("Barry", "barry@email.nl", "qwerty");
        Mockito.when(userService.getById(1L)).thenReturn(barry);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(view().name(UserController.USER_EDITPAGE))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", barry))
                .andDo(print());
    }

    @Test
    void handleSave() throws Exception {
        mockMvc.perform(post(URL).content("{}"))
                .andExpect(status().isOk())
                .andExpect(view().name(UserController.USER_EDITPAGE))
                .andExpect(model().attributeExists("user"));
    }
}