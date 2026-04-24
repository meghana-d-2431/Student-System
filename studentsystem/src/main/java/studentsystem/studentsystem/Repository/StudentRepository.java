package studentsystem.studentsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import studentsystem.studentsystem.Model.Student;

@Repository
public interface  StudentRepository extends JpaRepository<Student, Long> {

}
