package br.com.tco.countryapi.domain.country.usecase.exception;

import br.com.tco.countryapi.domain.exception.BaseDomainException;

public class CountryRequestInvalidException extends BaseDomainException {

  protected static final int COUNTRY_REQUEST_ERROR_CODE = 1;

  public CountryRequestInvalidException(String message) {
    super(COUNTRY_REQUEST_ERROR_CODE, message);
  }
}
