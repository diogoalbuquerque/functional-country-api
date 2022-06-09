package br.com.tco.countryapi.domain.country.usecase;

import br.com.tco.countryapi.domain.country.entities.Country;
import br.com.tco.countryapi.domain.country.usecase.port.CountryRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindCountry {
  final CountryRepository countryRepository;

  public Mono<Country> findById(String id) {
    return countryRepository.findById(id);
  }

  public Flux<Country> findAll() {
    return countryRepository.findAll();
  }
}
