package com.web.project.entities;

import jdk.jfr.Unsigned;
import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "check_in")
public class Check_in {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "student_id")
    private String 	student_id;

    @Column(name = "building_id")
    private String building_id;

    @Column(name = "temperature")
    private Float temperature;

    @Column(name = "time")
    private String time;

}

