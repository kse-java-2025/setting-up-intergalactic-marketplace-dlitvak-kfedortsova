package com.cosmocats.domain.cart;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Cart {
    UUID id;
    List<CartItem> items;
}
