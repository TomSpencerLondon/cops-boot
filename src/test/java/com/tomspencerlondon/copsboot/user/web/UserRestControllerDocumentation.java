package com.tomspencerlondon.copsboot.user.web;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeMatchingHeaders;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import com.tomspencerlondon.copsboot.infrastructure.security.OAuth2ServerConfiguration;
import com.tomspencerlondon.copsboot.infrastructure.security.SecurityConfiguration;
import com.tomspencerlondon.copsboot.infrastructure.security.StubUserDetailsService;
import com.tomspencerlondon.copsboot.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

@WebMvcTest(UserRestController.class)
@ActiveProfiles(SpringProfiles.TEST)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class UserRestControllerDocumentation {
  private MockMvc mvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserService service;

  @Autowired
  private WebApplicationContext context;
  private RestDocumentationResultHandler resultHandler;

  @BeforeEach
  public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
    resultHandler = document("{method-name", preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint(), removeMatchingHeaders("X.*", "Pragma", "Expires")));

    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(springSecurity())
        .apply(documentationConfiguration(restDocumentation))
        .alwaysDo(resultHandler)
        .build();
  }

  @Test
  void ownUserDetailsWhenNotLoggedInExample() throws Exception {
    mvc.perform(get("/api/"))
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
    public TokenStore tokenStore() {
      return new InMemoryTokenStore();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityConfiguration securityConfiguration() {
      return new SecurityConfiguration();
    }
  }

}
