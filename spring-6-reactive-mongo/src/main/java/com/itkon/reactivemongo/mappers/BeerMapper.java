package com.itkon.reactivemongo.mappers;

import com.itkon.reactivemongo.domain.Beer;
import com.itkon.reactivemongo.model.BeerDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface BeerMapper {

    BeerDTO entityToDto(Beer beer);

    Beer dtoToEntity(BeerDTO beerDTO);
}
