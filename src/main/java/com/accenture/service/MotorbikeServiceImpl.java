package com.accenture.service;


import com.accenture.exception.VehicleException;
import com.accenture.repository.LocationDao;
import com.accenture.repository.MotorbikeDao;
import com.accenture.repository.entity.Location;
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
    private final LocationDao locationDao;

    public MotorbikeServiceImpl(MotorbikeDao motorbikeDao, MotorbikeMapper motorbikeMapper, LocationDao locationDao) {
        this.motorbikeDao = motorbikeDao;
        this.motorbikeMapper = motorbikeMapper;
        this.locationDao = locationDao;
    }


    /**
     * Verifies the given motorbike request DTO.
     *
     * @param motorbikeRequestDto the motorbike request DTO to verify
     * @throws VehicleException if any required field is null or blank, or if any required field has an insufficient value
     */
    private static void toVerifyMotorbike(MotorbikeRequestDto motorbikeRequestDto) throws VehicleException {
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
        if (motorbikeRequestDto.cylinders() == 0)
            throw new VehicleException("motorbike's cylinders are insufficient");
        if (motorbikeRequestDto.licencesList() == null)
            throw new VehicleException("motorbike's licence is absent");
        if (motorbikeRequestDto.cylinderCapacity() == null || motorbikeRequestDto.cylinderCapacity().isBlank())
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
        if (motorbike.isActive() != existingMotorbike.isActive())
            existingMotorbike.setActive(motorbike.isActive());
        if (motorbike.isOutCarPark() != existingMotorbike.isOutCarPark())
            existingMotorbike.setOutCarPark(motorbike.isOutCarPark());
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
        Optional<Motorbike> motorbikeOptional = motorbikeDao.findById(id);
        if (motorbikeOptional.isEmpty())
            throw new EntityNotFoundException(NULLABLE_ID);

        Motorbike existingMotorbike = motorbikeOptional.get();

        Motorbike motorbike = motorbikeMapper.toMotorbike(motorbikeRequestDto);

        toReplace(motorbike, existingMotorbike);
        existingMotorbike.setId(id);
        Motorbike registrdMotorbike = motorbikeDao.save(existingMotorbike);
        return motorbikeMapper.toMotorbikeResponseDto(registrdMotorbike);
    }


    @Override
    public void delete(int id) throws EntityNotFoundException {

        logger.info("Starting delete method for ID : {}", id);
        Motorbike motorbike = motorbikeDao.findById(id).orElseThrow(() -> new EntityNotFoundException("No car for this ID"));
        logger.info("Motorbike found with ID : {}", id);
        List<Location> locationList = locationDao.findByVehicleId(motorbike.getId());

        logger.info("Location list size : {}", locationList.size());
        if (locationList.isEmpty()) {
            motorbikeDao.delete(motorbike);
            logger.info("Motorbike deleted with ID : {} ", id);
            return;
        }
        motorbike.setOutCarPark(true);
        motorbikeDao.save(motorbike);
        logger.info("Motorbike updated to out-of-car-park status with ID : {} ", id);

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
