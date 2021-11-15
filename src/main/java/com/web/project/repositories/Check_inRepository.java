package com.web.project.repositories;

import com.web.project.entities.Check_in;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface Check_inRepository extends JpaRepository<Check_in, Long> {

    @Override
    List<Check_in> findAll();

    Optional<Check_in> findById(Long id);

    @Query(value ="SELECT DISTINCT c.student_id FROM Check_in c " +
            "WHERE DATE_FORMAT(c.time, \"%Y-%m-%d\") = ?1 AND c.building_id IN( SELECT DISTINCT building_id FROM Check_in WHERE student_id = ?2 ) AND student_id != ?2 " ,nativeQuery = true)
    List<String> findByTime(String time,String id);


}