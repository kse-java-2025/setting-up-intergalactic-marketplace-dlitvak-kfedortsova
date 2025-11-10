package com.cosmocats.intergalactic_market.domain.order;

import com.cosmocats.intergalactic_market.domain.product.Product;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class OrderItem {
    Product product;
    int quantity;
}
