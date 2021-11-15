package com.web.project.entities;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "building")
public class Building {

    @Id
    @Column(name = "building_id")
    private String building_id;

    @Column(name = "name")
    private String name;

    @Column(name = "danger")
    private int danger;

}
