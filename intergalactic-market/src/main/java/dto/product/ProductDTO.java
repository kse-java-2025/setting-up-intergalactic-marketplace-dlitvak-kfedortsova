package dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import validators.CosmicWordCheck;

import java.util.List;

@Data
public class ProductDTO {

    @NotBlank(message = "Name is required and should not be empty")
    @Size(min = 3, max = 255, message = "Name must contain from 3 to 50 characters")
    @CosmicWordCheck
    private String name;

    @NotNull(message = "Price is required")
    @Positive(message = "Price cannot be negative")
    private Double price;

    // @NotNull - it is optional
    @Size(min = 10, max = 300)
    private String description;

    @NotNull(message = "Item should have a category")
    @Size(min = 1, message = "There must be at least one category to which item belongs")
    private List<String> category;
}
