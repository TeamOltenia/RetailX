package com.fmi.demo.domain.repository;

import com.fmi.demo.domain.model.OutfitSuggestions;
import com.fmi.demo.domain.model.SavedOutfits;

import java.util.List;
import java.util.Optional;

public interface SavedOutfitsRepository {

    String save(SavedOutfits clothing);

    void delete(String id);

    Optional<SavedOutfits> getById(String id);

    boolean existsById(String id);

    List<SavedOutfits> getAll( String userId);

    void deleteClothingApparnces(String userId, String clothinId);
}
