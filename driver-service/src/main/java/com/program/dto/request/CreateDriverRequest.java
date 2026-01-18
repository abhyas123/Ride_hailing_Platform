package com.program.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDriverRequest {

    @NotBlank(message = "Phone number is required")
    @Pattern(
            regexp = "^\\+91[6-9]\\d{9}$",
            message = "Phone number must be in +91XXXXXXXXXX format"
    )
    private String phone;
}
