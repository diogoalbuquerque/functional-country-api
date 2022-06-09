package br.com.tco.countryapi.domain.exception;

import lombok.Getter;

@Getter
public class BaseDomainException extends RuntimeException {

  private final Integer errorCode;

  public BaseDomainException(Integer errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }
}
