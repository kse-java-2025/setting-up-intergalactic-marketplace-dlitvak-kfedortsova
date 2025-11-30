package com.cosmocats.intergalactic_market.featuretoggle.annotation;

import com.cosmocats.intergalactic_market.featuretoggle.FeatureToggles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FeatureToggle {

    FeatureToggles value();

}
