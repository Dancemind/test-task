package com.example.testtask.repository;

import com.example.testtask.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    @Query(value = "SELECT * from section s inner join geological_class g " +
            "on s.id = g.section_id " +
            "where g.code = :code", nativeQuery = true)
    List<Section> findAllByGeologicalClassesThatContainCode(@Param("code") String code);

}
