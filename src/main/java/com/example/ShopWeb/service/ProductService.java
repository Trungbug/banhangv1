package com.example.ShopWeb.service;

import com.example.ShopWeb.DTO.ProductDTO;
import com.example.ShopWeb.DTO.ProductImageDTO;
import com.example.ShopWeb.DTO.ProductItemRelationDTO;
import com.example.ShopWeb.Entity.*;
import com.example.ShopWeb.Exeption.DataNotFoundException;
import com.example.ShopWeb.Exeption.InvalidParamException;
import com.example.ShopWeb.repositoies.*;
import com.example.ShopWeb.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductItemRepository productItemRepository;
    private final ProductItemRelationRepository productItemRelationRepository;

    @Override
    @Transactional
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository
                .findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find category with id: " + productDTO.getCategoryId()));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();

        // Lưu sản phẩm
        newProduct = productRepository.save(newProduct);

        // Lưu các ProductItemRelation
        saveProductItemRelations(newProduct, productDTO.getItems());

        return newProduct;
    }

    @Override
    public Product getProductById(long productId) throws Exception {
        return productRepository.findById(productId).orElseThrow(() ->
                new DataNotFoundException("Can't find product with id: " + productId));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(ProductResponse::fromProduct);
    }

    @Override
    @Transactional
    public Product updateProduct(long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(
                        "Cannot find category with id: " + productDTO.getCategoryId()));

        existingProduct.setName(productDTO.getName());
        existingProduct.setCategory(existingCategory);
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setThumbnail(productDTO.getThumbnail());

        // Xóa các quan hệ ProductItemRelation cũ
        productItemRelationRepository.deleteByProductId(existingProduct.getId());

        // Cập nhật lại các ProductItemRelation
        saveProductItemRelations(existingProduct, productDTO.getItems());

        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImage createProductImage(Long productId, ProductImageDTO productImageDTO) throws Exception {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() ->
                new DataNotFoundException("Cannot find product with id: " + productImageDTO.getProductId()));

        int currentImageCount = productImageRepository.findByProductId(productId).size();
        if (currentImageCount >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new InvalidParamException("Number of images must be <= 5");
        }

        ProductImage newProductImage = ProductImage.builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        return productImageRepository.save(newProductImage);
    }

    // Phương thức riêng để lưu các ProductItemRelation
    private void saveProductItemRelations(Product product, List<ProductItemRelationDTO> itemRelations) throws DataNotFoundException {
        if (itemRelations != null) {
            List<ProductItemRelation> productItemRelations = new ArrayList<>();
            for (ProductItemRelationDTO relationDTO : itemRelations) {
                ProductItem productItem = productItemRepository.findById(relationDTO.getItemId())
                        .orElseThrow(() -> new DataNotFoundException(
                                "Cannot find item with id: " + relationDTO.getItemId()));

                ProductItemRelation productItemRelation = ProductItemRelation.builder()
                        .product(product)
                        .productItem(productItem)
                        .build();
                productItemRelations.add(productItemRelation);
            }
            productItemRelationRepository.saveAll(productItemRelations);
        }
    }
}