package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void testDeleteBeer() {
        BeerDTO newDTO = BeerDTO.builder()
                .price(new BigDecimal("8.88"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123456")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDTO);

        beerClient.deleteBeer(savedDto.getId());

        assertThrows(HttpClientErrorException.class,
                () -> beerClient.getBeerById(savedDto.getId()));
    }

    @Test
    void testUpdateBeer() {
        BeerDTO newDTO = BeerDTO.builder()
                .price(new BigDecimal("8.88"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123456")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDTO);

        final String newName = "New Name";
        savedDto.setBeerName(newName);

        BeerDTO updatedBeer = beerClient.updateBeer(savedDto);
        assertEquals(newName, updatedBeer.getBeerName());
    }

    @Test
    void testCreateBeer() {
        BeerDTO newDTO = BeerDTO.builder()
                .price(new BigDecimal("8.88"))
                .beerName("Mango Bobs")
                .beerStyle(BeerStyle.IPA)
                .quantityOnHand(500)
                .upc("123456")
                .build();

        BeerDTO savedDto = beerClient.createBeer(newDTO);
        assertNotNull(savedDto);
    }

    @Test
    void listBeersById() {
        Page<BeerDTO> beerDTOS = beerClient.listBeers();
        BeerDTO dto = beerDTOS.getContent().get(0);

        BeerDTO byId = beerClient.getBeerById(dto.getId());

        assertNotNull(byId);
    }

    @Test
    void listBeersNoName() {
        beerClient.listBeers(null, null, null, null, null);
    }

    @Test
    void listBeers() {
        beerClient.listBeers("ALE", null, null, null, null);
    }
}