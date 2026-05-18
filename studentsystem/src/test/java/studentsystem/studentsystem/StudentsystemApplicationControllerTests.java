package studentsystem.studentsystem;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import studentsystem.studentsystem.Controller.StudentController;
import studentsystem.studentsystem.Model.Student;
import studentsystem.studentsystem.Service.StudentService;

@ExtendWith(MockitoExtension.class) 
// This annotation is used to enable Mockito in JUnit which allows us to use @Mock and @InjectMocks annotations 
// for mocking dependencies and injecting them into the class under test.
public class StudentsystemApplicationControllerTests {
    
@Mock
    private StudentService studentService; // Mockito creates a fake version of StudentService so we we only test controller logic

    @InjectMocks
    private StudentController studentController; // Mockito injects the mocked StudentService into the StudentController so we can test it without needing a real service implementation

    @Test // this annotation indicates that the following method is a test case
    void testAddStudent() { 
        Student student = new Student(1L, "John Doe", "Texas"); // Create a sample student object to be used in the test

        String result = studentController.add(student); //call the add method in the StudentController with the sample student object and store the result

        assertEquals("New Student has been added successfully", result); // we check if the result from the add method matches the expected success message

        verify(studentService, times(1)).saveStudent(student);
        // this 
    }

    @Test
    void testGetAllStudents() {
        List<Student> students = Arrays.asList( // sample list of students data 
                new Student(1L, "John Doe", "Texas"),
                new Student(2L, "Jane Smith", "New York")
        );

        when(studentService.getAllStudents()).thenReturn(students); // when studentService.getAllStudents() is called, it will return the sample list of students we created above

        List<Student> result = studentController.getAllStudents(); // store that list of students in a variable called result
    //[
        //Student(1L, "John Doe", "Texas"),
        //Student(2L, "Jane Smith", "New York")
    //]

        assertEquals(2, result.size()); // we check if the size of the result list is 2, which means it contains the two students we created in the sample data
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());

        verify(studentService, times(1)).getAllStudents();
        // checks whether the getAllStudents method was called or not, and how many times it was called. In this case, 
        // we expect it to be called once when we call the getAllStudents method in the controller.
    }
}
