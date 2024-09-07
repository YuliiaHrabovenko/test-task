package com.test.task.repository;

import com.test.task.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String departmentName);

    @Query(value = "select d.departmentHead from Department d where d.name = ?1")
    Optional<String> getDepartmentHeadByDepartmentName(String departmentName);

    @Query(nativeQuery = true,
            value = "select count(l.id)\n" +
                    "from lector l\n" +
                    "         join department_lector dl on l.id = dl.lector_id\n" +
                    "         join department d on d.id = dl.department_id\n" +
                    "where d.name = ?1")
    Integer countEmployeesByDepartmentName(String departmentName);
}
