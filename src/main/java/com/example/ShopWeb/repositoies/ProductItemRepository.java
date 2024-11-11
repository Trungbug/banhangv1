package com.example.ShopWeb.repositoies;

import com.example.ShopWeb.Entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

}
