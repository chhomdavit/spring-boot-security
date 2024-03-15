package web.project.springbootcrud.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import web.project.springbootcrud.dto.EmployeeDTO;
import web.project.springbootcrud.dto.EmployeePageResponse;
import web.project.springbootcrud.model.Employee;
import web.project.springbootcrud.service.EmployeeService;
import web.project.springbootcrud.util.AppConstants;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
@RequiredArgsConstructor
public class EmployeeController {
    
    private final EmployeeService employeeService;
    
    // @GetMapping("/employees")
	// public ResponseEntity<List<Employee>> getAllEmployees() {
    //     List<Employee> employees = employeeService.getAllAdminEmployees();
    //     return ResponseEntity.ok().body(employees);
    // }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        try {
            List<Employee> employees = employeeService.getAllAdminEmployees();
            return ResponseEntity.ok().body(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/employees/pagination")
    public ResponseEntity<EmployeePageResponse> getPagination(
                @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
            ){
        try {
            
            return ResponseEntity.ok().body(employeeService.getPagination(pageNumber, pageSize));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // @GetMapping("/employees")
    // public ResponseEntity<?> getEmployees(
    //         @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
    //         @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    // ) {
    //     try {
    //         if (pageNumber != null && pageSize != null) {
    //             EmployeePageResponse pageResponse = employeeService.getPagination(pageNumber, pageSize);
    //             return ResponseEntity.ok().body(pageResponse);
    //         } else {
    //             List<Employee> employees = employeeService.getAllAdminEmployees();
    //             return ResponseEntity.ok().body(employees);
    //         }
    //     } catch (Exception e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    //     }
    // }

    @PostMapping("/employees/create")
    public ResponseEntity<?> saveEmployee(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("photo") MultipartFile photo) {
        if (photo.isEmpty()) {
            return ResponseEntity.badRequest().body("Photo is required");
        }

        try {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setFirstName(firstName);
            employeeDTO.setLastName(lastName);
            employeeDTO.setEmail(email);

            EmployeeDTO createEmployeeDTO = employeeService.createEmployee(employeeDTO, photo);

            return ResponseEntity.ok().body(createEmployeeDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create employee: " + e.getMessage());
        }
    }
    
    @PutMapping("/employees/update/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable(value = "id") long id,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam(value = "photo", required = false) MultipartFile photo) {
        try {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setFirstName(firstName);
            employeeDTO.setLastName(lastName);
            employeeDTO.setEmail(email);
            
            EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployee(id, employeeDTO, photo);

            return ResponseEntity.ok().body(updatedEmployeeDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update employee: " + e.getMessage());
        }
    }

    @DeleteMapping("/employees/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable(value = "id") long id) {
        try {
		    employeeService.delete(id);
            return ResponseEntity.ok("Employee deleted successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete employee: " + e.getMessage());
        }
	}


    // @GetMapping("/employees/pagination")
	// public ResponseEntity<EmployeePageResponse> getMoiesWithPagination(
	// 			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
	// 			@RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
	// 		){
	// 	return ResponseEntity.ok().body(employeeService.getPagination(pageNumber, pageSize));
	// }


}
