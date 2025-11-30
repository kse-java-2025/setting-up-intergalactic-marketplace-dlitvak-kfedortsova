package com.cosmocats.intergalactic_market.service;

import com.cosmocats.intergalactic_market.featuretoggle.FeatureToggles;
import com.cosmocats.intergalactic_market.featuretoggle.annotation.FeatureToggle;

public class CosmoCatService {

    @FeatureToggle(FeatureToggles.COSMO_CATS)
    public String getCosmoCats() {
        return "≽^•⩊•^≼ Cosmo Cats! ≽^• ˕ • ྀི≼ ";
    }

}
