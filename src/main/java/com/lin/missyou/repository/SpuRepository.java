package com.lin.missyou.repository;

import com.lin.missyou.model.Spu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpuRepository extends JpaRepository<Spu,Long> {
    Spu findOneById(Long id);
}
