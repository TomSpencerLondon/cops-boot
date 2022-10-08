package com.tomspencerlondon.copsboot.infrastructure.test;

import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@Retention(RetentionPolicy.RUNTIME)
@WebMvcTest
@ContextConfiguration(classes = CopsbootControllerTestConfiguration.class)
@ActiveProfiles(SpringProfiles.TEST)
public @interface CopsbootControllerTest {

  @AliasFor(annotation = WebMvcTest.class, attribute = "value") Class<?>[] value() default {};
}
