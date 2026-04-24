package studentsystem.studentsystem.Service;

import java.util.List;

import studentsystem.studentsystem.Model.Student;

public interface StudentService {

    public Student saveStudent(Student student);

    public List<Student> getAllStudents();
}
