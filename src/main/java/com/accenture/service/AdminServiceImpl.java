package com.accenture.service;

import com.accenture.Application;
import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.repository.AdminDao;
import com.accenture.repository.entity.Admin;
import com.accenture.service.dto.*;
import com.accenture.service.mapper.AdminMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
    private static final String NULLABLE_ID = "Non present ID";
    @Autowired
    private final AdminDao adminDao;
    private final AdminMapper adminMapper;
    private static final String REGEX_PW = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[&#@-_§])[A-Za-z\\d&%$_]{8,16}$";


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
        if (!adminRequestDto.password().matches(REGEX_PW))
            throw new ClientException("Admin's password is not valid");
        if (adminRequestDto.function() == null || adminRequestDto.function().isBlank())
            throw new ClientException("Admin's function is absent");

    }

    private static void toReplaceAdmin(Admin admin, Admin existingAdmin) {
        if (admin.getName() != null)
            existingAdmin.setName(admin.getName());
        if (admin.getFirstName() != null)
            existingAdmin.setFirstName(admin.getFirstName());
        if (admin.getEmail() != null)
            existingAdmin.setEmail((admin.getEmail()));
        if (admin.getPassword() != null)
            existingAdmin.setPassword((admin.getPassword()));
        if (admin.getFunction() != null)
            existingAdmin.setFunction((admin.getFunction()));
    }

    @Override
    public List<AdminResponseDto> toFindAll(String email, String password) {
        return adminDao.findAll().stream()
                .map(adminMapper::toAdminResponseDto)
                .toList();
    }

    @Override
    public AdminResponseDto toFindAdmin(String email) throws EntityNotFoundException {
        Optional<Admin> optAdmin = adminDao.findById(email);
        if (optAdmin.isEmpty()) throw new EntityNotFoundException("Absent ID");
        Admin admin = optAdmin.get();
        return adminMapper.toAdminResponseDto(admin);
    }

    @Override
    public AdminResponseDto toAddMin(AdminRequestDto adminRequestDto) throws ClientException {
        adminVerify(adminRequestDto);
        Admin admin = adminMapper.toAdmin(adminRequestDto);
        Admin backedAdmin = adminDao.save(admin);


        return adminMapper.toAdminResponseDto(backedAdmin);

    }

    public AdminResponseDto toUpdateAdmin(String email, String password, AdminRequestDto adminRequestDto) throws AdminException, EntityNotFoundException {
        if (!adminDao.existsById(email))
            throw new EntityNotFoundException(NULLABLE_ID);

        adminVerify(adminRequestDto);

        Admin admin = adminMapper.toAdmin(adminRequestDto);
        admin.setEmail(email);

        Admin registrdAdmin = adminDao.save(admin);
        return adminMapper.toAdminResponseDto(registrdAdmin);
    }

    public AdminResponseDto toPartiallyUpdateAdmin(String email, AdminRequestDto adminRequestDto) throws AdminException, EntityNotFoundException {
        Optional<Admin> adminOptional = adminDao.findById(email);
        if (adminOptional.isEmpty())
            throw new EntityNotFoundException(NULLABLE_ID);

        Admin admin = adminOptional.get();

        Admin existingAdmin = adminMapper.toAdmin(adminRequestDto);

        toReplaceAdmin((Admin) admin, (Admin) existingAdmin);
        Admin registrdAdmin = adminDao.save(existingAdmin);
        return adminMapper.toAdminResponseDto(registrdAdmin);
    }

    @Override
    public void deleteAdmin(String email, String password) throws EntityNotFoundException {
        if (adminDao.existsById(email))
            adminDao.deleteById(email);

    }

    @Override
    public List<AdminResponseDto> searchAdmin(String email, String firstName, String name, String function) {
        List<Admin> list = null;
        Optional<Admin> opt;
        if (email != null) opt = adminDao.findByEmailContaining(email);
        else if (firstName != null) list = adminDao.findByFirstNameContaining(firstName);
        else if (name != null) list = adminDao.findByNameContaining(name);
        else if (function != null) list = adminDao.findByFunctionContaining(function);
        if (list == null) throw new AdminException("Please enter something valid !");
        return list.stream().map(adminMapper::toAdminResponseDto).toList();
    }
}
