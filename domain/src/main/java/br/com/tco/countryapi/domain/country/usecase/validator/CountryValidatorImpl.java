package br.com.tco.countryapi.domain.country.usecase.validator;

import br.com.tco.countryapi.domain.country.entities.Country;
import br.com.tco.countryapi.domain.country.usecase.exception.CountryRequestInvalidException;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class CountryValidatorImpl implements CountryValidator {
  @Override
  public Mono<Country> validate(Country country) {

    return Mono.justOrEmpty(country)
        .switchIfEmpty(
            Mono.defer(
                () -> Mono.error(new CountryRequestInvalidException("Country could not be null"))))
        .filter(c -> Objects.nonNull(c.name()))
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new CountryRequestInvalidException("Country name could not be null"))))
        .filter(c -> Objects.nonNull(c.abbreviation()))
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new CountryRequestInvalidException(
                            "Country abbreviation could not be null"))))
        .filter(c -> c.abbreviation().length() == 2)
        .switchIfEmpty(
            Mono.defer(
                () ->
                    Mono.error(
                        new CountryRequestInvalidException(
                            "Country abbreviation length must be equal to 2"))));
  }
}
