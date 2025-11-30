package com.cosmocats.intergalactic_market.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;

@Data
@Component
@ConfigurationProperties(prefix = "feature")
public class FeatureToggleProperties {
    private Map<String, Boolean> toggles = new HashMap<>();

    public boolean check(String featureToggle) {
        return toggles.getOrDefault(featureToggle, false);
    }
}
