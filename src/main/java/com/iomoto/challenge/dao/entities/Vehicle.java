package com.iomoto.challenge.dao.entities;

import org.springframework.data.annotation.Id;

public class Vehicle {
    @Id
    private String vehicleID;
    private String VIN;
    private String licensePlateNumber;
    private String vehicleName;
    private String vehicleProperties;

    public Vehicle() {

    }

    public Vehicle(String vehicleID, String VIN, String vehicleName, String licensePlateNumber, String vehicleProperties) {
        this.vehicleID = vehicleID;
        this.VIN = VIN;
        this.vehicleName = vehicleName;
        this.licensePlateNumber = licensePlateNumber;
        this.vehicleProperties = vehicleProperties;
    }


    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleProperties() {
        return vehicleProperties;
    }

    public void setVehicleProperties(String vehicleProperties) {
        this.vehicleProperties = vehicleProperties;
    }
}
