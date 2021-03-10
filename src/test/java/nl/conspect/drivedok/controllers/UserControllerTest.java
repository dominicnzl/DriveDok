package nl.conspect.drivedok.controllers;

import nl.conspect.drivedok.model.User;
import nl.conspect.drivedok.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Value("${user.list.page}")
    private String userListpage;

    @Value("${user.edit.page}")
    private String userEditpage;

    @Test
    @DisplayName("Calling /users should return userlistpage and the model should contain a collection of users")
    void listPage() throws Exception {
        var users = List.of(
                new User("Henk", "henk@email.com", "wachtwoord123"),
                new User("Timo", "timo@email.nl", "zomer2020")
        );
        when(userService.findAll()).thenReturn(users);
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(view().name(userListpage))
                .andExpect(model().attribute("users", users));
    }

    @Test
    @DisplayName("Calling /users/1 should return usereditpage and the model should have that one user")
    void editPage() throws Exception {
        var barry = new User("Barry", "barry@email.nl", "qwerty");
        when(userService.getById(1L)).thenReturn(barry);
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(view().name(userEditpage))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", barry));
    }

    @Test
    @DisplayName("Calling users/new (when opening a new template) should return the user-editpage")
    void handleNew() throws Exception {
        mockMvc.perform(get("/users/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(userEditpage))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @DisplayName("Webuser should return to the listpage after calling delete")
    void handleDelete() throws Exception {
        when(userService.findById(12L)).thenReturn(Optional.of(new User()));
        mockMvc.perform(delete("/users/12"))
                .andExpect(status().isOk())
                .andExpect(view().name(userListpage))
                .andExpect(model().attributeExists("users"));
    }

    @Test
    void handleSave() throws Exception {
        mockMvc.perform(post(URL).content("{}"))
                .andExpect(status().isOk())
                .andExpect(view().name(userEditpage))
                .andExpect(model().attributeExists("user"));
    }
}
