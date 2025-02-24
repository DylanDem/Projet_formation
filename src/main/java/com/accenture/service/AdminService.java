package com.accenture.service;

import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface AdminService {

    List<AdminResponseDto> toFindAll(String email, String password);

    AdminResponseDto toFindAdmin(String email) throws EntityNotFoundException;

    AdminResponseDto toAddMin(AdminRequestDto adminRequestDto) throws ClientException;

    AdminResponseDto toUpdateAdmin(String email, String password, AdminRequestDto adminRequestDto) throws AdminException, EntityNotFoundException;

    AdminResponseDto toPartiallyUpdateAdmin(String email, AdminRequestDto adminRequestDto) throws AdminException, EntityNotFoundException;

    List<AdminResponseDto> searchAdmin(String email, String firstName, String name, String function);

   void deleteAdmin(String email, String password) throws EntityNotFoundException;
}
