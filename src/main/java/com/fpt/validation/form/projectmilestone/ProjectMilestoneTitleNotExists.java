package com.fpt.validation.form.projectmilestone;

import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ProjectMilestoneTitleNotExistsValidator.class})
public @interface ProjectMilestoneTitleNotExists {

    String message() default "{ProjectMilestoneForm.title.NotExists}";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List{
        ProjectMilestoneTitleNotExists[] value();
    }
}
