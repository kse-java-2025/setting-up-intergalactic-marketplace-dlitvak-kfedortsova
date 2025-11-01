package domain.cart;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Cart {
    String id;
    List<CartItem> items;

}
