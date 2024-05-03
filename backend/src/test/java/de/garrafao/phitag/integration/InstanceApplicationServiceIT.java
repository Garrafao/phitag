package de.garrafao.phitag.integration;

import de.garrafao.phitag.application.authentication.AuthenticationApplicationService;
import de.garrafao.phitag.application.authentication.data.AuthenticateUserCommand;
import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.instance.InstanceApplicationService;
import de.garrafao.phitag.application.instance.lexsubinstance.LexSubInstanceApplicationService;
import de.garrafao.phitag.application.instance.usepairinstance.UsePairInstanceApplicationService;
import de.garrafao.phitag.application.instance.userankinstance.UseRankInstanceApplicationService;
import de.garrafao.phitag.application.instance.userankpairinstance.UseRankPairInstanceApplicationService;
import de.garrafao.phitag.application.instance.userankrelative.UseRankRelativeInstanceApplicationService;
import de.garrafao.phitag.application.instance.wssiminstance.WSSIMInstanceApplicationService;
import de.garrafao.phitag.domain.instance.usepairinstance.UsePairInstance;
import de.garrafao.phitag.domain.instance.userankinstance.UseRankInstance;
import de.garrafao.phitag.domain.instance.userankrelative.UseRankRelativeInstance;
import de.garrafao.phitag.domain.phase.Phase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ActiveProfiles("integration")
public class InstanceApplicationServiceIT {
    /**
     * Before all tests, "log in" as "user-0" with password "Password1234!" and get
     * the JWT token.
     */
        @Autowired
        private AuthenticationApplicationService authenticationApplicationService;

        @Autowired
        private UsePairInstanceApplicationService usePairInstanceApplicationService;

        @Autowired
        private UseRankRelativeInstanceApplicationService useRankRelativeInstanceApplicationService;

        @Autowired
        private UseRankInstanceApplicationService useRankInstanceApplicationService;

        @Autowired
        private UseRankPairInstanceApplicationService useRankPairInstanceApplicationService;

        @Autowired
        private LexSubInstanceApplicationService lexSubInstanceApplicationService;

        @Autowired
        private WSSIMInstanceApplicationService wssimInstanceApplicationService;

        @Autowired
        private InstanceApplicationService instanceApplicationService;

        @Autowired
        private CommonService commonService;


    private static String token;


    @BeforeEach
    public void before() {
        AuthenticateUserCommand authenticateUserCommand = new AuthenticateUserCommand("user-0", "Password1234!");
        InstanceApplicationServiceIT.token = authenticationApplicationService
                .authenticateUser(authenticateUserCommand)
                .getAuthenticationToken();
    }



    @Test
    public void testFetchAllUsePairInstancesByPhase() {
        final String owner = "user-0";
        final String project = "project-0";
        final String phaseId = "phase-0";

        // Fetch the Phase using the common service
        final Phase phase = this.commonService.getPhase(owner, project, phaseId);

        List<UsePairInstance> usePairInstances =usePairInstanceApplicationService.findByPhase(phase);
        assertEquals(3, usePairInstances.size());

    }


    @Test
    public void testFetchAllUseRankInstancesByPhase() {
        final String owner = "user-0";
        final String project = "project-0";
        final String phaseId = "phase-0";

        // Fetch the Phase using the common service
        final Phase phase = this.commonService.getPhase(owner, project, phaseId);

        List<UseRankInstance> instances =useRankInstanceApplicationService.findByPhase(phase);

        assertEquals(2, instances.size());

    }
    @Test
    public void testFetchAllUseRankRelativeInstancesByPhase() {
        final String owner = "user-0";
        final String project = "project-0";
        final String phaseId = "phase-0";

        // Fetch the Phase using the common service
        final Phase phase = this.commonService.getPhase(owner, project, phaseId);

        List<UseRankRelativeInstance> instances =useRankRelativeInstanceApplicationService.findByPhase(phase);

        assertEquals(2, instances.size());

    }

    @Test
    public void testFindUsePairInstanceByPhasePaged() {
        final String owner = "user-0";
        final String project = "project-0";
        final String phaseId = "phase-0";

        final Phase phase = this.commonService.getPhase(owner, project, phaseId);
        // Call the method under test
        Page<UsePairInstance> result = usePairInstanceApplicationService.findByPhasePaged(phase, 1,1, "id");

        Assertions.assertNotNull(result);


    }

    @Test
    public void testFindUseRankInstanceByPhasePaged() {
        final String owner = "user-0";
        final String project = "project-0";
        final String phaseId = "phase-0";

        final Phase phase = this.commonService.getPhase(owner, project, phaseId);
        // Call the method under test
        Page<UseRankInstance> result = useRankInstanceApplicationService.findByPhasePaged(phase, 1,1, "id");

        Assertions.assertNotNull(result);
    }

    @Test
    public void testFindUseRankRelativeInstanceByPhasePaged() {
        final String owner = "user-0";
        final String project = "project-0";
        final String phaseId = "phase-0";

        final Phase phase = this.commonService.getPhase(owner, project, phaseId);
        // Call the method under test
        Page<UseRankRelativeInstance> result = useRankRelativeInstanceApplicationService.findByPhasePaged(phase, 1,1, "id");

        Assertions.assertNotNull(result);


    }




}
