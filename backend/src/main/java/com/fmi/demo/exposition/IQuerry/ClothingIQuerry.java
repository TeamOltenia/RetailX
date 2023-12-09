package com.fmi.demo.exposition.IQuerry;

import com.fmi.demo.domain.model.Clothing;

import java.util.List;

public interface ClothingIQuerry {

    Clothing getById(String id);
    List<Clothing> getAll(String userId);

}
