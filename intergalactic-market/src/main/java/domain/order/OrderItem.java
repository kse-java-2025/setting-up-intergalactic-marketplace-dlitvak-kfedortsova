package domain.order;

import domain.product.Product;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderItem {
    Product product;
    int quantity;
}
