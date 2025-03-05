package com.accenture.service;

import com.accenture.exception.LocationException;
import com.accenture.model.TypeVehicleEnum;
import com.accenture.repository.ClientDao;
import com.accenture.repository.LocationDao;
import com.accenture.repository.VehicleDao;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Location;
import com.accenture.repository.entity.Vehicle;
import com.accenture.service.dto.LocationRequestDto;
import com.accenture.service.dto.LocationResponseDto;
import com.accenture.service.mapper.LocationMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {

    private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);
    private static final String NULLABLE_ID = "Non present ID";
    private final LocationDao locationDao;
    private final LocationMapper locationMapper;
    private final ClientDao clientDao;
    private final VehicleDao vehicleDao;

    public LocationServiceImpl(LocationDao locationDao, LocationMapper locationMapper, ClientDao clientDao, VehicleDao vehicleDao) {
        this.locationDao = locationDao;
        this.locationMapper = locationMapper;
        this.clientDao = clientDao;
        this.vehicleDao = vehicleDao;
    }

    private static void locationVerify(LocationRequestDto locationRequestDto) throws LocationException {
        if (locationRequestDto == null)
            throw new LocationException("LocationRequestDto is null");
        if (locationRequestDto.idVehicle() == 0)
            throw new LocationException("Vehicle's absent");
        if (locationRequestDto.locationState() == null || locationRequestDto.locationState().isBlank())
            throw new LocationException("location's state is absent");
        if (locationRequestDto.startDate() == null)
            throw new LocationException("start date's absent");
        if (locationRequestDto.endDate() == null)
            throw new LocationException("end date's absent");
        if (locationRequestDto.endDate().isBefore(locationRequestDto.startDate()))
            throw new LocationException("end date cannot be before start date");
        if (locationRequestDto.totalAmountEuros() == 0)
            throw new LocationException("Location's total amount is absent");
        if (locationRequestDto.validationDate() == null)
            throw new LocationException("Validation date is absent");
        if (locationRequestDto.validationDate().isBefore(locationRequestDto.startDate()) ||
                locationRequestDto.validationDate().isAfter(locationRequestDto.endDate())) {
            throw new LocationException("Validation date is out of range");
        }
    }

    private static void toReplace(Location location, Location existingLocation) {
        if (location.getLocationState() != null)
            existingLocation.setLocationState(location.getLocationState());
        if (location.getAccessory() != null)
            existingLocation.setAccessory(location.getAccessory());
        if (location.getClient() != null)
            existingLocation.setClient(location.getClient());
        if (location.getEndDate() != null)
            existingLocation.setEndDate(location.getEndDate());
        if (location.getStartDate() != null)
            existingLocation.setStartDate(location.getStartDate());
        if (location.getValidationDate() != null)
            existingLocation.setValidationDate(location.getValidationDate());
        if (location.getTotalAmountEuros() != 0)
            existingLocation.setTotalAmountEuros(location.getTotalAmountEuros());
    }

    @Override
    public LocationResponseDto addReservation(String email, LocationRequestDto locationRequestDto) throws LocationException {
        locationVerify(locationRequestDto);
        Client client = clientDao.findById(email).orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Location location = locationMapper.toLocation(locationRequestDto);
        location.setClient(client);

        Vehicle vehicle = vehicleDao.findById(locationRequestDto.idVehicle()).orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
        location.setVehicle(vehicle);

        Location savedLocation = locationDao.save(location);

        return locationMapper.toLocationResponseDto(savedLocation);

    }

    @Override
    public LocationResponseDto toPartiallyUpdate(int id, LocationRequestDto locationRequestDto) throws LocationException, EntityNotFoundException {
        Optional<Location> locationOptional = locationDao.findById(id);
        if (locationOptional.isEmpty())
            throw new EntityNotFoundException(NULLABLE_ID);

        Location existingLocation = locationOptional.get();

        Location location = locationMapper.toLocation(locationRequestDto);

        toReplace(location, existingLocation);
        existingLocation.setId(id);
        Location registrdLocation = locationDao.save(existingLocation);
        return locationMapper.toLocationResponseDto(registrdLocation);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        Location location = locationDao.findById(id).orElseThrow(() -> new EntityNotFoundException("No location found for this ID"));
        location.getVehicle().setActive(true);
        locationDao.delete(location);
    }

    @Override
    public List<LocationResponseDto> toFindAll() {

        return locationDao.findAll().stream()
                .map(locationMapper::toLocationResponseDto)
                .toList();
    }

    @Override
    public LocationResponseDto toFind(int id) throws EntityNotFoundException {
        Optional<Location> locationOptional = locationDao.findById(id);
        if (locationOptional.isEmpty()) throw new EntityNotFoundException("Absent id");
        Location location = locationOptional.get();
        return locationMapper.toLocationResponseDto(location);
    }

}
