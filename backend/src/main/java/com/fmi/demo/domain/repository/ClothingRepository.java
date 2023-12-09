package com.fmi.demo.domain.repository;

import com.fmi.demo.domain.model.Clothing;

import java.util.List;
import java.util.Optional;

public interface ClothingRepository {
    String save(Clothing clothing);

    void delete(String id);

    Optional<Clothing> getById(String id);

    boolean existsById(String id);

    List<Clothing> getAll(String userId);
}
