package service.mapper;

import domain.category.Category;
import domain.product.Product;
import dto.product.ProductDTO;
import dto.product.ProductEntry;
import dto.product.ProductListDTO;
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
    default List<String> toCategoryString(List<Category> category) {
        return category.stream().map(cat -> cat.getName().toLowerCase()).toList();
    }

    // mapping DTO (ProductDTO) to Product
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toProduct(ProductDTO productDTO);

    // Product List to ProductListDTO
    default ProductListDTO toProductListDTO(List<Product> products) {
        List<ProductEntry> entries = products.stream().map(this::toProductEntry).toList();
        return ProductListDTO.builder().products(entries).build();
    }

    List<ProductEntry> toProductEntry(List<Product> product);
}
