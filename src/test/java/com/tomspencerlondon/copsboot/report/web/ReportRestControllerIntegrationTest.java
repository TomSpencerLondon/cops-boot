package com.tomspencerlondon.copsboot.report.web;

import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForRestAssured;
import com.tomspencerlondon.copsboot.user.UserService;
import com.tomspencerlondon.copsboot.user.Users;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles(SpringProfiles.TEST)
public class ReportRestControllerIntegrationTest {

  @LocalServerPort
  private int serverport;

  @Autowired
  private UserService userService;

  @Before
  public void setUp() {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
  }

  @Test
  void officerIsUnableToPostAReportIfFileSizeIsTooBig() {
    userService.createOfficer(Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);

    String dateTime = "2018-04-11T22:59:03.189+02:00";
    String description = "The suspect is wearing a black hat.";

    SecurityHelperForRestAssured.givenAuthenticatedUser(serverport, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD)
        .when()
        .multiPart("image", new MultiPartSpecBuilder(new byte[2_000_000])
            .fileName("picture.png")
            .mimeType("image/png")
            .build())
        .formParam("dateTime", dateTime)
        .formParam("description", description)
        .port(serverport)
        .post("/api/reports")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value());

  }
}


