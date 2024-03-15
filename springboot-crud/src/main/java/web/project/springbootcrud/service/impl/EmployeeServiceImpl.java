package web.project.springbootcrud.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import web.project.springbootcrud.dto.EmployeeDTO;
import web.project.springbootcrud.dto.EmployeePageResponse;
import web.project.springbootcrud.exception.NotFoundException;
import web.project.springbootcrud.model.Employee;
import web.project.springbootcrud.repository.EmployeeRepository;
import web.project.springbootcrud.service.EmployeeService;
import web.project.springbootcrud.service.FileUploadService;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final FileUploadService fileUploadService;
    private final ModelMapper modelMapper;

    @Value("${project.upload}")
    private String path;

    // 
    
    @Override
    public List<Employee> getAllAdminEmployees() {
        try {
            return (List<Employee>) employeeRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return empty list in case of exception
        }
    }

    @Override
    public void delete(Long id) throws IOException {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));

        String uploadDirectory = path;
        String photoFileName = existingEmployee.getPhoto();
        if (photoFileName != null && !photoFileName.isEmpty()) {
            try {
                Files.deleteIfExists(Paths.get(uploadDirectory, photoFileName));
            } catch (IOException e) {
                e.printStackTrace();
                throw new IOException("Failed to delete photo", e);
            }
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee getAdminEmployeeById(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        Employee employee = null;
        if (optional.isPresent()) {
            employee = optional.get();
        } else {
            throw new RuntimeErrorException(null, "Eemployee not fount for :: " + id);
        }
        return employee;
    }

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO, MultipartFile file) throws IOException {

        String newFileName = fileUploadService.saveFile(file, path);
        employeeDTO.setPhoto(newFileName);

        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        Employee saved = employeeRepository.save(employee);
        EmployeeDTO response = modelMapper.map(saved, EmployeeDTO.class);
        return response;
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO, MultipartFile file) throws IOException {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));

        String newFileName = null;
        try {
            if (file != null && !file.isEmpty()) {
                if (existingEmployee.getPhoto() != null && !existingEmployee.getPhoto().isEmpty()) {
                    Files.deleteIfExists(Paths.get(path, existingEmployee.getPhoto()));
                }
                newFileName = fileUploadService.saveFile(file, path);
            } else {
                newFileName = existingEmployee.getPhoto();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while updating employee", e);
        }

        employeeDTO.setPhoto(newFileName);

        Employee updated = modelMapper.map(employeeDTO, Employee.class);
        updated.setId(existingEmployee.getId());
        Employee saved = employeeRepository.save(updated);

        EmployeeDTO response = modelMapper.map(saved, EmployeeDTO.class);
        return response;
    }
    


    @Override
    public EmployeePageResponse getPagination(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Employee> employeePages = employeeRepository.findAll(pageable);
        List<Employee> employees = employeePages.getContent();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
            employeeDTOs.add(employeeDTO);
        }

        return new EmployeePageResponse(employeeDTOs, pageNumber, pageSize, employeePages.getTotalElements(),
                employeePages.getTotalPages(), employeePages.isLast());
    }

}





// package web.project.springbootcrud.service.impl;

// import java.io.File;
// import java.io.IOException;
// import java.io.InputStream;
// import java.nio.file.Files;
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;
// import java.util.List;
// import java.util.Optional;
// import java.util.UUID;

// import javax.management.RuntimeErrorException;

// import org.modelmapper.ModelMapper;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.data.domain.Page;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import lombok.RequiredArgsConstructor;
// import web.project.springbootcrud.dto.EmployeeDTO;
// import web.project.springbootcrud.exception.NotFoundException;
// import web.project.springbootcrud.model.Employee;
// import web.project.springbootcrud.repository.EmployeeRepository;
// import web.project.springbootcrud.service.EmployeeService;

// @Service
// @RequiredArgsConstructor
// @SuppressWarnings("null")
// public class EmployeeServiceImpl implements EmployeeService {

//     private final EmployeeRepository employeeRepository;
//     // private final FileUploadService fileUploadService;
//     private final ModelMapper modelMapper;

//     @Value("${project.upload}")
// 	private String path;

//     @Override
//     public List<Employee> getAllAdminEmployees() {
//         return (List<Employee>) employeeRepository.findAll();
//     }


//     @Override
//     public void delete(Long id) throws IOException {
//         Employee existingEmployee = employeeRepository.findById(id)
//                 .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));

//         String uploadDirectory = path;
//         String photoFileName = existingEmployee.getPhoto();
//         if (photoFileName != null && !photoFileName.isEmpty()) {
//             try {
//                 Files.deleteIfExists(Paths.get(uploadDirectory, photoFileName));
//             } catch (IOException e) {
//                 e.printStackTrace();
//                 throw new IOException("Failed to delete photo", e);
//             }
//         }
//         employeeRepository.deleteById(id);
//     }


//     @Override
//     public Employee getAdminEmployeeById(Long id) {
//         Optional<Employee> optional = employeeRepository.findById(id);
//         Employee employee = null;
//         if (optional.isPresent()) {
//             employee = optional.get();
//         } else {
//             throw new RuntimeErrorException(null, "Eemployee not fount for :: " + id);
//         }
//         return employee;
//     }

//     @Override
//     public EmployeeDTO createEmployee(EmployeeDTO employeeDTO, MultipartFile photo) throws IOException {
//         String uploadDirectory = path;
//         File uploadDir = new File(uploadDirectory);
//         if (!uploadDir.exists()) {
//             uploadDir.mkdir();
//         }
//         try {
//             if (photo != null && !photo.isEmpty()) {
//                 InputStream inputStream = photo.getInputStream();
//                 String originalFileName = photo.getOriginalFilename();
//                 if(originalFileName != null){
//                     String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//                 String newFileName = UUID.randomUUID().toString() + fileExtension; 
//                 Files.copy(
//                         inputStream,
//                         Paths.get(uploadDirectory, newFileName),
//                         StandardCopyOption.REPLACE_EXISTING);
//                 employeeDTO.setPhoto(newFileName);
//                 } 
//             } else {
//                 employeeDTO.setPhoto(null);
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             throw new RuntimeException("Failed to create employee", e);
//         }
//         Employee employee = modelMapper.map(employeeDTO, Employee.class);
//         Employee saved = employeeRepository.save(employee);
//         EmployeeDTO response = modelMapper.map(saved, EmployeeDTO.class);
//         return response;
//     }

//     // @Override
//     // public EmployeeDTO createEmployee(EmployeeDTO employeeDTO, MultipartFile photo) throws IOException {
        
//     //     String newFileName = fileUploadService.saveFile(photo, part);
//     //     employeeDTO.setPhoto(newFileName);

//     //     Employee employee = modelMapper.map(employeeDTO, Employee.class);
//     //     Employee saved = employeeRepository.save(employee);
//     //     EmployeeDTO response = modelMapper.map(saved, EmployeeDTO.class);
//     //     return response;
//     // }

//     @Override
//     public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO, MultipartFile photo) throws IOException {
//         Employee existingEmployee = employeeRepository.findById(id)
//                 .orElseThrow(() -> new NotFoundException("Employee not found with id: " + id));

//         String uploadDirectory = path;
//         File uploadDir = new File(uploadDirectory);
//         if (!uploadDir.exists()) {
//             uploadDir.mkdir();
//         }
//         try {
//             if (photo != null && !photo.isEmpty()) {
//                 if (existingEmployee.getPhoto() != null && !existingEmployee.getPhoto().isEmpty()) {
//                     Files.deleteIfExists(Paths.get(uploadDirectory, existingEmployee.getPhoto()));
//                 }
//                 InputStream inputStream = photo.getInputStream();
//                 String originalFileName = photo.getOriginalFilename();
//                 if(originalFileName != null){
//                     String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
//                     String newFileName = UUID.randomUUID().toString() + fileExtension;
//                     Files.copy(
//                             inputStream,
//                             Paths.get(uploadDirectory, newFileName),
//                             StandardCopyOption.REPLACE_EXISTING);
//                     employeeDTO.setPhoto(newFileName);
//                 }

//             } else {
//                 employeeDTO.setPhoto(existingEmployee.getPhoto());
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//             throw new RuntimeException("Error occurred while updating employee", e);
//         }
//         Employee updated = modelMapper.map(employeeDTO, Employee.class);
//         updated.setId(existingEmployee.getId());
//         Employee saved = employeeRepository.save(updated);
//         EmployeeDTO response = modelMapper.map(saved, EmployeeDTO.class);
//         return response;
//     }

//     @Override
//     public Page<Employee> getAllAdminEmployees(int page, int size) {
//         throw new UnsupportedOperationException("Unimplemented method 'getAllAdminEmployees'");
//     }

//     @Override
//     public Page<Employee> searchAdminEmployees(String keyword, int page, int size) {
//         throw new UnsupportedOperationException("Unimplemented method 'searchAdminEmployees'");
//     }

// }



