package br.com.tco.countryapi.application.dataproviders.mongo;

import br.com.tco.countryapi.application.dataproviders.mongo.entity.CountryEntity;
import br.com.tco.countryapi.domain.country.entities.Country;
import br.com.tco.countryapi.domain.country.usecase.port.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class CountryMongoReactiveRepository implements CountryRepository {

  final CountryReactiveRepository countryReactiveRepository;

  @Override
  public Mono<Country> create(@NonNull Country country) {
    final var entity = new CountryEntity(country);
    final var countryEntity = countryReactiveRepository.save(entity);
    return countryEntity.map(CountryEntity::toCountry);
  }

  @Override
  public Mono<Country> findById(@NonNull String id) {
    return countryReactiveRepository.findById(id).map(CountryEntity::toCountry);
  }

  @Override
  public Flux<Country> findAll() {
    return countryReactiveRepository.findAll().map(CountryEntity::toCountry);
  }
}
