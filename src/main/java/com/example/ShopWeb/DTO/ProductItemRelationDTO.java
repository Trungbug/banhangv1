package com.example.ShopWeb.DTO;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemRelationDTO {
        private Long idRelation;
        private Long productId;  // ID của sản phẩm
        private Long itemId;     // ID của mẫu mã sản phẩm

}

