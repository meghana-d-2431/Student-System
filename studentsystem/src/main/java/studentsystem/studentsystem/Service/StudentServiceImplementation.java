package studentsystem.studentsystem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import studentsystem.studentsystem.Model.Student;
import studentsystem.studentsystem.Repository.StudentRepository;

@Service
public class  StudentServiceImplementation implements StudentService {
   
    @Autowired   
    private StudentRepository studentRepository;

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

}
