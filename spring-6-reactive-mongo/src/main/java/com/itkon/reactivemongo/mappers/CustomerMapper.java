package com.itkon.reactivemongo.mappers;

import com.itkon.reactivemongo.domain.Customer;
import com.itkon.reactivemongo.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer dtoToEntity(CustomerDTO dto);

    CustomerDTO entityToDto(Customer customer);
}
