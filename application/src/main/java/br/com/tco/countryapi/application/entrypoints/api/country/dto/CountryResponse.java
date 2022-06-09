package br.com.tco.countryapi.application.entrypoints.api.country.dto;

import br.com.tco.countryapi.domain.country.entities.Country;


public record CountryResponse(String id, String name, String abbreviation){
    public CountryResponse(Country country){
        this(country.id(), country.name(), country.abbreviation());
    }
}
