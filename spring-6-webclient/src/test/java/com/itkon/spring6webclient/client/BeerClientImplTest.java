package com.itkon.spring6webclient.client;

import com.itkon.spring6webclient.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient client;

    @Test
    void listBeers() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeers().subscribe(s -> {
            System.out.println(s);
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerMap() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerMap().subscribe(map -> {
            System.out.println(map);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerJson() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerJsonNode().subscribe(jsonNode -> {
            System.out.println(jsonNode);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetListBeersDto() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        client.listBeerDtos().subscribe(dto -> {
            System.out.println(dto.getBeerName());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerById() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerDtos()
                .flatMap(beerDTO -> client.getBeerById(beerDTO.getId()))
                .subscribe(beerDTO -> {
                    System.out.println(beerDTO.getBeerName());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerByBeerStyle() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.getBeerByBeerStyle("Pale Ale")
                .subscribe(beerDTO -> {
                    System.out.println(beerDTO);
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testCreateBeer() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO newBeerDTO = BeerDTO.builder()
                .price(BigDecimal.TEN)
                .beerName("name")
                .beerStyle("IPS")
                .quantityOnHand(18)
                .upc("123321")
                .build();

        client.createBeer(newBeerDTO)
                .subscribe(beerDTO -> {
                    System.out.println(beerDTO);
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testUpdate() {
        final String newName = "new name";
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerDtos()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(newName))
                .flatMap(beerDTO -> client.updateBeer(beerDTO))
                .subscribe(beerDTO -> {
                    System.out.println(beerDTO);
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }
}