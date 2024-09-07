package com.test.task.service;

import com.test.task.repository.LectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectorRepository lectorRepository;

    public String getEmployeeNameByTemplate(String template) {
        return lectorRepository.getLectorsByTemplate(template).stream()
                .map(n -> n.getFirstName() + " " + n.getLastName())
                .collect(Collectors.joining(", "));
    }
}
