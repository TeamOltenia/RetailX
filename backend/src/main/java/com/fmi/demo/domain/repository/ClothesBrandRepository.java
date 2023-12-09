package com.fmi.demo.domain.repository;

import com.fmi.demo.domain.model.ClothesBrand;

import java.util.List;
import java.util.Optional;

public interface ClothesBrandRepository {
    String save(ClothesBrand clothing);

    void delete(String id);

    Optional<ClothesBrand> getById(String id);

    boolean existsById(String id);

    List<ClothesBrand> getAll();
}
