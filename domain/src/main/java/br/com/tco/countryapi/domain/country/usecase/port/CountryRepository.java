package br.com.tco.countryapi.domain.country.usecase.port;

import br.com.tco.countryapi.domain.country.entities.Country;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CountryRepository {
  Mono<Country> create(Country country);

  Mono<Country> findById(String id);

  Flux<Country> findAll();
}
