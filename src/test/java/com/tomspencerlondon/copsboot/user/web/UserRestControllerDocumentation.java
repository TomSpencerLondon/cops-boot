package com.tomspencerlondon.copsboot.user.web;

import static com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForMockMvc.HEADER_AUTHORIZATION;
import static com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForMockMvc.bearer;
import static com.tomspencerlondon.copsboot.infrastructure.security.SecurityHelperForMockMvc.obtainAccessToken;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.removeMatchingHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.tomspencerlondon.copsboot.infrastructure.test.CopsbootControllerTest;
import com.tomspencerlondon.copsboot.user.UserService;
import com.tomspencerlondon.copsboot.user.Users;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@CopsbootControllerTest(UserRestController.class)
public class UserRestControllerDocumentation {

  private MockMvc mvc;

  @MockBean
  private UserService service;

  @Autowired
  private WebApplicationContext context;
  private RestDocumentationResultHandler resultHandler;

  @BeforeEach
  public void setUp(RestDocumentationContextProvider restDocumentation) {
    resultHandler = document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint(), removeMatchingHeaders("X.*", "Pragma", "Expires")));

    mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).apply(documentationConfiguration(restDocumentation)).alwaysDo(resultHandler).build();
  }

  @Test
  void ownUserDetailsWhenNotLoggedInExample() throws Exception {
    mvc.perform(get("/api/")).andExpect(status().isUnauthorized());
  }

  @Test
  void authenticatedOfficerDetailsExample() throws Exception {
    String accessToken = obtainAccessToken(mvc, Users.OFFICER_EMAIL, Users.OFFICER_PASSWORD);

    when(service.getUser(Users.officer().getId())).thenReturn(Optional.of(Users.officer()));

    mvc.perform(get("/api/users/me")
        .header(HEADER_AUTHORIZATION, bearer(accessToken))).andExpect(status().isOk())
        .andDo(resultHandler.document(responseFields(
            fieldWithPath("id")
            .description("The unique id of the user.").optional(),
            fieldWithPath("email")
            .description("The email address of the user.").optional(),
            fieldWithPath("roles").description("The security roles of the user.").optional())));
  }
}
