package br.com.tco.countryapi.domain.country.usecase;

import br.com.tco.countryapi.domain.country.entities.Country;
import br.com.tco.countryapi.domain.country.usecase.exception.CountryRequestInvalidException;
import br.com.tco.countryapi.domain.country.usecase.port.CountryRepository;
import br.com.tco.countryapi.domain.country.usecase.validator.CountryValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveCountryTest {

  SaveCountry saveCountry;
  CountryRepository countryRepository;

  @BeforeEach
  void setup() {
    countryRepository = Mockito.mock(CountryRepository.class);
    saveCountry = new SaveCountry(new CountryValidatorImpl(), countryRepository);
  }

  @Test
  void whenCreateCorrectCountry_returnCountry() {
    final var correctCountry = new Country("A1", "Brazil", "BR");
    Mockito.when(countryRepository.create(correctCountry)).thenReturn(Mono.just(correctCountry));

    StepVerifier.create(saveCountry.createCountry(correctCountry))
        .consumeNextWith(
            country -> {
              assertEquals(correctCountry.id(), country.id(), "This value should be equal");
              assertEquals(correctCountry.name(), country.name(), "This value should be equal");
              assertEquals(
                  correctCountry.abbreviation(),
                  country.abbreviation(),
                  "This value should be equal");
            })
        .verifyComplete();
  }

  @Test
  void whenCreateIncorrectCountry_throwsException() {
    final var incorrectCountry = new Country("A1", "Brazil", null);

    StepVerifier.create(saveCountry.createCountry(incorrectCountry))
        .expectErrorMatches(
            throwable ->
                throwable instanceof CountryRequestInvalidException
                    && throwable.getMessage().equals("Country abbreviation could not be null"))
        .verify();
  }
}
