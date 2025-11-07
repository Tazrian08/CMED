package com.prescription.management.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayCountDTO {
    private Integer day;
    private Long count;
}
