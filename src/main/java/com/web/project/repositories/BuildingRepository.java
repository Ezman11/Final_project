package com.web.project.repositories;

import com.web.project.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface BuildingRepository extends JpaRepository<Building, String> {

    @Override
    List<Building> findAll();

    Optional<Building> findById(String id);


}