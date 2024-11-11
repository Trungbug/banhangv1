package com.example.ShopWeb.DTO;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItemDTO {
    private Long id;
    private String nameItem;
}
