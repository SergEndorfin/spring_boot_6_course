package com.itkon.spring6restmvc.repositories;

import com.itkon.spring6restmvc.bootstrap.BootstrapData;
import com.itkon.spring6restmvc.entities.Beer;
import com.itkon.spring6restmvc.model.BeerStyle;
import com.itkon.spring6restmvc.service.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName() {
        Page<Beer> beerList = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null);
        assertThat(beerList.getContent()).hasSize(336);
    }

    @Test
    void testSaveBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            beerRepository.save(
                    Beer.builder()
                            .beerName("name 123654231 123654231 123654231 123654231 12345678")
                            .beerStyle(BeerStyle.PALE_ALE)
                            .upc("123654231")
                            .price(new BigDecimal("22.88"))
                            .build()
            );
            beerRepository.flush();
        });
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(
                Beer.builder()
                        .beerName("name")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("123654231")
                        .price(new BigDecimal("22.88"))
                        .build()
        );

        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}