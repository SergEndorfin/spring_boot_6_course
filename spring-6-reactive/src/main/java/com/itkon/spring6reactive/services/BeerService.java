package com.itkon.spring6reactive.services;

import com.itkon.spring6reactive.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> getBeerById(Integer beerId);

    Mono<BeerDTO> saveNewBeer(BeerDTO beerDto);

    Mono<BeerDTO> updateBeer(Integer beerId, BeerDTO beerDto);

    Mono<Void> deleteBeerById(Integer beerId);

    Mono<BeerDTO> patchBeer(Integer beerId, BeerDTO beerDto);
}
