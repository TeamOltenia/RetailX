package com.fmi.demo.infra.repository.jpa;

import com.fmi.demo.infra.jpa.ClothingJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothingJPARepository extends JpaRepository<ClothingJPA,String> {

    List<ClothingJPA> findClothingJPAByUserIdEquals(String userId);
}
