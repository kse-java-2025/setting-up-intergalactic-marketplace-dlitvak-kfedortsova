package dto.product;

import jakarta.validation.constraints.*;
import lombok.Data;
import validation.CosmicWordCheck;

import java.util.List;

@Data
public class ProductDTO {

    @NotNull
    @Size(min = 3, max = 50)
    @CosmicWordCheck
    private String name;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Size(min = 10, max = 300)
    private String description;

    @NotNull
    @Size(min = 1)
    private List<String> category;
}
