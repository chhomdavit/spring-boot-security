package web.project.springbootcrud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    
    private long id;

    private String firstName;

    private String lastName;

    private String email;

    private String photo;
}
