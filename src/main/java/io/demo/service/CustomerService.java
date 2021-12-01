package io.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import io.demo.entities.Customer;
import io.demo.exceptions.ServiceException;
import io.demo.mappers.CustomerMapper;
import io.demo.models.CustomerRest;
import io.demo.repositories.CustomerRepository;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CustomerService {

  private CustomerRepository customerRepository;
  private CustomerMapper customerMapper;

  public List<CustomerRest> getAll() {
    return customerRepository.findAll().stream().map(customerMapper::toRest).collect(Collectors.toList());
  }

  public Optional<CustomerRest> getById(Long id) {
    return customerRepository.findByIdOptional(id).map(customerMapper::toRest);
  }

  @Transactional
  public CustomerRest save(CustomerRest customer) {
    Customer entity = customerMapper.toEntity(customer);
    customerRepository.persist(entity);
    return customerMapper.toRest(entity);
  }

  @Transactional
  public CustomerRest update(CustomerRest customer) {
    if (customer.getId() == null) {
      throw new ServiceException("Customer does not have an Id");
    }

    Optional<Customer> optional = customerRepository.findByIdOptional(customer.getId());
    if (optional.isEmpty()) {
      throw new ServiceException("Customer with id: " + customer.getId() + " not found");
    }

    Customer entity = optional.get();

    entity.setFirstName(customer.getFirstName());
    entity.setMiddleName(customer.getMiddleName());
    entity.setLastName(customer.getLastName());
    entity.setSuffix(customer.getSuffix());
    entity.setEmail(customer.getEmail());
    entity.setPhone(customer.getPhone());

    customerRepository.persist(entity);
    return customerMapper.toRest(entity);
  }
}
