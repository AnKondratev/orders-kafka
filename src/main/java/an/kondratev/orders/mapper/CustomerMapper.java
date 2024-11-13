package an.kondratev.orders.mapper;

import an.kondratev.orders.dto.CustomerDTO;
import an.kondratev.orders.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }

        return Customer.builder()
                .firstName(customerDTO.getFirstNameDTO())
                .lastName(customerDTO.getLastNameDTO())
                .email(customerDTO.getEmailDTO())
                .phone(customerDTO.getPhoneDTO())
                .build();
    }
}

