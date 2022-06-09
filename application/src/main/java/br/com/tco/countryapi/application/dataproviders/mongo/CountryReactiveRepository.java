package br.com.tco.countryapi.application.dataproviders.mongo;

import br.com.tco.countryapi.application.dataproviders.mongo.entity.CountryEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryReactiveRepository extends ReactiveCrudRepository<CountryEntity, String> {}
