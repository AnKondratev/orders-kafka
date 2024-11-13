package an.kondratev.orders.controller;

import an.kondratev.orders.dto.CustomerDTO;
import an.kondratev.orders.model.Customer;
import an.kondratev.orders.service.CustomerServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerServiceInterface customerService;

    private CustomerDTO customerDTO;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        customerDTO = CustomerDTO.builder()
                .firstNameDTO("John")
                .lastNameDTO("Doe")
                .emailDTO("john.doe@example.com")
                .phoneDTO("1234567890")
                .build();

        customer = Customer.builder()
                .customerId(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();
    }

    @Test
    public void testAddCustomer_Success() {
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.addCustomer(customerDTO);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(customer);
        verify(customerService).createCustomer(customerDTO);
    }

    @Test
    public void testAddCustomer_NullInput() {
        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(null);

        ResponseEntity<Customer> response = customerController.addCustomer(null);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNull();
        verify(customerService).createCustomer(null);
    }
}

