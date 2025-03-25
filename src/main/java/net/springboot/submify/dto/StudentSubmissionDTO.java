package net.springboot.submify.dto;

import lombok.Data;

@Data
public class StudentSubmissionDTO {
    private String rollNo;
    private String name;
    private Integer ut1;
    private Integer ut2;
    private String remark;
    private Boolean status;

    public StudentSubmissionDTO(String rollNo ,String name, Integer ut1, Integer ut2, String remark, Boolean status) {
        this.rollNo = rollNo;
        this.name = name;
        this.ut1 = ut1;
        this.ut2 = ut2;
        this.remark = remark;
        this.status = status;
    }
}
