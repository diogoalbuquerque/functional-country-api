package br.com.tco.countryapi.domain.country.usecase.validator;

import br.com.tco.countryapi.domain.country.entities.Country;
import br.com.tco.countryapi.domain.country.usecase.exception.CountryRequestInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class CountryValidatorTest {

  CountryValidator countryValidator;

  @BeforeEach
  void setup() {
    countryValidator = new CountryValidatorImpl();
  }

  @Test
  void whenNullCountry_expectException() {
    StepVerifier.create(countryValidator.validate(null))
        .expectErrorMatches(
            throwable ->
                throwable instanceof CountryRequestInvalidException
                    && throwable.getMessage().equals("Country could not be null"))
        .verify();
  }

  @Test
  void whenNullCountryName_expectException() {
    var countryTest = new Country("A1", null, "BR");
    StepVerifier.create(countryValidator.validate(countryTest))
        .expectErrorMatches(
            throwable ->
                throwable instanceof CountryRequestInvalidException
                    && throwable.getMessage().equals("Country name could not be null"))
        .verify();
  }

  @Test
  void whenNullCountryAbbreviation_expectException() {
    var countryTest = new Country("A1", "Brazil", null);
    StepVerifier.create(countryValidator.validate(countryTest))
        .expectErrorMatches(
            throwable ->
                throwable instanceof CountryRequestInvalidException
                    && throwable.getMessage().equals("Country abbreviation could not be null"))
        .verify();
  }

  @Test
  void whenCountryAbbreviationGreaterThan2_expectException() {
    var countryTest = new Country("A1", "Brazil", "BRA");
    StepVerifier.create(countryValidator.validate(countryTest))
        .expectErrorMatches(
            throwable ->
                throwable instanceof CountryRequestInvalidException
                    && throwable
                        .getMessage()
                        .equals("Country abbreviation length must be equal to 2"))
        .verify();
  }

  @Test
  void whenCountryAbbreviationLessThan2_expectException() {
    var countryTest = new Country("A1", "Brazil", "B");
    StepVerifier.create(countryValidator.validate(countryTest))
        .expectErrorMatches(
            throwable ->
                throwable instanceof CountryRequestInvalidException
                    && throwable
                        .getMessage()
                        .equals("Country abbreviation length must be equal to 2"))
        .verify();
  }
}
