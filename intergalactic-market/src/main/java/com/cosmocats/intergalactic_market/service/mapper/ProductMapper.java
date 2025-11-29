package com.cosmocats.intergalactic_market.service.mapper;

import com.cosmocats.intergalactic_market.common.CosmicCategory;
import com.cosmocats.intergalactic_market.domain.product.Product;
import com.cosmocats.intergalactic_market.product.ProductDTO;
import com.cosmocats.intergalactic_market.product.ProductEntry;
import com.cosmocats.intergalactic_market.product.ProductListDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = UUID.class)
public interface ProductMapper {

    @Mapping(target = "category", source = "category", qualifiedByName = "toCategoryString")
    ProductEntry toProductEntry(Product product);

    @Named("toCategoryString")
    default List<String> toCategoryString(List<CosmicCategory> category) {
        if (category == null) return List.of();
        return category.stream()
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }

    default ProductListDTO toProductListDTO(List<Product> products) {
        List<ProductEntry> entries = products.stream().map(this::toProductEntry).toList();
        return ProductListDTO.builder().products(entries).build();
    }

    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    @Mapping(target = "category", source = "category", qualifiedByName = "toCategoryEnum")
    Product toProduct(ProductDTO productDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category", qualifiedByName = "toCategoryEnum")
    Product toProductForUpdate(ProductDTO productDTO);

    @Named("toCategoryEnum")
    default List<CosmicCategory> toCategoryEnum(List<String> categories) {
        if (categories == null) return List.of();
        return categories.stream()
                .map(c -> CosmicCategory.valueOf(c.toUpperCase()))
                .toList();
    }
}
