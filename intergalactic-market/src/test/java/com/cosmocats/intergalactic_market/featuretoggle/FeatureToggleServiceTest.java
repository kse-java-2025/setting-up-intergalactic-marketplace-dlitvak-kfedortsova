package com.cosmocats.intergalactic_market.featuretoggle;

import com.cosmocats.intergalactic_market.config.FeatureToggleProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "feature.toggles.COSMO_CATS=true",
        "feature.toggles.TEST_FEATURE=false"
})
class FeatureToggleServiceTest {

    @Autowired
    private FeatureToggleService featureToggleService;

    @Autowired
    private FeatureToggleProperties properties;

    @Test
    void propertiesAreLoadedCorrectly() {
        assertTrue(properties.check("COSMO_CATS"));
        assertFalse(properties.check("TEST_FEATURE"));
    }

    @Test
    void serviceChecksFeatureCorrectly() {
        assertTrue(featureToggleService.check("COSMO_CATS"));
        assertFalse(featureToggleService.check("UNKNOWN"));
    }

    @Test
    void enableDisableWorks() {
        featureToggleService.enable("X");
        assertTrue(featureToggleService.check("X"));

        featureToggleService.disable("X");
        assertFalse(featureToggleService.check("X"));
    }
}
