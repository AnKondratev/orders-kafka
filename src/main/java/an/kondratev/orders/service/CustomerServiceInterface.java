package an.kondratev.orders.service;

import an.kondratev.orders.dto.CustomerDTO;
import an.kondratev.orders.model.Customer;

import java.util.List;

public interface CustomerServiceInterface {
    Customer createCustomer(CustomerDTO customerDTO);

    Customer updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long id);

    List<Customer> getAllCustomers();


}
