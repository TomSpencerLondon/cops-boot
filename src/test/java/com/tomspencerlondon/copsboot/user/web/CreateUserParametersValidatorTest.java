package com.tomspencerlondon.copsboot.user.web;
import static com.tomspencerlondon.copsboot.util.test.ConstraintViolationSetAssert.assertThat;
import static org.mockito.Mockito.when;

import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import com.tomspencerlondon.copsboot.user.User;
import com.tomspencerlondon.copsboot.user.UserId;
import com.tomspencerlondon.copsboot.user.UserService;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(SpringProfiles.TEST)
class CreateUserParametersValidatorTest {

  @MockBean
  private UserService userService;
  @Autowired
  private PasswordEncoder encoder;
  @Autowired
  private ValidatorFactory factory;

  @Test
  void invalidIfAlreadyUserWithGivenEmail() {
    String email = "wim.deblauwe@example.com";

    when(userService.findUserByEmail(email))
        .thenReturn(Optional.of(User.createOfficer(new UserId(UUID.randomUUID()),
            email, encoder.encode("testing1234"))
        ));

    Validator validator = factory.getValidator();

    CreateOfficerParameters userParameters = new CreateOfficerParameters();
    userParameters.setEmail(email);
    userParameters.setPassword("my-secret-pwd-1234");
    Set<ConstraintViolation<CreateOfficerParameters>> violationSet = validator.validate(userParameters);

    assertThat(violationSet).hasViolationSize(2)
        .hasViolationOnPath("email");
  }

  @Test
  void vlaidIfNoUserWithGivenEmail() {
    String email = "wim.deblauwe@example.com";
    when(userService.findUserByEmail(email))
        .thenReturn(Optional.empty());

    Validator validator = factory.getValidator();

    CreateOfficerParameters userParameters = new CreateOfficerParameters();
    userParameters.setEmail(email);
    userParameters.setPassword("my-secret-pwd-1234");
    Set<ConstraintViolation<CreateOfficerParameters>> violationSet = validator.validate(userParameters);

    assertThat(violationSet).hasNoViolations();
  }
}