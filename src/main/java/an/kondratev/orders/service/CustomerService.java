package an.kondratev.orders.service;

import an.kondratev.orders.dto.CustomerDTO;
import an.kondratev.orders.mapper.CustomerMapper;
import an.kondratev.orders.model.Customer;
import an.kondratev.orders.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService implements CustomerServiceInterface {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Customer createCustomer(CustomerDTO customerDTO) {
        log.info("Create customer #{}", customerDTO.getCustomerIdDTO());
        return customerRepository.save(customerMapper.toEntity(customerDTO));
    }

    @Override
    public Customer updateCustomer(CustomerDTO customerDTO) {
        log.info("Customer #{} updated", customerDTO.getCustomerIdDTO());
        return customerRepository.save(customerMapper.toEntity(customerDTO));
    }

    @Override
    public void deleteCustomer(Long id) {
        log.info("Customer #{} deleted", id);
        customerRepository.deleteById(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}
