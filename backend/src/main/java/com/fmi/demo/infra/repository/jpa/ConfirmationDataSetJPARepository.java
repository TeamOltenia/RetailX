package com.fmi.demo.infra.repository.jpa;

import com.fmi.demo.infra.jpa.ConfirmationDataSetJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationDataSetJPARepository extends JpaRepository<ConfirmationDataSetJPA , String> {

    List<ConfirmationDataSetJPA> findConfirmationDataSetJPAByConfirmed(Boolean confirmation );
}
