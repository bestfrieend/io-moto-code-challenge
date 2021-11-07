package io.iomoto.challenge.application.params;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


@Getter
@Setter
@ApiModel("VehicleParam")
public class FilterParam {

    private String vin;
    private String name;
    private String licensePlateNumber;

    @Min(value = 0, message = "Error: Page number must be greater than or equal to 0")
    private int pageNum;

    @Max(value = 1000, message = "Error: Page size can not exceed 1000")
    private int pageSize;



}
