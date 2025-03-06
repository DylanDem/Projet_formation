package com.accenture.service;

import com.accenture.exception.RentalException;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService {

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

    private static void locationVerify(RentalRequestDto rentalRequestDto) throws RentalException {
        if (rentalRequestDto == null)
            throw new RentalException("LocationRequestDto is null");
        if (rentalRequestDto.idVehicle() == 0)
            throw new RentalException("Vehicle's absent");
        if (rentalRequestDto.locationState() == null || rentalRequestDto.locationState().isBlank())
            throw new RentalException("location's state is absent");
        if (rentalRequestDto.startDate() == null)
            throw new RentalException("start date's absent");
        if (rentalRequestDto.endDate() == null)
            throw new RentalException("end date's absent");
        if (rentalRequestDto.endDate().isBefore(rentalRequestDto.startDate()))
            throw new RentalException("end date cannot be before start date");
        if (rentalRequestDto.totalAmountEuros() == 0)
            throw new RentalException("Location's total amount is absent");
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


    /**
     * Adds a new rental reservation based on the provided email and RentalRequestDto.
     *
     * @param email The email of the client making the reservation
     * @param rentalRequestDto The RentalRequestDto containing the rental information
     * @return The RentalResponseDto object containing the saved rental information
     * @throws RentalException If there is an error with the rental request
     */
    @Override
    public RentalResponseDto addReservation(String email, RentalRequestDto rentalRequestDto) throws RentalException {
        locationVerify(rentalRequestDto);
        Client client = clientDao.findById(email).orElseThrow(() -> new EntityNotFoundException("Client not found"));
        Rental rental = rentalMapper.toLocation(rentalRequestDto);
        rental.setClient(client);

        Vehicle vehicle = vehicleDao.findById(rentalRequestDto.idVehicle()).orElseThrow(() -> new EntityNotFoundException("Vehicle not found"));
        rental.setVehicle(vehicle);

        Rental savedRental = rentalDao.save(rental);

        return rentalMapper.toLocationResponseDto(savedRental);

    }


    /**
     * Partially updates an existing rental based on its ID and the provided RentalRequestDto.
     *
     * @param id The ID of the rental to partially update
     * @param rentalRequestDto The RentalRequestDto containing the updated rental information
     * @return The RentalResponseDto object containing the updated rental information
     * @throws RentalException If there is an error with the rental request
     * @throws EntityNotFoundException If the rental is not found
     */
    @Override
    public RentalResponseDto toPartiallyUpdate(int id, RentalRequestDto rentalRequestDto) throws RentalException, EntityNotFoundException {
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


    /**
     * Deletes a rental based on its ID.
     * If the rental is associated with a vehicle, the vehicle will be marked as active.
     *
     * @param id The ID of the rental to delete
     * @throws EntityNotFoundException If the rental is not found
     */
    @Override
    public void delete(int id) throws EntityNotFoundException {
        Rental rental = rentalDao.findById(id).orElseThrow(() -> new EntityNotFoundException("No location found for this ID"));
        rental.getVehicle().setActive(true);
        rentalDao.delete(rental);
    }

    /**
     * Retrieves a list of all rentals.
     *
     * @return A list of RentalResponseDto objects
     */
    @Override
    public List<RentalResponseDto> toFindAll() {

        return rentalDao.findAll().stream()
                .map(rentalMapper::toLocationResponseDto)
                .toList();
    }

    /**
     * Retrieves a rental based on its ID.
     *
     * @param id The ID of the rental to retrieve
     * @return The RentalResponseDto object containing the rental information
     * @throws EntityNotFoundException If the rental is not found
     */
    @Override
    public RentalResponseDto toFind(int id) throws EntityNotFoundException {
        Optional<Rental> locationOptional = rentalDao.findById(id);
        if (locationOptional.isEmpty()) throw new EntityNotFoundException("Absent id");
        Rental rental = locationOptional.get();
        return rentalMapper.toLocationResponseDto(rental);
    }

}
