package com.cosmocats.dto.product;

import lombok.Builder;

import java.util.List;

@Builder
public class ProductListDTO {
    List<ProductEntry> products;
}
