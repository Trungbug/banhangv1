package com.example.ShopWeb.repositoies;

import com.example.ShopWeb.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
