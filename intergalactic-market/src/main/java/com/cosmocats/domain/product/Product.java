package com.cosmocats.domain.product;

import com.cosmocats.common.CosmicCategory;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class Product {
    UUID id;
    String name;
    Double price;
    String description;
    List<CosmicCategory> category;
}
