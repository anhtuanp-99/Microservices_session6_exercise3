package com.medical.doctorservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO rút gọn cho endpoint GET /api/v1/doctors.
 * Chỉ trả về các field cần thiết theo yêu cầu đề bài:
 * id, name, specialization.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorSummaryDTO {
    private Long id;
    private String name;
    private String specialization;
}