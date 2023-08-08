package com.itkon.spring6webclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.itkon.spring6webclient.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BeerClient {

    Flux<String> listBeers();

    Flux<Map> listBeerMap();

    Flux<JsonNode> listBeerJsonNode();

    Flux<BeerDTO> listBeerDtos();

    Mono<BeerDTO> getBeerById(String id);

    Flux<BeerDTO> getBeerByBeerStyle(String style);

    Mono<BeerDTO> createBeer(BeerDTO newBeerDTO);

    Mono<BeerDTO> updateBeer(BeerDTO beerDTO);
}
