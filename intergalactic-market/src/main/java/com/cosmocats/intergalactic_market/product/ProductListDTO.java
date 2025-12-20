package com.cosmocats.intergalactic_market.product;

import lombok.Builder;

import java.util.List;

@Builder
public class ProductListDTO {
    List<ProductEntry> products;
}
