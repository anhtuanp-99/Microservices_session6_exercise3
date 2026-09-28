package com.medical.doctorservice.service;

import com.medical.doctorservice.dto.request.DoctorRequestDTO;
import com.medical.doctorservice.dto.response.DoctorResponseDTO;
import com.medical.doctorservice.dto.response.DoctorSummaryDTO;
import com.medical.doctorservice.entity.Doctor;
import com.medical.doctorservice.exception.DuplicateResourceException;
import com.medical.doctorservice.exception.ResourceNotFoundException;
import com.medical.doctorservice.mapper.DoctorMapper;
import com.medical.doctorservice.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    public DoctorResponseDTO createDoctor(DoctorRequestDTO request) {
        // Kiểm tra email trùng
        if (doctorRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException(
                    "Email '" + request.getEmail() + "' đã được sử dụng bởi bác sĩ khác!");
        }

        Doctor doctor = Doctor.builder()
                .name(request.getName())
                .specialization(request.getSpecialization())
                .experienceYears(request.getExperienceYears())
                .email(request.getEmail())
                .status(request.getStatus())
                .build();

        Doctor saved = doctorRepository.save(doctor);
        log.info("Đã tạo doctor id={}, name={}, specialization={}",
                saved.getId(), saved.getName(), saved.getSpecialization());

        return doctorMapper.toResponseDTO(saved);
    }

    public DoctorResponseDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "ID", id));
        return doctorMapper.toResponseDTO(doctor);
    }

    /**
     * Trả về danh sách bác sĩ với chỉ 3 field: id, name, specialization.
     * Đây là endpoint chính theo yêu cầu đề bài.
     */
    public List<DoctorSummaryDTO> getAllDoctors() {
        return doctorMapper.toSummaryDTOList(doctorRepository.findAll());
    }
}