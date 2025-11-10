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

@Mapper(componentModel = "spring")
public interface ProductMapper {
    // mapping Product to ProductEntry
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

    // mapping DTO (ProductDTO) to Product
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "category", qualifiedByName = "toCategoryEnum")
    Product toProduct(ProductDTO productDTO);

    @Named("toCategoryEnum")
    default CosmicCategory toCategoryEnum(String category) {
        try {
            return CosmicCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

    // Product List to ProductListDTO
    default ProductListDTO toProductListDTO(List<Product> products) {
        List<ProductEntry> entries = products.stream().map(this::toProductEntry).toList();
        return ProductListDTO.builder().products(entries).build();
    }

    List<ProductEntry> toProductEntry(List<Product> product);
}
