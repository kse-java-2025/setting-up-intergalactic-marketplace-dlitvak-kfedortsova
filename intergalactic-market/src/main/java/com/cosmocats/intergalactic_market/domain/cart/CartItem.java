package com.cosmocats.intergalactic_market.domain.cart;

import com.cosmocats.intergalactic_market.domain.product.Product;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CartItem {
    Product product;
    int quantity;
}
