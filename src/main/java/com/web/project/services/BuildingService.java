package com.web.project.services;

import com.web.project.entities.Building;
import io.micrometer.core.instrument.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class BuildingService {

    @Autowired
    BuildingService buildingService;

    @Transactional
    public Building save(Building building) {
        building.setDanger(0);
        return buildingService.save(building);
    }

    public List<Building> findAll() {
        return buildingService.findAll();
    }

    @Transactional
    public Building delete(Building building) {
        return buildingService.delete(building);
    }



}
