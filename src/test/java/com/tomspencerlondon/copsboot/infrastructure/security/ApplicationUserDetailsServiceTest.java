package com.tomspencerlondon.copsboot.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import com.tomspencerlondon.copsboot.user.UserRepository;
import com.tomspencerlondon.copsboot.user.UserRepositoryTest;
import com.tomspencerlondon.copsboot.user.Users;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(SpringProfiles.TEST)
public class ApplicationUserDetailsServiceTest {

  @Test
  void givenExistingUsername_whenLoadingUser_userIsReturned() {
    UserRepository repository = mock(UserRepository.class);
    ApplicationUserDetailsService service = new ApplicationUserDetailsService(repository);
    when(repository.findByEmailIgnoreCase(Users.OFFICER_EMAIL)).thenReturn(Optional.of(Users.officer()));

    UserDetails userDetails = service.loadUserByUsername(Users.OFFICER_EMAIL);

    assertThat(userDetails).isNotNull();
    assertThat(userDetails.getUsername()).isEqualTo(Users.OFFICER_EMAIL);
    assertThat(userDetails.getAuthorities()).extracting(GrantedAuthority::getAuthority).contains("ROLE_OFFICER");
    assertThat(userDetails).isInstanceOfSatisfying(ApplicationUserDetails.class, applicationUserDetails -> {
      assertThat(applicationUserDetails.getUserId()).isEqualTo(Users.officer().getId());
    });
  }

  @Test
  void givenNotExistingUsername_whenLoadingUser_exceptionThrown() {
    UserRepository repository = mock(UserRepository.class);
    ApplicationUserDetailsService service = new ApplicationUserDetailsService(repository);
    when(repository.findByEmailIgnoreCase(anyString()))
        .thenReturn(Optional.empty());

    assertThatThrownBy(() -> service.loadUserByUsername("i@donotexist.com"))
        .isInstanceOf(UsernameNotFoundException.class);
  }
}
