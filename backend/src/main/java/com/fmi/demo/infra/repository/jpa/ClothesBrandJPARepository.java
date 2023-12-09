package com.fmi.demo.infra.repository.jpa;

import com.fmi.demo.infra.jpa.ClothesBrandJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesBrandJPARepository extends JpaRepository<ClothesBrandJPA,String>{
}
