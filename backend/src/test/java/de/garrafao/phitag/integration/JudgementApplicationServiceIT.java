package de.garrafao.phitag.integration;

import de.garrafao.phitag.application.authentication.AuthenticationApplicationService;
import de.garrafao.phitag.application.authentication.data.AuthenticateUserCommand;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration")
public class JudgementApplicationServiceIT {

    @Autowired
    private AuthenticationApplicationService authenticationApplicationService;



    private static String token;
    @BeforeEach
    public void before() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-0", "Password1234!");
        JudgementApplicationServiceIT.token = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();
    }



    
}
