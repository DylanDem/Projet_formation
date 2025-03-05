package com.accenture.model;

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

    Accessory(TypeVehicleEnum type) {
        this.type = type;
    }

    public TypeVehicleEnum getType(){
        return type;
    }
}
