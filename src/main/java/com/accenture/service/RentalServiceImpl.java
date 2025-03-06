package com.accenture.service;

import com.accenture.exception.LocationException;
import com.accenture.repository.ClientDao;
import com.accenture.repository.RentalDao;
import com.accenture.repository.VehicleDao;
import com.accenture.repository.entity.Client;
import com.accenture.repository.entity.Rental;
import com.accenture.repository.entity.Vehicle;
import com.accenture.service.dto.RentalRequestDto;
import com.accenture.service.dto.RentalResponseDto;
import com.accenture.service.mapper.RentalMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

    private static final Logger logger = LoggerFactory.getLogger(RentalServiceImpl.class);
    private static final String NULLABLE_ID = "Non present ID";
    private final RentalDao rentalDao;
    private final RentalMapper rentalMapper;
    private final ClientDao clientDao;
    private final VehicleDao vehicleDao;

    public RentalServiceImpl(RentalDao rentalDao, RentalMapper rentalMapper, ClientDao clientDao, VehicleDao vehicleDao) {
        this.rentalDao = rentalDao;
        this.rentalMapper = rentalMapper;
        this.clientDao = clientDao;
        this.vehicleDao = vehicleDao;
    }

    private static void locationVerify(RentalRequestDto rentalRequestDto) throws LocationException {
        if (rentalRequestDto == null)
            throw new LocationException("LocationRequestDto is null");
        if (rentalRequestDto.idVehicle() == 0)
            throw new LocationException("Vehicle's absent");
        if (rentalRequestDto.locationState() == null || rentalRequestDto.locationState().isBlank())
            throw new LocationException("location's state is absent");
        if (rentalRequestDto.startDate() == null)
            throw new LocationException("start date's absent");
        if (rentalRequestDto.endDate() == null)
            throw new LocationException("end date's absent");
        if (rentalRequestDto.endDate().isBefore(rentalRequestDto.startDate()))
            throw new LocationException("end date cannot be before start date");
        if (rentalRequestDto.totalAmountEuros() == 0)
            throw new LocationException("Location's total amount is absent");
        if (rentalRequestDto.validationDate() == null)
            throw new LocationException("Validation date is absent");
        if (rentalRequestDto.validationDate().isBefore(rentalRequestDto.startDate()) ||
                rentalRequestDto.validationDate().isAfter(rentalRequestDto.endDate())) {
            throw new LocationException("Validation date is out of range");
        }
    }

    private static void toReplace(Rental rental, Rental existingRental) {
        if (rental.getLocationState() != null)
            existingRental.setLocationState(rental.getLocationState());
        if (rental.getAccessory() != null)
            existingRental.setAccessory(rental.getAccessory());
        if (rental.getClient() != null)
            existingRental.setClient(rental.getClient());
        if (rental.getEndDate() != null)
            existingRental.setEndDate(rental.getEndDate());
        if (rental.getStartDate() != null)
            existingRental.setStartDate(rental.getStartDate());
        if (rental.getValidationDate() != null)
            existingRental.setValidationDate(rental.getValidationDate());
        if (rental.getTotalAmountEuros() != 0)
            existingRental.setTotalAmountEuros(rental.getTotalAmountEuros());
    }

    @Override
    public RentalResponseDto addReservation(String email, RentalRequestDto rentalRequestDto) throws LocationException {
        locationVerify(rentalRequestDto);
        Client client = clientDao.findById(email).orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Rental rental = rentalMapper.toLocation(rentalRequestDto);
        rental.setClient(client);

        Vehicle vehicle = vehicleDao.findById(rentalRequestDto.idVehicle()).orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
        rental.setVehicle(vehicle);

        Rental savedRental = rentalDao.save(rental);

        return rentalMapper.toLocationResponseDto(savedRental);

    }

    @Override
    public RentalResponseDto toPartiallyUpdate(int id, RentalRequestDto rentalRequestDto) throws LocationException, EntityNotFoundException {
        Optional<Rental> locationOptional = rentalDao.findById(id);
        if (locationOptional.isEmpty())
            throw new EntityNotFoundException(NULLABLE_ID);

        Rental existingRental = locationOptional.get();

        Rental rental = rentalMapper.toLocation(rentalRequestDto);

        toReplace(rental, existingRental);
        existingRental.setId(id);
        Rental registrdRental = rentalDao.save(existingRental);
        return rentalMapper.toLocationResponseDto(registrdRental);
    }

    @Override
    public void delete(int id) throws EntityNotFoundException {
        Rental rental = rentalDao.findById(id).orElseThrow(() -> new EntityNotFoundException("No location found for this ID"));
        rental.getVehicle().setActive(true);
        rentalDao.delete(rental);
    }

    @Override
    public List<RentalResponseDto> toFindAll() {

        return rentalDao.findAll().stream()
                .map(rentalMapper::toLocationResponseDto)
                .toList();
    }

    @Override
    public RentalResponseDto toFind(int id) throws EntityNotFoundException {
        Optional<Rental> locationOptional = rentalDao.findById(id);
        if (locationOptional.isEmpty()) throw new EntityNotFoundException("Absent id");
        Rental rental = locationOptional.get();
        return rentalMapper.toLocationResponseDto(rental);
    }

}
