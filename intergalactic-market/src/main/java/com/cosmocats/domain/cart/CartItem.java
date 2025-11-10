package com.cosmocats.domain.cart;

import com.cosmocats.domain.product.Product;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CartItem {
    Product product;
    int quantity;
}
