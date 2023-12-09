package com.fmi.demo.domain.repository;

import com.fmi.demo.domain.model.Clothing;
import com.fmi.demo.domain.model.OutfitSuggestions;

import java.util.List;
import java.util.Optional;

public interface OutfitSuggestionsRepository {

    String save(OutfitSuggestions clothing);

    void delete(String id);

    Optional<OutfitSuggestions> getById(String id);

    boolean existsById(String id);

    List<OutfitSuggestions> getAll();
}
