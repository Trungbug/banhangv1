package com.example.ShopWeb.repositoies;

import com.example.ShopWeb.Entity.ProductItemRelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductItemRelationRepository extends JpaRepository<ProductItemRelation, Long> {
    List<ProductItemRelation> findByProductId(Long id);
    void deleteByProductId(Long productId);
}
