package com.itkon.spring6restmvc.mappers;

import com.itkon.spring6restmvc.entities.Customer;
import com.itkon.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
