package com.cosmocats.intergalactic_market.product;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Value;
import com.cosmocats.intergalactic_market.validators.CosmicWordCheck;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class ProductDTO {

    @NotBlank(message = "Name is required and should not be empty")
    @Size(min = 3, max = 255, message = "Name must contain from 3 to 50 characters")
    @CosmicWordCheck
    String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price cannot be negative")
    Double price;

    // @NotNull - it is optional
    @Size(min = 10, max = 300)
    String description;

    @NotNull(message = "Item should have a category")
    @Size(min = 1, message = "There must be at least one category to which item belongs")
    List<String> category;
}
