package com.cosmocats.intergalactic_market.featuretoggle.aspect;
import com.cosmocats.intergalactic_market.featuretoggle.FeatureToggles;
import com.cosmocats.intergalactic_market.featuretoggle.annotation.FeatureToggle;
import com.cosmocats.intergalactic_market.featuretoggle.exception.FeatureToggleNotEnabledException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.cosmocats.intergalactic_market.featuretoggle.FeatureToggleService;

@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Around("@annotation(featureToggle)")
    public Object checkFeatureToggleAnnotation(
            ProceedingJoinPoint proceedingJoinPoint, FeatureToggle featureToggle
    ) throws Throwable {
        return checkFeatureToggle(proceedingJoinPoint, featureToggle);
    }

    public Object checkFeatureToggle(
            ProceedingJoinPoint proceedingJoinPoint, FeatureToggle featureToggle
    ) throws Throwable {
        FeatureToggles feature = featureToggle.value();

        if (featureToggleService.check(feature)) {
            return proceedingJoinPoint.proceed();
        } else {
            throw new FeatureToggleNotEnabledException(feature.getFeatureName());
        }
    }
}
