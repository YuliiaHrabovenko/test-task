package com.test.task.repository;

import com.test.task.entity.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LectorRepository extends JpaRepository<Lector, Long> {
    @Query(value = "select l from Lector l where l.firstName like %:template% or l.lastName like %:template%")
    List<Lector> getLectorsByTemplate(@Param("template") String template);
}
