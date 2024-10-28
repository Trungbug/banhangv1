package com.example.ShopWeb.service;

import com.example.ShopWeb.DTO.ProductDTO;
import com.example.ShopWeb.DTO.ProductImageDTO;
import com.example.ShopWeb.Model.Product;
import com.example.ShopWeb.Model.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {

        Product createProduct(ProductDTO productDTO) throws Exception;
        Product getProductById(long id) throws Exception;
        Page<Product> getAllProducts(PageRequest pageRequest);
        Product updateProduct(long id, ProductDTO productDTO) throws Exception;
        void deleteProduct(long id);
        boolean existsByName(String name);
        ProductImage createProductImage(
                Long productId,
                ProductImageDTO productImageDTO) throws Exception;


}