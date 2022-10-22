package com.tomspencerlondon.copsboot.infrastructure.security;

import static io.restassured.RestAssured.given;

import io.restassured.specification.RequestSpecification;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class SecurityHelperForRestAssured {

  private static final String UNIT_TEST_CLIENT_ID = "test-client-id";
  private static final String UNIT_TEST_CLIENT_SECRET = "test-client-secret";

  public static RequestSpecification givenAuthenticatedUser(int serverPort, String username, String password) {
    OAuth2RestTemplate template = new OAuth2RestTemplate(createResourceOwnerPasswordResourceDetails(serverPort, username, password));

    OAuth2AccessToken accessToken = template.getAccessToken();

    return given().auth().preemptive().oauth2(accessToken.getValue());
  }

  private static OAuth2ProtectedResourceDetails createResourceOwnerPasswordResourceDetails(int serverPort, String username, String password) {
    ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
    details.setAccessTokenUri(String.format("http://localhost:%s/oauth/token", serverPort));
    details.setUsername(username);
    details.setPassword(password);
    details.setClientId(UNIT_TEST_CLIENT_ID);
    details.setClientSecret(UNIT_TEST_CLIENT_SECRET);

    return details;
  }
}
