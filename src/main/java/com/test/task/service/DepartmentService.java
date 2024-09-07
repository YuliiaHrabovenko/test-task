package com.test.task.service;

import com.test.task.entity.Degree;
import com.test.task.entity.Department;
import com.test.task.entity.Lector;
import com.test.task.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public String findHeadByDepartmentName(String departmentName) {
        return departmentRepository.getDepartmentHeadByDepartmentName(departmentName).orElse("");
    }

    public Map<Degree, Long> getDepartmentStatistics(String departmentName) {
        Department department = departmentRepository.findByName(departmentName).orElseGet(Department::new);
        return department.getLectors().stream()
                .collect(Collectors.groupingBy(Lector::getDegree, Collectors.counting()));
    }

    public Double getDepartmentAverageSalary(String departmentName) {
        Department department = departmentRepository.findByName(departmentName).orElseGet(Department::new);
        return department.getLectors().stream()
                .map(Lector::getSalary)
                .collect(Collectors.averagingDouble(salary -> salary));
    }

    public Integer countEmployeesByDepartmentName(String departmentName) {
        return departmentRepository.countEmployeesByDepartmentName(departmentName);
    }

    public String getAllDepartmentNames() {
        return departmentRepository.findAll().stream()
                .map(Department::getName)
                .collect(Collectors.joining(", "));
    }
}
