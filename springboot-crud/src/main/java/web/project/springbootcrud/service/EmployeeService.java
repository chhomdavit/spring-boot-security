package web.project.springbootcrud.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import web.project.springbootcrud.dto.EmployeeDTO;
import web.project.springbootcrud.dto.EmployeePageResponse;
import web.project.springbootcrud.model.Employee;

public interface EmployeeService {
    
    List<Employee> getAllAdminEmployees();
	void delete(Long id) throws IOException;
	Employee getAdminEmployeeById(Long id);
	EmployeeDTO createEmployee(EmployeeDTO employeeDTO ,MultipartFile file) throws IOException;
	EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO, MultipartFile file) throws IOException;
	
	// EmployeePageResponse getAllWithPagination(Integer pageNumber, Integer pageSize);
	// EmployeePageResponse getAllWithPaginationAndSorting( Integer pageNumber, Integer pageSize, String sortBy, String dir);
	// EmployeePageResponse search(String keyword, Integer pageNumber, Integer pageSize);

	EmployeePageResponse getPagination(Integer pageNumber, Integer pageSize);
	// Page<Employee> search(String keyword, int page, int size);

	// EmployeePageResponse getAll( Integer pageNumber,Integer pageSize, String sortBy,String sortDir);
}
