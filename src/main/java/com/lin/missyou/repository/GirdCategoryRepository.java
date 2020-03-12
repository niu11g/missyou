package com.lin.missyou.repository;

import com.lin.missyou.model.GridCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GirdCategoryRepository extends JpaRepository<GridCategory,Long> {
}
