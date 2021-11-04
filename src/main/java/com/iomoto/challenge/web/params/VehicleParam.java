package com.iomoto.challenge.web.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("VehicleParam")
public class VehicleParam {

    @ApiModelProperty(value = "VIN", example = "1", required = false)
    private String VIN;
    @ApiModelProperty(value = "vehicleID", example = "1", required = false)
    private String vehicleID;
    @ApiModelProperty(value = "vehicleNumber", example = "ACB32434", required = true)
    private String licensePlateNumber;
    @ApiModelProperty(value = "vehicleName", example = "Mazda", required = false)
    private String vehicleName;
    @ApiModelProperty(value = "vehicleProperties", example = "JSON/XML", required = false)
    private String vehicleProperties;


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

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }
}
