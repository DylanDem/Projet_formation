package com.accenture.model;


/**
 * Enum representing different types of accessories for vehicles.
 * Each accessory is associated with a specific type of vehicle.
 */
public enum Accessory {
    BABYSEAT(TypeVehicleEnum.CAR),
    GPS(TypeVehicleEnum.CAR),
    BIKERACKS(TypeVehicleEnum.CAR),
    ROOFBOX(TypeVehicleEnum.CAR),

    HELMET(TypeVehicleEnum.MOTORBIKE),
    GLOVES(TypeVehicleEnum.MOTORBIKE),
    ANTITHEFT(TypeVehicleEnum.MOTORBIKE),
    RAINPANTS(TypeVehicleEnum.MOTORBIKE);

    private TypeVehicleEnum type;

    /**
     * Constructor for the Accessory enum.
     *
     * @param type The type of vehicle associated with the accessory
     */
    Accessory(TypeVehicleEnum type) {
        this.type = type;
    }


    /**
     * Gets the type of vehicle associated with the accessory.
     *
     * @return The type of vehicle
     */
    public TypeVehicleEnum getType(){
        return type;
    }
}
