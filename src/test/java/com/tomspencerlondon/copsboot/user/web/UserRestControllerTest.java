package com.tomspencerlondon.copsboot.user.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import com.tomspencerlondon.copsboot.infrastructure.security.OAuth2ServerConfiguration;
import com.tomspencerlondon.copsboot.infrastructure.security.SecurityConfiguration;
import com.tomspencerlondon.copsboot.infrastructure.security.StubUserDetailsService;
import com.tomspencerlondon.copsboot.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles(SpringProfiles.TEST)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService service;

  @Test
  void givenNotAuthenticated_whenAskingMyDetails_forbidden() throws Exception {
    mvc.perform(get("/api/users/me"))
        .andExpect(status().isUnauthorized());
  }

  @TestConfiguration
  @Import(OAuth2ServerConfiguration.class)
  static class TestConfig {
    @Bean
    public UserDetailsService userDetailsService() {
      return new StubUserDetailsService();
    }

    @Bean
    public SecurityConfiguration securityConfiguration() {
      return new SecurityConfiguration();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
      return new InMemoryTokenStore();
    }
  }
}
