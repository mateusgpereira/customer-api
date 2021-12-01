package io.demo.mappers;

import org.mapstruct.Mapper;

import io.demo.entities.Customer;
import io.demo.models.CustomerRest;

@Mapper(componentModel = "cdi")
public interface CustomerMapper {
  Customer toEntity(CustomerRest rest);

  CustomerRest toRest(Customer entity);
}
