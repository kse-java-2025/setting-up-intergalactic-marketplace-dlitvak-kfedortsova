package com.cosmocats.intergalactic_market.featuretoggle;

import com.cosmocats.intergalactic_market.config.FeatureToggleProperties;
import com.cosmocats.intergalactic_market.featuretoggle.annotation.FeatureToggle;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class FeatureToggleService {

    private final HashMap<String, Boolean> featureToggles;

    public FeatureToggleService(FeatureToggleProperties featureToggleProperties) {
        featureToggles = new HashMap<>(featureToggleProperties.getToggles());
    }

    public boolean check(String featureName) {
        return featureToggles.getOrDefault(featureName, false);
    }

    public boolean check(FeatureToggles featureToggle) {
        return check(featureToggle.getFeatureName());
    }

    public void enable(String featureName) {
        featureToggles.put(featureName, true);
    }

    public void disable(String featureName) {
        featureToggles.put(featureName, false);
    }
}
