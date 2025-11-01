package domain.product;

import domain.category.Category;
import lombok.Value;

import java.util.List;

@Value
public class Product {
    String id;
    String name;
    Double price;
    String description;
    List<Category> category;
}
