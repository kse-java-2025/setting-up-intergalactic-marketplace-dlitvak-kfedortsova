package com.cosmocats.intergalactic_market.featuretoggle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeatureToggles {
    COSMO_CATS("cosmo-cats"),
    KITTY_PRODUCTS("kitty-products");

    private final String featureName;
}
