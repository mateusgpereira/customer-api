package io.demo.repositories;

import javax.enterprise.context.ApplicationScoped;

import io.demo.entities.Customer;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, Long> {

}
