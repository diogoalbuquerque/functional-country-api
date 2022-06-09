package br.com.tco.countryapi.domain.country.usecase;

import br.com.tco.countryapi.domain.country.entities.Country;
import br.com.tco.countryapi.domain.country.usecase.port.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FindCountryTest {
  FindCountry findCountry;
  CountryRepository countryRepository;

  @BeforeEach
  void setup() {
    countryRepository = Mockito.mock(CountryRepository.class);
    findCountry = new FindCountry(countryRepository);
  }

  @Test
  void whenFindIdExist_returnMonoCountry() {
    Mockito.when(countryRepository.findById("A1"))
        .thenReturn(Mono.just(new Country("A1", "Brazil", "BR")));

    StepVerifier.create(findCountry.findById("A1"))
        .consumeNextWith(
            country -> {
              assertNotNull(country, "Country should be not null");
              assertEquals("A1", country.id(), "This object should be the same");
            })
        .verifyComplete();
  }

  @Test
  void whenFindIdNotExist_returnMonoEmpty() {
    Mockito.when(countryRepository.findById("A1")).thenReturn(Mono.empty());

    StepVerifier.create(findCountry.findById("A1")).verifyComplete();
  }

  @Test
  void whenFindAllExist_returnFluxCountries() {
    Mockito.when(countryRepository.findAll())
        .thenReturn(
            Flux.just(
                new Country("A1", "Brazil", "BR"),
                new Country("A2", "United States of America", "US")));

    StepVerifier.create(findCountry.findAll())
        .consumeNextWith(
            country -> {
              assertNotNull(country, "Country should be not null");
              assertEquals("A1", country.id(), "This object should be the same");
            })
        .consumeNextWith(
            country -> {
              assertNotNull(country, "Country should be not null");
              assertEquals("A2", country.id(), "This object should be the same");
            })
        .verifyComplete();
  }

  @Test
  void whenFindAllNotExist_returnFluxEmpty() {
    Mockito.when(countryRepository.findAll()).thenReturn(Flux.empty());

    StepVerifier.create(findCountry.findAll()).verifyComplete();
  }
}
