package com.example.ShopWeb.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Id;

public class ProductItemRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRelation;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private ProductItem productItem;
}
