package domain.order;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
public class Order {
    String id;
    List<OrderItem> orderItems;
    Double totalPrice;
    OrderStatus status;
    LocalDateTime orderTime;
}
