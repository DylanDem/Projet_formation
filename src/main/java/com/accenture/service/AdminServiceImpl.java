package com.accenture.service;

import com.accenture.exception.ClientException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.ClientDao;
import com.accenture.service.dto.;
import com.accenture.service.mapper.AdminMapper;
import com.accenture.service.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.Period;

public class AdminServiceImpl implements AdminService {

    private static final String NULLABLE_ID = "Non present ID";
    @Autowired
    private final AdminDao adminDao;
    private final AdminMapper adminMapper;

    public AdminServiceImpl(AdminDao adminDao, AdminMapper adminMapper) {
        this.adminDao = adminDao;
        this.adminMapper = adminMapper;
    }


    private static void adminVerify(AdminRequestDto adminRequestDto) throws ClientException {
        if (adminRequestDto == null)
            throw new ClientException("AdminRequestDto is null");
        if (adminRequestDto.name() == null || adminRequestDto.name().isBlank())
            throw new ClientException("Admin's name is absent");
        if (adminRequestDto.firstName() == null || adminRequestDto.firstName().isBlank())
            throw new ClientException("Admin's first name is absent");
        if (adminRequestDto.email() == null || adminRequestDto.email().isBlank())
            throw new ClientException("Admin's email is absent");
        if (adminRequestDto.password() == null || adminRequestDto.password().isBlank())
            throw new ClientException("Admin's password is absent");

    }
}
