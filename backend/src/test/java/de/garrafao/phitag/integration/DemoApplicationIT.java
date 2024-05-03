package de.garrafao.phitag.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration")
public class DemoApplicationIT {

    @Test
    public void it_demo() {
        assert true;
    }
}
