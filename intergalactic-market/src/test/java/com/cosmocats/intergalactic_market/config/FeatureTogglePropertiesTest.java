package com.cosmocats.intergalactic_market.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
        "feature.toggles.A=true",
        "feature.toggles.B=false"
})
class FeatureTogglePropertiesTest {

    @Autowired
    private FeatureToggleProperties props;

    @Test
    void loadsPropertiesCorrectly() {
        assertTrue(props.check("A"));
        assertFalse(props.check("B"));
        assertFalse(props.check("C"));
    }
}
