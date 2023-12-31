package com.itkon.reactivemongo.service;

import com.itkon.reactivemongo.domain.Beer;
import com.itkon.reactivemongo.mappers.BeerMapper;
import com.itkon.reactivemongo.mappers.BeerMapperImpl;
import com.itkon.reactivemongo.model.BeerDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
public class BeerServiceImplTest {

    @Autowired
    BeerService beerService;

    @Autowired
    BeerMapper beerMapper;

    BeerDTO beerDTO;

    @BeforeEach
    void setUp() {
        beerDTO = beerMapper.entityToDto(getTestBeer());
    }

    @Test
    @DisplayName("Test Save Beer Using Subscriber")
    void saveBeerUseSubscriber() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicReference<BeerDTO> atomicDto = new AtomicReference<>();

        Mono<BeerDTO> savedBeerDTOMono = beerService.saveBeer(beerDTO);

        savedBeerDTOMono.subscribe(savedDTO -> {
            System.out.println(savedDTO);
            atomicBoolean.set(true);
            atomicDto.set(savedDTO);
        });

        await().untilTrue(atomicBoolean);

        BeerDTO persistedDto = atomicDto.get();
        assertThat(persistedDto).isNotNull();
        assertThat(persistedDto.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Save Beer Using Block")
    void testSaveBeerUseBlock() {
        BeerDTO savedDto = beerService.saveBeer(getTestBeerDto()).block();
        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Update Beer Using Block")
    void testUpdateBlocking() {
        final String newName = "New Beer Name";  // use final so cannot mutate
        BeerDTO savedBeerDto = getSavedBeerDto();
        savedBeerDto.setBeerName(newName);

        BeerDTO updatedDto = beerService.saveBeer(savedBeerDto).block();

        //verify exists in db
        BeerDTO fetchedDto = beerService.getById(updatedDto.getId()).block();
        assertThat(fetchedDto.getBeerName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("Test Update Using Reactive Streams")
    void testUpdateStreaming() {
        final String newName = "New Beer Name";  // use final so cannot mutate

        AtomicReference<BeerDTO> atomicDto = new AtomicReference<>();

        beerService.saveBeer(getTestBeerDto())
                .map(savedBeerDto -> {
                    savedBeerDto.setBeerName(newName);
                    return savedBeerDto;
                })
                .flatMap(beerService::saveBeer) // save updated beer
                .flatMap(savedUpdatedDto -> beerService.getById(savedUpdatedDto.getId())) // get from db
                .subscribe(atomicDto::set);

        await().until(() -> atomicDto.get() != null);
        assertThat(atomicDto.get().getBeerName()).isEqualTo(newName);
    }

    @Test
    void testDeleteBeer() {
        BeerDTO beerToDelete = getSavedBeerDto();
        beerService.deleteBeerById(beerToDelete.getId()).block();
        Mono<BeerDTO> expectedEmptyBeerMono = beerService.getById(beerToDelete.getId());

        BeerDTO emptyBeer = expectedEmptyBeerMono.block();

        assertThat(emptyBeer).isNull();
    }

    public BeerDTO getSavedBeerDto() {
        return beerService.saveBeer(getTestBeerDto()).block();
    }

    public static BeerDTO getTestBeerDto() {
        return new BeerMapperImpl().entityToDto(getTestBeer());
    }

    public static Beer getTestBeer() {
        return Beer.builder()
                .beerName("Space Dust")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(88)
                .upc("123213")
                .build();
    }

    @Test
    void findFirstByBeerName() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO savedBeerDto = getSavedBeerDto();
        Mono<BeerDTO> foundDto = beerService.findFirstByBeerName(savedBeerDto.getBeerName());
        foundDto.subscribe(dto -> {
            System.out.println(dto);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void findByBeerStyle() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO savedBeerDto = getSavedBeerDto();
        beerService.findByBeerStyle(savedBeerDto.getBeerStyle())
                .subscribe(dto -> {
                    System.out.println(dto);
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }
}