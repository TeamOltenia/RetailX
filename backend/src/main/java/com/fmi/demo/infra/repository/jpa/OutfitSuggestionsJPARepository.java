package com.fmi.demo.infra.repository.jpa;

import com.fmi.demo.infra.jpa.OutfitSuggestionsJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutfitSuggestionsJPARepository extends JpaRepository<OutfitSuggestionsJPA,String> {
}
