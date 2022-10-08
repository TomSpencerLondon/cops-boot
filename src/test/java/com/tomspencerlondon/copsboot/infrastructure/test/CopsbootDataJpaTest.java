package com.tomspencerlondon.copsboot.infrastructure.test;

import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Retention(RetentionPolicy.RUNTIME)
@DataJpaTest
@ContextConfiguration(classes = CopsbootDataJpaTestConfiguration.class)
@ActiveProfiles(SpringProfiles.INTEGRATION_TEST)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public @interface CopsbootDataJpaTest {
}
