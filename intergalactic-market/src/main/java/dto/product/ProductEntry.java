package dto.product;

import java.util.List;
import java.util.UUID;

public class ProductEntry {
    // no need for validation as we trust what we send as a response
    UUID id;
    String name;
    Double price;
    String description;
    List<String> category;
}
