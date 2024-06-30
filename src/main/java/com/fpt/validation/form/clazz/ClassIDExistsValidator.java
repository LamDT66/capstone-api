package com.fpt.validation.form.clazz;

import org.springframework.beans.factory.annotation.Autowired;

import com.fpt.service.ClassService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClassIDExistsValidator implements ConstraintValidator<ClassIDExists, Long> {

    @Autowired
    private ClassService service;

    @Override
    public boolean isValid(Long data, ConstraintValidatorContext constraintValidatorContext) {

        if (data <= 0) {
            return false;
        }
        return service.isClassExistsById(data);
    }
}
