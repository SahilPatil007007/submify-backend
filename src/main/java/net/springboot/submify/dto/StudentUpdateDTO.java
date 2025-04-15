package net.springboot.submify.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentUpdateDTO {
    private String studentId;
    private int subjectId;
    private int ut1;
    private int ut2;
    private boolean status;
    private String remark;
}
