package com.cosmocats.domain.order;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Order {
    UUID id;
    List<OrderItem> orderItems;
    Double totalPrice;
    OrderStatus status;
    LocalDateTime orderTime;
}
