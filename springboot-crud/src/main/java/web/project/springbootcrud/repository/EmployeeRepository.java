package web.project.springbootcrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import web.project.springbootcrud.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

    Employee getAdminEmployeeById(long id);
    // Page<Employee> findByLastNameContainingIgnoreCase(String keyword, Pageable pageable);
    // Page<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String firstName, String lastName, String email, Pageable pageable);
    @Query("select e from Employee e where e.lastName like %:key%")
    List<Employee> searchByLastName(@Param("key") String lastName);
}
