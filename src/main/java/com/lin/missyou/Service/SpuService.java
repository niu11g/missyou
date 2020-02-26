package com.lin.missyou.Service;

import com.lin.missyou.model.Spu;
import com.lin.missyou.repository.SpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpuService {
    @Autowired
    SpuRepository spuRepository;

    public Spu getSpuById(Long id){
        return this.spuRepository.findOneById(id);
    }

    public List<Spu> getLatestPagingSpu(){
        return this.spuRepository.findAll();
    }
}
