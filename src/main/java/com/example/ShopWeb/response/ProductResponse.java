package com.example.ShopWeb.response;

import com.example.ShopWeb.Entity.Product;
import com.example.ShopWeb.DTO.ProductItemDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse {
    private String name;
    private Float price;
    private String thumbnail;
    private String description;

    @JsonProperty("category_id")
    private Long categoryId;

    // Thêm danh sách các mẫu mã sản phẩm
    @JsonProperty("product_items")
    private List<ProductItemDTO> productItems;

    public static ProductResponse fromProduct(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .price(product.getPrice())
                .thumbnail(product.getThumbnail())
                .description(product.getDescription())
                .categoryId(product.getCategory().getId())
                .build();
    }


}
