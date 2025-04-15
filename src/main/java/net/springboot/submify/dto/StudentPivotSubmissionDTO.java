package net.springboot.submify.dto;

import lombok.Data;

import java.util.Map;


@Data
public class StudentPivotSubmissionDTO {
    private String rollNo;
    private Map<String, SubmissionStatusDTO> subjectStatuses;
    private boolean finalized;

    public StudentPivotSubmissionDTO(String rollNo, Map<String, SubmissionStatusDTO> subjectStatuses, boolean finalized) {
        this.rollNo = rollNo;
        this.subjectStatuses = subjectStatuses;
        this.finalized = finalized;
    }
}
