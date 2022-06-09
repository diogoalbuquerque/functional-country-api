package br.com.tco.countryapi.domain.country.usecase;

import br.com.tco.countryapi.domain.country.entities.Country;
import br.com.tco.countryapi.domain.country.usecase.port.CountryRepository;
import br.com.tco.countryapi.domain.country.usecase.validator.CountryValidatorImpl;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SaveCountry {
  final CountryValidatorImpl countryValidatorImpl;
  final CountryRepository countryRepository;

  public Mono<Country> createCountry(Country country) {
    return countryValidatorImpl.validate(country).flatMap(countryRepository::create);
  }
}
