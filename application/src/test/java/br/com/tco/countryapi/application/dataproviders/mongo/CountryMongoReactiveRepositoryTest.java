package br.com.tco.countryapi.application.dataproviders.mongo;

import br.com.tco.countryapi.application.ObjectTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class CountryMongoReactiveRepositoryTest extends ObjectTest {

  @MockBean CountryReactiveRepository countryReactiveRepository;
  CountryMongoReactiveRepository countryMongoReactiveRepository;

  @BeforeEach
  void setup() {
    countryMongoReactiveRepository = new CountryMongoReactiveRepository(countryReactiveRepository);
  }

  @Test
  void whenCreateCountry_returnCreatedCountry() {

    Mockito.when(countryReactiveRepository.save(any()))
        .thenReturn(Mono.just(createCountryEntityTest()));

    final var country = createCountryTest();

    StepVerifier.create(countryMongoReactiveRepository.create(country))
        .consumeNextWith(
            countryCreated -> {
              assertEquals(country.id(), countryCreated.id(), "This value should be equal");
              assertEquals(country.name(), countryCreated.name(), "This value should be equal");
              assertEquals(
                  country.abbreviation(),
                  countryCreated.abbreviation(),
                  "This value should be equal");
            })
        .verifyComplete();
  }

  @Test
  void whenCreateAndFindCountry_returnNewCreatedCountry() {

    final var countryEntityTest = createCountryEntityTest();
    Mockito.when(countryReactiveRepository.findById(countryEntityTest.getId()))
        .thenReturn(Mono.just(countryEntityTest));

    StepVerifier.create(countryMongoReactiveRepository.findById(countryEntityTest.getId()))
        .consumeNextWith(
            countryFound -> {
              assertEquals(
                  countryEntityTest.getId(), countryFound.id(), "This value should be equal");
              assertEquals(
                  countryEntityTest.getName(), countryFound.name(), "This value should be equal");
              assertEquals(
                  countryEntityTest.getAbbreviation(),
                  countryFound.abbreviation(),
                  "This value should be equal");
            })
        .verifyComplete();
  }

  @Test
  void whenFindNonexistentCountry_returnEmpty() {
    final var notFoundID = "AZ99";
    Mockito.when(countryReactiveRepository.findById(notFoundID)).thenReturn(Mono.empty());
    StepVerifier.create(countryMongoReactiveRepository.findById(notFoundID)).verifyComplete();
  }

  @Test
  void whenCreateAndFindAllCountry_returnAllNewCreatedCountry() {

    final var countryEntityTest = createCountryEntityTest();
    final var anotherCountryEntityTest = createAnotherCountryEntityTest();

    Mockito.when(countryReactiveRepository.findAll())
        .thenReturn(Flux.just(countryEntityTest, anotherCountryEntityTest));

    StepVerifier.create(countryMongoReactiveRepository.findAll())
        .consumeNextWith(
            countryFound -> {
              assertEquals(
                  countryEntityTest.getId(), countryFound.id(), "This value should be equal");
              assertEquals(
                  countryEntityTest.getName(), countryFound.name(), "This value should be equal");
              assertEquals(
                  countryEntityTest.getAbbreviation(),
                  countryFound.abbreviation(),
                  "This value should be equal");
            })
        .consumeNextWith(
            anotherCountryFound -> {
              assertEquals(
                  anotherCountryEntityTest.getId(),
                  anotherCountryFound.id(),
                  "This value should be equal");
              assertEquals(
                  anotherCountryEntityTest.getName(),
                  anotherCountryFound.name(),
                  "This value should be equal");
              assertEquals(
                  anotherCountryEntityTest.getAbbreviation(),
                  anotherCountryFound.abbreviation(),
                  "This value should be equal");
            })
        .verifyComplete();
  }

  @Test
  void whenFindAllNonexistentCountry_returnEmpty() {
    Mockito.when(countryReactiveRepository.findAll()).thenReturn(Flux.empty());
    StepVerifier.create(countryMongoReactiveRepository.findAll()).verifyComplete();
  }
}
