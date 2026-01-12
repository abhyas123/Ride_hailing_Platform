package com.program.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SubmitPhoneRequest {

    @NotBlank
    @Pattern(regexp = "\\+91[0-9]{10}")
    private String phone;

    private String role; // optional
}
