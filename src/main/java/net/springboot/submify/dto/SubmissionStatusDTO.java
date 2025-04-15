package net.springboot.submify.dto;

import lombok.Data;

@Data
public class SubmissionStatusDTO {
    private boolean status;
    private String remark;

    public SubmissionStatusDTO(boolean status, String remark) {
        this.status = status;
        this.remark = remark;
    }
}
