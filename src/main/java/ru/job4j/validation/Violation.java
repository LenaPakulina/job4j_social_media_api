package ru.job4j.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class Violation {
    private final String fieldName;
    private final String message;
}
