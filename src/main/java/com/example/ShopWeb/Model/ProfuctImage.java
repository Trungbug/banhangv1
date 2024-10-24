package com.example.ShopWeb.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products_images")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfuctImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "prdduct_id")
    private Product product;

    @Column(name = "images_url")
    private String imagesUrl;

}
