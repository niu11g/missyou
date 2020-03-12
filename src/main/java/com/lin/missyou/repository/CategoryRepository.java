package com.lin.missyou.repository;

import com.lin.missyou.model.Banner;
import com.lin.missyou.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findAllByIsRootOrderByIndexAsc(Boolean isRoot);

}
