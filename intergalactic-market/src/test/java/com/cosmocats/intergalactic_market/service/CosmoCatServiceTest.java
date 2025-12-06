package com.cosmocats.intergalactic_market.service;

import com.cosmocats.intergalactic_market.featuretoggle.FeatureToggles;
import com.cosmocats.intergalactic_market.featuretoggle.FeatureToggleService;
import com.cosmocats.intergalactic_market.featuretoggle.exception.FeatureToggleNotEnabledException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CosmoCatServiceTest {

    @Autowired
    private CosmoCatService cosmoCatService;

    @MockBean
    private FeatureToggleService featureToggleService;

    @Test
    void getCosmoCats_featureDisabled_throwsException() {
        when(featureToggleService.check(FeatureToggles.COSMO_CATS)).thenReturn(false);

        assertThrows(
                FeatureToggleNotEnabledException.class,
                () -> cosmoCatService.getCosmoCats()
        );
    }

    @Test
    void getCosmoCats_featureEnabled_returnsCats() {
        when(featureToggleService.check(FeatureToggles.COSMO_CATS)).thenReturn(true);

        String result = cosmoCatService.getCosmoCats();

        assertTrue(result.contains("Cosmo Cats"));
    }
}
