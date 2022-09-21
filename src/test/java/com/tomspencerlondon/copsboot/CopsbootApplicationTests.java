package com.tomspencerlondon.copsboot;

import com.tomspencerlondon.copsboot.infrastructure.SpringProfiles;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles(SpringProfiles.TEST)
class CopsbootApplicationTests {

  @Test
  void contextLoads() {
  }

}
