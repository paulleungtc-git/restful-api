package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository mockStudentRepository;

    private static final ObjectMapper om = new ObjectMapper();

    @Before
    public void init() {
        Student student = new Student(223445L, "Mike", "Wong", "3 A", "Singapore");
        when(mockStudentRepository.findById(223445L).get()).thenReturn(student);
    }


    @Test
    public void addNewStudent() throws Exception {

        Student newStudent = new Student(223449L, "Paul", "Tang", "3 A", "Japan");
        when(mockStudentRepository.save(any(Student.class))).thenReturn(newStudent);

        mockMvc.perform(post("/students")
                .content(om.writeValueAsString(newStudent))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                /*.andDo(print())*/
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(223449L)))
                .andExpect(jsonPath("$.firstName", is("Paul")))
                .andExpect(jsonPath("$.lastName", is("Tang")))
                .andExpect(jsonPath("$.class", is("3 A")))
                .andExpect(jsonPath("$.nationality", is("Japan")));

        verify(mockStudentRepository, times(1)).save(any(Student.class));

    }

    public void updateStudent() {

    }

    public void deleteStudent() {

    }

    public void fetchBulkRecord() {

    }

    public void fetchOneStudent() {

    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
