package validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;

public class CosmicWordCheckValidator implements ConstraintValidator<CosmicWordCheck, String> {

    private final List<String> cosmicWords = List.of("star", "galaxy", "comet", "nebula", "planet", "meteor", "cosmos");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        String lower = value.toLowerCase();
        return cosmicWords.stream().anyMatch(lower::contains);
    }
}
