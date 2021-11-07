package io.iomoto.challenge.application.params;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import io.iomoto.challenge.domain.entities.Vehicle;
import io.iomoto.challenge.platform.utils.Utility;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel("VehicleParam")
@NoArgsConstructor
@AllArgsConstructor
public class VehicleParam {
    private static ObjectMapper mapper = new ObjectMapper();

    @ApiModelProperty(value = "vin", example = "1", required = true)
    @NotNull
    @Size(min = 17, max = 17, message = "Error: vin number should be exactly 17 digits")
    private String vin;
    @ApiModelProperty(value = "licensePlateNumber", example = "ACB32434", required = true)
    @NotNull
    private String licensePlateNumber;
    @ApiModelProperty(value = "name", example = "Mazda")
    private String name;
    @ApiModelProperty(value = "properties", example = "{\"color\":\"red\"}")
    private String properties;


    public Vehicle toVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(this.getName());
        vehicle.setVin(this.getVin());
        vehicle.setLicensePlateNumber(this.getLicensePlateNumber());
        vehicle.setProperties(new BasicDBObject(Utility.toMap(this.getProperties())));
        return vehicle;
    }

}
