package dto.product;

import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
public class ProductEntry {
    // no need for validation as we trust what we send as a response
    UUID id;
    String name;
    Double price;
    String description;
    List<String> category;
}
