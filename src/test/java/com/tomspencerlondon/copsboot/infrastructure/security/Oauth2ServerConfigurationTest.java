package com.tomspencerlondon.copsboot.infrastructure.security;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import com.tomspencerlondon.copsboot.user.UserService;
import com.tomspencerlondon.copsboot.user.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(SpringProfiles.TEST)
public class Oauth2ServerConfigurationTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private UserService userService;

  @Test
  void testGetAccessTokenAsOfficer() throws Exception {
    userService.createOfficer(Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);

    String clientId = "test-client-id";
    String clientSecret = "test-client-secret";

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    params.add("grant_type", "password");
    params.add("client_id", clientId);
    params.add("client_secret", clientSecret);
    params.add("username", Users.OFFICER_EMAIL);
    params.add("password", Users.OFFICER_PASSWORD);

    mvc.perform(post("/oauth/token")
        .params(params)
        .with(httpBasic(clientId, clientSecret))
        .accept("application/json;charset=UTF-8"))
        .andExpect(status().isOk())
        .andExpect(content().contentType("application/json;charset=UTF-8"))
        .andDo(print())
        .andExpect(jsonPath("access_token").isString())
        .andExpect(jsonPath("token_type").value("bearer"))
        .andExpect(jsonPath("refresh_token").isString())
        .andExpect(jsonPath("expires_in").isNumber())
        .andExpect(jsonPath("scope").value("mobile_app"));
  }
}
