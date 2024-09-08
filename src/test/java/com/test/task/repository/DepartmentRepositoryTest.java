package com.test.task.repository;

import com.test.task.entity.Degree;
import com.test.task.entity.Department;
import com.test.task.entity.Lector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DepartmentRepositoryTest extends BaseContainer {

    @BeforeEach
    void beforeEach() {
        Department engDepartment = Department.builder()
                .departmentHead("John Doe")
                .name("English")
                .build();
        Department mathDepartment = Department.builder()
                .departmentHead("Sam Wilson")
                .name("Mathematics")
                .build();
        Lector assistant = Lector.builder()
                .degree(Degree.ASSISTANT)
                .salary(5000)
                .firstName("Jack")
                .lastName("Lee")
                .departments(List.of(engDepartment, mathDepartment))
                .build();
        Lector professor = Lector.builder()
                .degree(Degree.PROFESSOR)
                .salary(8000)
                .firstName("Mary")
                .lastName("Watson")
                .departments(List.of(mathDepartment))
                .build();
        engDepartment.setLectors(List.of(assistant));
        mathDepartment.setLectors(List.of(professor, assistant));

        departmentRepository.saveAll(List.of(engDepartment, mathDepartment));
        lectorRepository.saveAll(List.of(assistant, professor));
    }

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LectorRepository lectorRepository;

    @Test
    void givenDepartmentName_whenGetDepartmentHeadByDepartmentName_thenReturnDepartmentHead() {
        String headByDepartmentName = departmentRepository.getDepartmentHeadByDepartmentName("English").orElse(null);
        assertThat(headByDepartmentName).isEqualTo("John Doe");
    }

    @Test
    void givenDepartmentName_whenCountEmployeesByDepartmentName_thenReturnEmployeeCount() {
        Integer count = departmentRepository.countEmployeesByDepartmentName("Mathematics");
        assertThat(count).isEqualTo(2);
    }

    @Test
    void givenTemplate_whenGetLectorsByTemplate_thenReturnLectors() {
        List<Lector> lectors = lectorRepository.getLectorsByTemplate("ac");
        assertThat(lectors).hasSize(1);
        assertThat(lectors.get(0).getFirstName()).contains("ac");
    }

    @Test
    void givenNonExistentDepartmentName_whenGetDepartmentHeadByDepartmentName_thenReturnNoDepartmentHead() {
        Optional<String> headByDepartmentName = departmentRepository.getDepartmentHeadByDepartmentName("Biology");
        assertThat(headByDepartmentName).isEmpty();
    }

    @Test
    void givenNonExistentDepartmentName_whenCountEmployeesByDepartmentName_thenReturnZeroEmployeeCount() {
        Integer count = departmentRepository.countEmployeesByDepartmentName("Biology");
        assertThat(count).isEqualTo(0);
    }

    @Test
    void givenNonMatchingTemplate_whenGetLectorsByTemplate_thenReturnEmptyEmployeeList() {
        List<Lector> lectors = lectorRepository.getLectorsByTemplate("rb");
        assertThat(lectors).isEmpty();
    }
}
