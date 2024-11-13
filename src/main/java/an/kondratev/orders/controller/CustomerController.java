package an.kondratev.orders.controller;

import an.kondratev.orders.dto.CustomerDTO;
import an.kondratev.orders.model.Customer;
import an.kondratev.orders.service.CustomerServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {

    private CustomerServiceInterface customerService;

    @PostMapping("/create")
    public ResponseEntity<Customer> addCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer newCustomer = customerService.createCustomer(customerDTO);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }
}
