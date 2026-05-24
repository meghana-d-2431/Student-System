package studentsystem.studentsystem;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import studentsystem.studentsystem.Model.Student;
import studentsystem.studentsystem.Repository.StudentRepository;
import studentsystem.studentsystem.Service.StudentServiceImplementation;

@ExtendWith(MockitoExtension.class) // This annotation is used to enable Mockito in the test class, 
class StudentServiceImplementationTests { // This is the test class for StudentService Implementation

    @Mock // This annotation is used to create a mock object of StudentRepository, which will be used to test the service implementation without actually hitting the database
    private StudentRepository studentRepository; // This is the mock repository that will be injected into the service implementation

    @InjectMocks // This annotation is used to create an instance of StudentServiceImplementation and inject the mock repository into it, so that we can test the service methods with the mocked repository
    private StudentServiceImplementation studentService; // This is the service implementation that we are testing, 

    @Test // This test method is used to test the saveStudent method of the service implementation,
    //  it checks if the student is saved correctly and if the repository's save method is called once
    void testSaveStudent() {
        Student student = new Student(1L, "John Doe", "Texas"); 
        when(studentRepository.save(student)).thenReturn(student);// This line tells Mockito to return the same student object when the save method is called with that student

        Student savedStudent = studentService.saveStudent(student); // This line calls the saveStudent method of the service implementation,

        assertNotNull(savedStudent); // check if the saved student is not null, which means it was saved successfully
        assertEquals(1L, savedStudent.getId()); // check if the id of the saved student is 1L, which is the id we given to the student object
        assertEquals("John Doe", savedStudent.getName()); // check if the name of the saved student is "John Doe", which is the name we given to the student object
        assertEquals("Texas", savedStudent.getAddress()); // check if the address of the saved student is "Texas", which is the address we given to the student object
        verify(studentRepository, times(1)).save(student); // This line verifies that the service method is calling the repository method correctly
    }

    @Test // This test method is used to test the getAllStudents method 
    void testGetAllStudents() {
        List<Student> students = Arrays.asList( // This line creates a list of students 
                new Student(1L, "John Doe", "Texas"), // This is the first student object in the list with id 1L, name "John Doe" and address "Texas"
                new Student(2L, "Jane Smith", "New York") // This is the second student object in the list with id 2L, name "Jane Smith" and address "New York"
        );
        when(studentRepository.findAll()).thenReturn(students); // This line tells Mockito to return the list of students when the findAll method is called on the repository

        List<Student> result = studentService.getAllStudents(); // This line calls the getAllStudents method of the service implementation, which should return the list of students from the repository

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
        verify(studentRepository, times(1)).findAll();
    }
}
