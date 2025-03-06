package com.accenture.service;

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



    private static void verifyAdmin(AdminRequestDto adminRequestDto) throws ClientException {
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


    /**
     * Retrieves a list of all admins.
     *
     * @param email The email of the admin
     * @param password The password of the admin
     * @return A list of AdminResponseDto objects
     */
    @Override
    public List<AdminResponseDto> toFindAll(String email, String password) {
        return adminDao.findAll().stream()
                .map(adminMapper::toAdminResponseDto)
                .toList();
    }


    /**
     * Retrieves an admin based on their email.
     *
     * @param email The email of the admin to retrieve
     * @return The AdminResponseDto object containing the admin's information
     * @throws EntityNotFoundException If the admin is not found
     */
    @Override
    public AdminResponseDto toFindAdmin(String email) throws EntityNotFoundException {
        Optional<Admin> optAdmin = adminDao.findById(email);
        if (optAdmin.isEmpty()) throw new EntityNotFoundException("Absent ID");
        Admin admin = optAdmin.get();
        return adminMapper.toAdminResponseDto(admin);
    }


    /**
     * Adds a new admin based on the provided AdminRequestDto.
     *
     * @param adminRequestDto The AdminRequestDto containing the new admin's information
     * @return The AdminResponseDto object containing the added admin's information
     * @throws ClientException If the AdminRequestDto is invalid
     */
    @Override
    public AdminResponseDto toAddMin(AdminRequestDto adminRequestDto) throws ClientException {
        verifyAdmin(adminRequestDto);
        Admin admin = adminMapper.toAdmin(adminRequestDto);
        Admin backedAdmin = adminDao.save(admin);


        return adminMapper.toAdminResponseDto(backedAdmin);

    }

    /**
     * Updates an existing admin based on their email, password, and the provided AdminRequestDto.
     *
     * @param email The email of the admin to update
     * @param password The password of the admin to update
     * @param adminRequestDto The AdminRequestDto containing the updated admin's information
     * @return The AdminResponseDto object containing the updated admin's information
     * @throws AdminException If there is an error updating the admin
     * @throws EntityNotFoundException If the admin is not found
     */
    public AdminResponseDto toUpdateAdmin(String email, String password, AdminRequestDto adminRequestDto) throws AdminException, EntityNotFoundException {
        if (!adminDao.existsById(email))
            throw new EntityNotFoundException(NULLABLE_ID);

        verifyAdmin(adminRequestDto);

        Admin admin = adminMapper.toAdmin(adminRequestDto);
        admin.setEmail(email);

        Admin registrdAdmin = adminDao.save(admin);
        return adminMapper.toAdminResponseDto(registrdAdmin);
    }


    /**
     * Partially updates an existing admin based on their email and the provided AdminRequestDto.
     *
     * @param email The email of the admin to partially update
     * @param adminRequestDto The AdminRequestDto containing the updated admin's information
     * @return The AdminResponseDto object containing the updated admin's information
     * @throws AdminException If there is an error updating the admin
     * @throws EntityNotFoundException If the admin is not found
     */
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


    /**
     * Deletes an admin based on their email and password.
     *
     * @param email The email of the admin to delete
     * @param password The password of the admin to delete
     * @throws EntityNotFoundException If the admin is not found
     */
    @Override
    public void deleteAdmin(String email, String password) throws EntityNotFoundException {
        if (adminDao.existsById(email))
            adminDao.deleteById(email);

    }


    /**
     * Searches for admins based on various criteria.
     *
     * @param email The email of the admin to search for
     * @param firstName The first name of the admin to search for
     * @param name The name of the admin to search for
     * @param function The function of the admin to search for
     * @return A list of AdminResponseDto objects matching the search criteria
     * @throws AdminException If no valid search criteria are provided
     */
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
