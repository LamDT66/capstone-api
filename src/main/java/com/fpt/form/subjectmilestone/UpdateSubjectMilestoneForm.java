package com.fpt.form.subjectmilestone;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubjectMilestoneForm {

    @JsonInclude
    private Long id;

    private String title;

    private int step;

    private int duration;

    private Long subjectId;
}
