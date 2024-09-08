package com.test.task.service;

import com.test.task.entity.Degree;
import com.test.task.entity.Department;
import com.test.task.entity.Lector;
import com.test.task.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    private static Department mathDepartment;

    @BeforeAll
    static void beforeAll() {
        mathDepartment = Department.builder()
                .departmentHead("Sam Wilson")
                .name("Mathematics")
                .build();
        Lector assistant = Lector.builder()
                .degree(Degree.ASSISTANT)
                .salary(5000)
                .firstName("Jack")
                .lastName("Lee")
                .departments(List.of(mathDepartment))
                .build();
        Lector professor = Lector.builder()
                .degree(Degree.PROFESSOR)
                .salary(8000)
                .firstName("Mary")
                .lastName("Watson")
                .departments(List.of(mathDepartment))
                .build();
        mathDepartment.setLectors(List.of(professor, assistant));
    }

    @Test
    void givenDepartmentName_whenGetDepartmentStatistics_thenReturnStatisticsByDepartment() {
        String depName = "English";
        when(departmentRepository.findByName(depName)).thenReturn(Optional.of(mathDepartment));

        Map<Degree, Long> actual = departmentService.getDepartmentStatistics(depName);
        Map<Degree, Long> expected = Map.of(Degree.ASSISTANT, 1L, Degree.PROFESSOR, 1L);

        assertThat(actual).hasSize(2);
        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void givenDepartmentName_whenGetDepartmentAverageSalary_thenReturnAverageSalary() {
        String depName = "English";
        when(departmentRepository.findByName(depName)).thenReturn(Optional.of(mathDepartment));

        Double averageSalary = departmentService.getDepartmentAverageSalary(depName);

        assertThat(averageSalary).isEqualTo(6500);
    }

    @Test
    void givenNonExistentDepartmentName_whenGetDepartmentStatistics_thenReturnEmptyStatisticsByDepartment() {
        String depName = "Biology";
        when(departmentRepository.findByName(depName)).thenReturn(Optional.empty());

        Map<Degree, Long> actual = departmentService.getDepartmentStatistics(depName);

        assertThat(actual).isEmpty();
    }

    @Test
    void givenNonExistentDepartmentName_whenGetDepartmentAverageSalary_thenReturnZeroAverageSalary() {
        String depName = "Biology";
        when(departmentRepository.findByName(depName)).thenReturn(Optional.empty());

        Double averageSalary = departmentService.getDepartmentAverageSalary(depName);

        assertThat(averageSalary).isEqualTo(0);
    }
}
