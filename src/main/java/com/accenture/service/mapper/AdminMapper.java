package com.accenture.service.mapper;

import com.accenture.repository.entity.Admin;
import com.accenture.service.dto.AdminRequestDto;
import com.accenture.service.dto.AdminResponseDto;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between Admin and AdminRequestDto/AdminResponseDto objects.
 */
@Mapper(componentModel = "spring")
public interface AdminMapper {


    /**
     * Converts an AdminRequestDto object to an Admin object.
     *
     * @param adminRequestDto The AdminRequestDto object to convert
     * @return The converted Admin object
     */
    Admin toAdmin(AdminRequestDto adminRequestDto);


    /**
     * Converts an Admin object to an AdminResponseDto object.
     *
     * @param admin The Admin object to convert
     * @return The converted AdminResponseDto object
     */
    AdminResponseDto toAdminResponseDto (Admin admin);
}
