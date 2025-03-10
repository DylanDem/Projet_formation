package com.accenture.service;


import com.accenture.exception.VehicleException;
import com.accenture.model.Licences;
import com.accenture.model.TypeVehicleEnum;
import com.accenture.repository.RentalDao;
import com.accenture.repository.MotorbikeDao;
import com.accenture.repository.entity.Rental;
import com.accenture.repository.entity.Motorbike;
import com.accenture.service.dto.MotorbikeRequestDto;
import com.accenture.service.dto.MotorbikeResponseDto;
import com.accenture.service.mapper.MotorbikeMapper;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class used to write core methods as in "add, replace, verify, delete, update,..."
 */

@Service
public class MotorbikeServiceImpl implements MotorbikeService {

    private static final Logger logger = LoggerFactory.getLogger(MotorbikeServiceImpl.class);
    private static final String NULLABLE_ID = "Non present ID";
    private final MotorbikeDao motorbikeDao;
    private final MotorbikeMapper motorbikeMapper;
    private final RentalDao rentalDao;

    public MotorbikeServiceImpl(MotorbikeDao motorbikeDao, MotorbikeMapper motorbikeMapper, RentalDao rentalDao) {
        this.motorbikeDao = motorbikeDao;
        this.motorbikeMapper = motorbikeMapper;
        this.rentalDao = rentalDao;
    }


    /**
     * Verifies the given motorbike request DTO.
     *
     * @param motorbikeRequestDto the motorbike request DTO to verify
     * @throws VehicleException if any required field is null or blank, or if any required field has an insufficient value
     */
    private static void toVerifyMotorbike(MotorbikeRequestDto motorbikeRequestDto) throws VehicleException {
        basicMotorbikeParameters(motorbikeRequestDto);
        if (motorbikeRequestDto.cylinders() == 0)
            throw new VehicleException("motorbike's cylinders are insufficient");
        if (motorbikeRequestDto.licencesList() == null)
            throw new VehicleException("motorbike's licence is absent");
        if (motorbikeRequestDto.cylinderCapacity() == 0)
            throw new VehicleException("motorbike's cylinder's capacity is insufficient");
        if (motorbikeRequestDto.transmission() == null || motorbikeRequestDto.transmission().isBlank())
            throw new VehicleException("car's transmission is absent");
        if (motorbikeRequestDto.weight() == 0)
            throw new VehicleException("motorbike's weight is absent");
        if (motorbikeRequestDto.kwPower() == 0)
            throw new VehicleException("motorbike's kW's power is absent");
        if (motorbikeRequestDto.seatHeight() == 0)
            throw new VehicleException("motorbike's seat's height is absent");
        if (motorbikeRequestDto.dailyLocationPrice() == 0)
            throw new VehicleException("car's daily's location price is absent");
        if (motorbikeRequestDto.kilometers() == 0)
            throw new VehicleException("car's kilometer is absent");
        if (motorbikeRequestDto.typeVehicleEnum().equals(TypeVehicleEnum.CAR)) {
            throw new VehicleException("This vehicle can ONLY be a MOTORBIKE");
        }
        checkLicences(motorbikeRequestDto);
    }

    private static void checkLicences(MotorbikeRequestDto motorbikeRequestDto) {
        for (Licences licence : motorbikeRequestDto.licencesList()) {
            switch (licence) {
                case A1:
                    if (motorbikeRequestDto.cylinderCapacity() > 125 || motorbikeRequestDto.kwPower() > 11) {
                        throw new VehicleException("motorbike's specifications do not match the A1 licence requirements");
                    }
                    break;
                case A2:
                    if (motorbikeRequestDto.kwPower() > 35) {
                        throw new VehicleException("motorbike's specifications do not match the A2 licence requirements");
                    }
                    break;
                case A:
                    if (motorbikeRequestDto.kwPower() <= 35) {
                        throw new VehicleException("motorbike's specifications require a higher licence than A");
                    }
                    break;
                default:
                    throw new VehicleException("Invalid licence type. Only A1, A2, and A licences are permitted.");
            }
        }
    }

    private static void basicMotorbikeParameters(MotorbikeRequestDto motorbikeRequestDto) {
        if (motorbikeRequestDto == null)
            throw new VehicleException("ClientRequestDto is null");
        if (motorbikeRequestDto.brand() == null || motorbikeRequestDto.brand().isBlank())
            throw new VehicleException("car's brand is absent");
        if (motorbikeRequestDto.model() == null || motorbikeRequestDto.model().isBlank())
            throw new VehicleException("car's model is absent");
        if (motorbikeRequestDto.color() == null || motorbikeRequestDto.color().isBlank())
            throw new VehicleException("car's color is absent");
        if (motorbikeRequestDto.typesMotorbike() == null)
            throw new VehicleException("car's type is absent");
    }


    /**
     * Replaces the existing motorbike's attributes with the given motorbike's attributes.
     *
     * @param motorbike         the motorbike with the new attributes
     * @param existingMotorbike the existing motorbike to be updated
     */
    private static void toReplace(Motorbike motorbike, Motorbike existingMotorbike) {
        if (motorbike.getBrand() != null)
            existingMotorbike.setBrand(motorbike.getBrand());
        if (motorbike.getModel() != null)
            existingMotorbike.setModel(motorbike.getModel());
        if (motorbike.getColor() != null)
            existingMotorbike.setColor(motorbike.getColor());
        if (motorbike.getTypesMotorbike() != null)
            existingMotorbike.setTypesMotorbike(motorbike.getTypesMotorbike());
        if (motorbike.getLicencesList() != null) {
            existingMotorbike.getLicencesList().clear();
            existingMotorbike.getLicencesList().addAll(motorbike.getLicencesList());
        }
        if (motorbike.getTransmission() != null)
            existingMotorbike.setTransmission(motorbike.getTransmission());
        if (motorbike.getWeight() != 0)
            existingMotorbike.setWeight(motorbike.getWeight());
        if (motorbike.getKwPower() != 0)
            existingMotorbike.setKwPower(motorbike.getKwPower());
        if (motorbike.getSeatHeight() != 0)
            existingMotorbike.setSeatHeight(motorbike.getSeatHeight());
        if (motorbike.getDailyLocationPrice() != 0)
            existingMotorbike.setDailyLocationPrice(motorbike.getDailyLocationPrice());
        if (motorbike.getKilometers() != 0)
            existingMotorbike.setKilometers(motorbike.getKilometers());
        if (motorbike.getActive() != existingMotorbike.getActive())
            existingMotorbike.setActive(motorbike.getActive());
        if (motorbike.getOutCarPark() != existingMotorbike.getOutCarPark())
            existingMotorbike.setOutCarPark(motorbike.getOutCarPark());
    }


    /**
     * Adds a new motorbike using the given motorbike request DTO.
     *
     * @param motorbikeRequestDto the motorbike request DTO to add
     * @return the response DTO of the added motorbike
     * @throws VehicleException if the motorbike request DTO is invalid
     */
    @Override
    public MotorbikeResponseDto toAdd(MotorbikeRequestDto motorbikeRequestDto) throws VehicleException {
        toVerifyMotorbike(motorbikeRequestDto);
        Motorbike motorbike = motorbikeMapper.toMotorbike(motorbikeRequestDto);
        Motorbike backedMotorbike = motorbikeDao.save(motorbike);

        return motorbikeMapper.toMotorbikeResponseDto(backedMotorbike);
    }


    /**
     * Partially updates an existing motorbike with the given ID using the given motorbike request DTO.
     *
     * @param id                  the ID of the motorbike to be partially updated
     * @param motorbikeRequestDto the motorbike request DTO with the new attributes
     * @return the response DTO of the updated motorbike
     * @throws VehicleException        if the motorbike request DTO is invalid
     * @throws EntityNotFoundException if the motorbike with the given ID is not found
     */
    @Override
    public MotorbikeResponseDto toPartiallyUpdate(int id, MotorbikeRequestDto motorbikeRequestDto) throws VehicleException, EntityNotFoundException {
        logger.info("Starting toPartiallyUpdate method for ID : {} ", id);

        Optional<Motorbike> motorbikeOptional = motorbikeDao.findById(id);
        if (motorbikeOptional.isEmpty())
            throw new EntityNotFoundException(NULLABLE_ID);

        logger.info("Motorbike found with ID : {} ", id);
        Motorbike existingMotorbike = motorbikeOptional.get();

        Motorbike motorbike = motorbikeMapper.toMotorbike(motorbikeRequestDto);
        logger.info("Motorbike mapped from request DTO");

        toReplace(motorbike, existingMotorbike);
        logger.info("Replaced existing motorbike details with new motorbike details");

        existingMotorbike.setId(id);
        Motorbike registrdMotorbike = motorbikeDao.save(existingMotorbike);
        logger.info("Saved updated Motorbike with ID : {} ", id);

        return motorbikeMapper.toMotorbikeResponseDto(registrdMotorbike);
    }


    /**
     * Deletes a motorbike identified by its ID. If the motorbike is associated with
     * any locations, it marks the motorbike as out of the car park instead of
     * deleting it.
     *
     * @param id the ID of the motorbike to be deleted
     * @throws EntityNotFoundException if no motorbike is found with the specified ID
     */
    @Override
    public void delete(int id) throws EntityNotFoundException {
        Motorbike motorbike = motorbikeDao.findById(id).orElseThrow(() -> new EntityNotFoundException("No car for this ID"));
        List<Rental> rentalList = rentalDao.findByVehicleId(motorbike.getId());
        if (rentalList.isEmpty()) {
            motorbikeDao.delete(motorbike);
            return;
        }
        motorbike.setOutCarPark(true);
        motorbikeDao.save(motorbike);

    }


    /**
     * Finds and returns all motorbikes.
     *
     * @return a list of response DTOs of all motorbikes
     */
    @Override
    public List<MotorbikeResponseDto> toFindAll() {
        logger.info("Gathering all motorbikes.");

        return motorbikeDao.findAll().stream()
                .map(motorbikeMapper::toMotorbikeResponseDto)
                .toList();
    }


    /**
     * Finds and returns the motorbike with the given ID.
     *
     * @param id the ID of the motorbike to be found
     * @return the response DTO of the found motorbike
     * @throws EntityNotFoundException if the motorbike with the given ID is not found
     */
    @Override
    public MotorbikeResponseDto toFind(int id) throws EntityNotFoundException {
        logger.info("Searching a motorbike with the following ID : {}", id);
        Optional<Motorbike> motorbikeOptional = motorbikeDao.findById(id);

        if (motorbikeOptional.isEmpty()) {
            logger.error("No motorbike found for the following id : {}", id);
            throw new EntityNotFoundException("Absent id");
        }
        Motorbike motorbike = motorbikeOptional.get();
        logger.info("Found motorbike found : {}", motorbike);

        return motorbikeMapper.toMotorbikeResponseDto(motorbike);
    }


}
