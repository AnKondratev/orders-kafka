package an.kondratev.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long customerIdDTO;
    private String firstNameDTO;
    private String lastNameDTO;
    private String emailDTO;
    private String phoneDTO;
}