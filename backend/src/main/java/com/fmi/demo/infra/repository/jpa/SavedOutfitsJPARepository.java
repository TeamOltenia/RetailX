package com.fmi.demo.infra.repository.jpa;

import com.fmi.demo.infra.jpa.SavedOutfitsJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedOutfitsJPARepository extends JpaRepository<SavedOutfitsJPA,String> {

List<SavedOutfitsJPA> findSavedOutfitsJPAByUserIdEquals(String userId);
}
