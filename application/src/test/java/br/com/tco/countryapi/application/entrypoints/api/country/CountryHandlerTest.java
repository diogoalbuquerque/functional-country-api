package br.com.tco.countryapi.application.entrypoints.api.country;

import br.com.tco.countryapi.application.ObjectTest;
import br.com.tco.countryapi.application.entrypoints.api.country.dto.CountryRequest;
import br.com.tco.countryapi.application.entrypoints.api.country.dto.CountryResponse;
import br.com.tco.countryapi.domain.country.usecase.FindCountry;
import br.com.tco.countryapi.domain.country.usecase.SaveCountry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CountryHandlerTest extends ObjectTest {

  protected static final String BASE_URL = "http://localhost:8080";
  protected static final String V1_COUNTRY_PATH = "/v1/country";
  protected static final String V1_COUNTRY_URL = BASE_URL + V1_COUNTRY_PATH;

  static WebTestClient webClient;
  @MockBean FindCountry findCountry;
  @MockBean SaveCountry saveCountry;

  @BeforeAll
  public static void setup() {
    webClient = WebTestClient.bindToServer().baseUrl(BASE_URL).build();
  }

  @Test
  void whenGetExistentCountry_returnOkWithCountryResponse() {

    final var countryTest = createCountryTest();
    Mockito.when(findCountry.findById(countryTest.id())).thenReturn(Mono.just(countryTest));

    webClient
        .get()
        .uri(V1_COUNTRY_URL + "/" + countryTest.id())
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(countryTest.id())
        .jsonPath("$.name")
        .isEqualTo(countryTest.name())
        .jsonPath("$.abbreviation")
        .isEqualTo(countryTest.abbreviation());
  }

  @Test
  void whenGetNonexistentCountry_returnNotFound() {

    Mockito.when(findCountry.findById("AA99")).thenReturn(Mono.empty());

    webClient.get().uri(V1_COUNTRY_URL + "/" + "AA99").exchange().expectStatus().isNotFound();
  }

  @Test
  void whenGetAllExistentCountry_returnOkWithCountryResponse() {

    final var countryTest = createCountryTest();
    final var anotherCountryTest = createAnotherCountryTest();
    Mockito.when(findCountry.findAll()).thenReturn(Flux.just(countryTest, anotherCountryTest));

    webClient
        .get()
        .uri(V1_COUNTRY_URL)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(CountryResponse.class)
        .hasSize(2)
        .contains(new CountryResponse(countryTest), new CountryResponse(anotherCountryTest));
  }

  @Test
  void whenGetAllNonExistentCountry_returnOkWithoutBody() {

    Mockito.when(findCountry.findAll()).thenReturn(Flux.empty());

    webClient
        .get()
        .uri(V1_COUNTRY_URL)
        .exchange()
        .expectStatus()
        .isOk()
        .expectBodyList(CountryResponse.class)
        .hasSize(0);
  }

  @Test
  void whenPostCorrectCountryRequest_returnOkWithCountryResponse() {

    final var countryTest = createCountryTest();
    Mockito.when(saveCountry.createCountry(any())).thenReturn(Mono.just(countryTest));

    webClient
        .post()
        .uri(V1_COUNTRY_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(createCountryRequestTest())
        .exchange()
        .expectStatus()
        .isCreated()
        .expectHeader()
        .location(V1_COUNTRY_PATH + "/" + countryTest.id())
        .expectBody()
        .jsonPath("$.id")
        .isEqualTo(countryTest.id())
        .jsonPath("$.name")
        .isEqualTo(countryTest.name())
        .jsonPath("$.abbreviation")
        .isEqualTo(countryTest.abbreviation());
  }

  @Test
  void whenPostCountryRequestWithoutName_returnBadRequest() {

    var incorrectCountryRequest = new CountryRequest(null, "BR");

    webClient
        .post()
        .uri(V1_COUNTRY_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(incorrectCountryRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.code")
        .isEqualTo(99)
        .jsonPath("$.violations[0].name")
        .isEqualTo("name")
        .jsonPath("$.violations[0].message")
        .isEqualTo("must not be blank");
  }

  @Test
  void whenPostCountryRequestWithoutAbbreviation_returnBadRequest() {

    var incorrectCountryRequest = new CountryRequest("Brazil", null);

    webClient
        .post()
        .uri(V1_COUNTRY_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(incorrectCountryRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.code")
        .isEqualTo(99)
        .jsonPath("$.violations[0].name")
        .isEqualTo("abbreviation")
        .jsonPath("$.violations[0].message")
        .isEqualTo("must not be blank");
  }

  @Test
  void whenPostCountryRequestWithAbbreviationLengthLessThan2_returnBadRequest() {

    var incorrectCountryRequest = new CountryRequest("Brazil", "B");

    webClient
        .post()
        .uri(V1_COUNTRY_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(incorrectCountryRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.code")
        .isEqualTo(99)
        .jsonPath("$.violations[0].name")
        .isEqualTo("abbreviation")
        .jsonPath("$.violations[0].message")
        .isEqualTo("length must be between 2 and 2");
  }

  @Test
  void whenPostCountryRequestWithAbbreviationLengthGreaterThan2_returnBadRequest() {

    var incorrectCountryRequest = new CountryRequest("Brazil", "BRA");

    webClient
        .post()
        .uri(V1_COUNTRY_URL)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(incorrectCountryRequest)
        .exchange()
        .expectStatus()
        .isBadRequest()
        .expectBody()
        .jsonPath("$.code")
        .isEqualTo(99)
        .jsonPath("$.violations[0].name")
        .isEqualTo("abbreviation")
        .jsonPath("$.violations[0].message")
        .isEqualTo("length must be between 2 and 2");
  }
}
