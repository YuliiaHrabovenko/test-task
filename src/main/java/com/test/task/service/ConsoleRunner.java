package com.test.task.service;

import com.test.task.entity.Degree;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import static com.test.task.util.StringUtil.toCamelCaseWithSingleSpaces;

@Component
@RequiredArgsConstructor
public class ConsoleRunner implements CommandLineRunner {

    private final DepartmentService departmentService;
    private final LectorService lectorService;

    private final Scanner scanner = new Scanner(System.in);
    private final ConfigurableApplicationContext context;

    @Override
    public void run(String... args) {
        System.out.println("Choose an option:");
        System.out.println("1 - Who is head of department {department_name}");
        System.out.println("2 - Show {department_name} statistics");
        System.out.println("3 - Show the average salary for the department {department_name}");
        System.out.println("4 - Show count of employee for {department_name}");
        System.out.println("5 - Global search by {template}");
        System.out.println("6 - Exit");
        System.out.println("\nAll department names: " + departmentService.getAllDepartmentNames());

        while (true) {
            try {
                System.out.println("\nEnter your choice:");
                int choice = scanner.nextInt();
                scanner.nextLine();
                String departmentName;
                switch (choice) {
                    case 1:
                        System.out.print("Who is head of department ");
                        departmentName = toCamelCaseWithSingleSpaces(scanner.nextLine());
                        String departmentHead = departmentService.findHeadByDepartmentName(departmentName);
                        System.out.printf("Head of %s department is %s", departmentName, departmentHead);
                        break;
                    case 2:
                        System.out.print("Show statistics of department ");
                        departmentName = toCamelCaseWithSingleSpaces(scanner.nextLine());
                        Map<Degree, Long> statistics = departmentService.getDepartmentStatistics(departmentName);
                        System.out.println("Assistants - " + statistics.getOrDefault(Degree.ASSISTANT, 0L));
                        System.out.println("Associate professors - " + statistics.getOrDefault(Degree.ASSOCIATE_PROFESSOR, 0L));
                        System.out.println("Professors - " + statistics.getOrDefault(Degree.PROFESSOR, 0L));
                        break;
                    case 3:
                        System.out.print("Show the average salary for the department ");
                        departmentName = toCamelCaseWithSingleSpaces(scanner.nextLine());
                        Double averageDepartmentSalary = departmentService.getDepartmentAverageSalary(departmentName);
                        System.out.printf("The average salary of %s is %.2f\n", departmentName, averageDepartmentSalary);
                        break;
                    case 4:
                        System.out.print("Show count of employees for ");
                        departmentName = toCamelCaseWithSingleSpaces(scanner.nextLine());
                        Integer employeeCount = departmentService.countEmployeesByDepartmentName(departmentName);
                        System.out.println(employeeCount);
                        break;
                    case 5:
                        System.out.print("Global search by ");
                        String template = scanner.nextLine();
                        String employeeNames = lectorService.getEmployeeNameByTemplate(template);
                        System.out.println(employeeNames);
                        break;
                    case 6:
                        System.out.println("Exiting application...");
                        scanner.close();
                        SpringApplication.exit(context, () -> 0);
                        return;
                    default:
                        System.out.println("Invalid option. Please try again...");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid option. Please try again...");
                scanner.next();
            }
        }
    }
}
