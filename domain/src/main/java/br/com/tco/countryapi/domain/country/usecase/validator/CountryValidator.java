package br.com.tco.countryapi.domain.country.usecase.validator;

import br.com.tco.countryapi.domain.country.entities.Country;
import reactor.core.publisher.Mono;

public interface CountryValidator {
  Mono<Country> validate(Country country);
}
