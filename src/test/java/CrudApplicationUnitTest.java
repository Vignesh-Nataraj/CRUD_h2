import com.example.crud_h2.model.User;
import com.example.crud_h2.repository.UserRepository;
import com.example.crud_h2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CrudApplicationUnitTest {


    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;
     private Date date1;
     private Date date2;
     private User user1;
     private User user2;
    @BeforeEach

    public void setUp() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date1 = dateFormat.parse("1990-01-01");
        date2 = dateFormat.parse("1992-02-02");
        user1=new User(date1,"Test","Test",1L,"Test");
        user2=new User(date2,"Test2","Test2",2L,"Test2");

    }

    @Test
    public void testGetAllUsers(){
        List<User> users= Arrays.asList(user1,user2);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        List <User> result = userService.getAllUsers();
        assertEquals(2,result.size());
    }

    @Test
    public void testGetUserById(){
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.ofNullable((user2)));
        Optional<User> result=userService.getUserById(2L);
        assertEquals("Test2",result.get().getNickname());

    }

    @Test
    public void testSaveUser(){
        Mockito.when(userRepository.save(user1)).thenReturn(user1);
        User result=userService.saveUser(user1);
        assertEquals("Test",result.getNickname());
        assertEquals("Test",result.getName());
        assertEquals(1L,result.getId());
        assertEquals("Test",result.getEmail());
    }

}
