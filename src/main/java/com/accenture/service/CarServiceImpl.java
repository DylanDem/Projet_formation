package com.accenture.service;

import com.accenture.exception.VehicleException;
import com.accenture.repository.CarDao;
import com.accenture.repository.entity.Car;
import com.accenture.service.dto.CarRequestDto;
import com.accenture.service.dto.CarResponseDto;
import com.accenture.service.mapper.CarMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Class used to write core methods as in "add, replace, verify, delete, update,..."
 */

@Service
public class CarServiceImpl implements CarService {

    private static final String NULLABLE_ID = "Non present ID";
    private final CarDao carDao;
    private final CarMapper carMapper;

    public CarServiceImpl(CarDao carDao, CarMapper carMapper) {
        this.carDao = carDao;
        this.carMapper = carMapper;
    }

    private static void carVerify(CarRequestDto carRequestDto) throws VehicleException {
        if (carRequestDto == null)
            throw new VehicleException("ClientRequestDto is null");
        if (carRequestDto.brand() == null || carRequestDto.brand().isBlank())
            throw new VehicleException("car's brand is absent");
        if (carRequestDto.model() == null || carRequestDto.model().isBlank())
            throw new VehicleException("car's model is absent");
        if (carRequestDto.color() == null || carRequestDto.color().isBlank())
            throw new VehicleException("car's color is absent");
        if (carRequestDto.types() == null)
            throw new VehicleException("car's type is absent");
        if (carRequestDto.placesNb() < 2)
            throw new VehicleException("car's places number is insufficient");
        if (carRequestDto.fuelsList() == null || carRequestDto.fuelsList().isEmpty())
            throw new VehicleException("car's fuel is absent");
        if (carRequestDto.licencesList() == null)
            throw new VehicleException("car's licence is absent");
        if (carRequestDto.doorsNb() < 3)
            throw new VehicleException("car's doors number is insufficient");
        if (carRequestDto.transmission() == null || carRequestDto.transmission().isBlank())
            throw new VehicleException("car's transmission is absent");
        if (carRequestDto.luggagePiecesNb() == 0)
            throw new VehicleException("car's luggage's pieces number is absent");
        if (carRequestDto.dailyLocationPrice() == 0)
            throw new VehicleException("car's daily's location price is absent");
        if (carRequestDto.kilometers() == 0)
            throw new VehicleException("car's kilometer is absent");
    }

    private static void toReplace(Car car, Car existingCar) {
        if (car.getBrand() != null)
            existingCar.setBrand(car.getBrand());
        if (car.getModel() != null)
            existingCar.setModel(car.getModel());
        if (car.getColor() != null)
            existingCar.setColor(car.getColor());
        if (car.getTypes() != null)
            existingCar.setTypes(car.getTypes());
        if (car.getFuelsList() != null) {
            existingCar.getFuelsList().clear();
            existingCar.getFuelsList().addAll(car.getFuelsList());
        }
        if (car.getLicencesList() != null) {
            existingCar.getLicencesList().clear();
            existingCar.getLicencesList().addAll(car.getLicencesList());
        }
        if (car.getTransmission() != null)
            existingCar.setTransmission(car.getTransmission());
        if (car.getLuggagePiecesNb() != 0)
            existingCar.setLuggagePiecesNb(car.getLuggagePiecesNb());
        if (car.getDoorsNb() != 0)
            existingCar.setDoorsNb(car.getDoorsNb());
        if (car.getPlacesNb() != 0)
            existingCar.setPlacesNb(car.getPlacesNb());
        if (car.getDailyLocationPrice() != 0)
            existingCar.setDailyLocationPrice(car.getDailyLocationPrice());
        if (car.getKilometers() != 0)
            existingCar.setKilometers(car.getKilometers());
        if (car.isActive() != existingCar.isActive())
            existingCar.setActive(car.isActive());
        if (car.isOutCarPark() != existingCar.isOutCarPark())
            existingCar.setOutCarPark(car.isOutCarPark());
        if (car.isAirConditioner() != existingCar.isAirConditioner())
            existingCar.setAirConditioner(car.isAirConditioner());
    }


    /**
     * Adds a new car based on the provided car request data.
     *
     * @param carRequestDto The data transfer object containing the car request information.
     * @return CarResponseDto The response data transfer object containing the added car information.
     * @throws VehicleException If the car verification fails.
     */
    @Override
    public CarResponseDto toAdd(CarRequestDto carRequestDto) throws VehicleException {
        carVerify(carRequestDto);
        Car car = carMapper.toCar(carRequestDto);
        Car backedCar = carDao.save(car);

        return carMapper.toCarResponseDto(backedCar);
    }


    /**
     * Partially updates the car with the specified ID based on the provided car request data.
     *
     * @param id The ID of the car to be updated.
     * @param carRequestDto The data transfer object containing the car request information.
     * @return CarResponseDto The response data transfer object containing the updated car information.
     * @throws VehicleException If the car verification fails.
     * @throws EntityNotFoundException If the car with the specified ID is not found.
     */
    @Override
    public CarResponseDto toPartiallyUpdate(int id, CarRequestDto carRequestDto) throws VehicleException, EntityNotFoundException {
        Optional<Car> carOptional = carDao.findById(id);
        if (carOptional.isEmpty())
            throw new EntityNotFoundException(NULLABLE_ID);

        Car existingCar = carOptional.get();

        Car car = carMapper.toCar(carRequestDto);

        toReplace(car, existingCar);
        existingCar.setId(id);
        Car registrdCar = carDao.save(existingCar);
        return carMapper.toCarResponseDto(registrdCar);
    }


    /**
     * Deletes the car with the specified ID.
     *
     * @param id The ID of the car to be deleted.
     * @throws EntityNotFoundException If the car with the specified ID is not found.
     */
    @Override
    public void delete(int id) throws EntityNotFoundException {
        if (carDao.existsById(id))
            carDao.deleteById(id);
    }


    /**
     * Retrieves a list of all cars and maps them to their response data transfer objects.
     *
     * @return List<CarResponseDto> The list of response data transfer objects containing car information.
     */
    @Override
    public List<CarResponseDto> toFindAll() {

        return carDao.findAll().stream()
                .map(carMapper::toCarResponseDto)
                .toList();
    }



    /**
     * Finds the car with the specified ID and maps it to its response data transfer object.
     *
     * @param id The ID of the car to be found.
     * @return CarResponseDto The response data transfer object containing the found car information.
     * @throws EntityNotFoundException If the car with the specified ID is not found.
     */
    public CarResponseDto toFind(int id) throws EntityNotFoundException {
        Optional<Car> carOptional = carDao.findById(id);
        if (carOptional.isEmpty()) throw new EntityNotFoundException("Absent id");
        Car car = carOptional.get();
        return carMapper.toCarResponseDto(car);
    }

}
