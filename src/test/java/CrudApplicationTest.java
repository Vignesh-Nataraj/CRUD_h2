import com.example.crud_h2.CrudH2Application;
import com.example.crud_h2.model.User;
import com.example.crud_h2.repository.UserRepository;
import com.example.crud_h2.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = CrudH2Application.class)
public class CrudApplicationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private Date date1;
    private Date date2;
    private Date date3;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date1 = dateFormat.parse("1990-01-01");
        date2 = dateFormat.parse("1992-02-02");
        date3 = dateFormat.parse("1994-03-03");
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User(date1, "Jhonny", "John Doe", 1L, "john.doe@example.com");
        User user2 = new User(date2, "Jhonny", "Jane Smith", 2L, "john.doe@example.com");
        List<User> users = Arrays.asList(user1, user2);

        Mockito.when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = new User(date3, "Jhonny", "John Doe", 3L, "john.doe@example.com");

        Mockito.when(userService.saveUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/saveUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testGetUserById() throws Exception {
        User user = new User(date3, "Jhonny", "John Doe", 3L, "john.doe@example.com");

        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        User user = new User(date1, "Jhonny", "John Doe", 2L, "john.doe@example.com");

        Mockito.when(userService.updateUser(Mockito.eq(1L), Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(put("/users/updateUser/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testPatchUser() throws Exception {
        User user = new User(date3, "Jhonny", "John Doe", 3L, "john.doe@example.com");

        Mockito.when(userService.patchUser(Mockito.eq(1L), Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(patch("/users/patchUser/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void testDeleteUserById() throws Exception {
        Mockito.doNothing().when(userService).deleteUserById(1L);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));
    }

    @Test
    public void testGetFilteredUser() throws Exception {
        User user1 = new User(date3, "Jhonny", "John Doe", 2L, "john.doe@example.com");
        List<User> users = Arrays.asList(user1);

        Mockito.when(userService.getFilteredUsers()).thenReturn(users);

        mockMvc.perform(get("/users/getFilteredUser"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

}