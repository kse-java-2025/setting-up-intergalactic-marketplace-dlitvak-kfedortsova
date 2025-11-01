package domain.product;

import domain.category.Category;
import lombok.Value;

@Value
public class Product {
    String id;
    String name;
    Double price;
    String description;
    Category category;
}
