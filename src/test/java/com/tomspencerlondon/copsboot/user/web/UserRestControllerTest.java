package com.tomspencerlondon.copsboot.user.web;

import static com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForMockMvc.HEADER_AUTHORIZATION;
import static com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForMockMvc.bearer;
import static com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForMockMvc.obtainAccessToken;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import com.tomspencerlondon.copsboot.infrastructure.test.CopsbootControllerTest;
import com.tomspencerlondon.copsboot.user.UserService;
import com.tomspencerlondon.copsboot.user.Users;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@CopsbootControllerTest(UserRestController.class)
public class UserRestControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService service;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void givenNotAuthenticated_whenAskingMyDetails_forbidden() throws Exception {
    mvc.perform(get("/api/users/me"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void givenAuthenticatedAsOfficer_whenAskingMyDetails_detailsReturned() throws Exception {
    String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);

    when(service.getUser(Users.officer().getId()))
        .thenReturn(Optional.of(Users.officer()));
    mvc.perform(get("/api/users/me")
        .header(HEADER_AUTHORIZATION, bearer(accessToken))
    ).andExpect(status().isOk())
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("email").value(Users.OFFICER_EMAIL))
        .andExpect(jsonPath("roles").isArray())
        .andExpect(jsonPath("roles[0]").value("OFFICER"));
  }

  @Test
  void testCreateOfficer() throws Exception {
    String email = "wim.deblauwe@example.com";
    String password = "my-super-secret-pwd";
    String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);

    CreateOfficerParameters parameters = new CreateOfficerParameters();
    parameters.setEmail(email);
    parameters.setPassword(password);

    when(service.createOfficer(email, password)).thenReturn(Users.newOfficer(email, password));

    mvc.perform(post("/api/users")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(objectMapper.writeValueAsString(parameters)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("id").exists())
        .andExpect(jsonPath("email").value(email))
        .andExpect(jsonPath("roles").isArray())
        .andExpect(jsonPath("roles[0]").value("OFFICER"));

    verify(service).createOfficer(email, password);
  }
}
