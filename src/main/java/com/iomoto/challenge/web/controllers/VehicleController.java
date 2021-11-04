package com.iomoto.challenge.web.controllers;

import com.iomoto.challenge.dao.entities.Vehicle;
import com.iomoto.challenge.dao.repositories.IVehicleRepository;
import com.iomoto.challenge.enums.ResultCodeEnum;
import com.iomoto.challenge.exceptions.BizException;
import com.iomoto.challenge.utils.ExUtil;
import com.iomoto.challenge.utils.ResultBuilderUtil;
import com.iomoto.challenge.web.params.VehicleParam;
import com.iomoto.challenge.web.response.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/vehicle")
public class VehicleController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    IVehicleRepository vehicleRepository;

    @GetMapping(path = "/fetchAll")
    public Object findAllVehicles() {
        return ResultBuilderUtil.buildSuccess(vehicleRepository.findAll());
    }

    @GetMapping(path = "/find/{VIN}")
    public Object findVehicle(@PathVariable String VIN) {
        return ResultBuilderUtil.buildSuccess(searchVehicle(VIN));
    }

    public Vehicle searchVehicle(String VIN) {
        return vehicleRepository.findByVehicleID(VIN);
    }

    @PostMapping(path = "/save")
    public CommonResponse save(@RequestBody VehicleParam param) {
        try {
            Vehicle vehicle = saveVehicle(param);
            if (vehicle == null) {
                return ResultBuilderUtil.buildFailure(ResultCodeEnum.CREATE_FAILED.getStatus(),
                        ResultCodeEnum.CREATE_FAILED.getCode(), "Already Exists");
            }
            return ResultBuilderUtil.buildSuccess(vehicle);
        } catch (Exception e) {
            logger.error(ExUtil.getStackTrace(e));
            return ResultBuilderUtil.buildFailure(ResultCodeEnum.CREATE_FAILED.getStatus(),
                    ResultCodeEnum.CREATE_FAILED.getCode(), e.getMessage());
        }
    }

    public Vehicle saveVehicle(VehicleParam param) {
        logger.info("save");
        Vehicle vehicle = vehicleRepository.findByVehicleID(param.getVIN());
        if (vehicle == null) {
            vehicle = new Vehicle(param.getVIN(), param.getVIN(), param.getVehicleName(), param.getLicensePlateNumber(), param.getVehicleProperties());
            vehicle = saveOrUpdate(vehicle);
        } else {
            vehicle = null;
        }
        return vehicle;
    }

    @PutMapping(path = "/update/{VIN}")
    public CommonResponse update(@PathVariable String VIN, @RequestBody VehicleParam param) {
        Vehicle vehicle = vehicleRepository.findByVehicleID(param.getVIN());
        if (vehicle != null) {
            vehicle = paramToEntity(param);
            vehicle.setVehicleID(VIN);
            return ResultBuilderUtil.buildSuccess(saveOrUpdate(vehicle));
        } else {
            return ResultBuilderUtil.buildFailure(ResultCodeEnum.UPDATE_FAILED);
        }
    }

    private Vehicle saveOrUpdate(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle paramToEntity(VehicleParam param) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID(param.getVehicleID());
        vehicle.setVIN(param.getVIN());
        vehicle.setVehicleName(param.getVehicleName());
        vehicle.setLicensePlateNumber(param.getLicensePlateNumber());
        vehicle.setVehicleProperties(param.getVehicleProperties());
        return vehicle;
    }

    @DeleteMapping(path = "/delete/{VIN}")
    public CommonResponse delete(@PathVariable String VIN) {
        try {
            vehicleRepository.deleteById(VIN);
            return ResultBuilderUtil.buildSuccess(ResultCodeEnum.SUCCESS.getMessage());
        } catch (Exception e) {
            logger.error(ExUtil.getStackTrace(e));
            return ResultBuilderUtil.buildFailure(ResultCodeEnum.CREATE_FAILED.getStatus(),
                    ResultCodeEnum.DELETE_FAILED.getCode(), e.getMessage());
        }
    }

    public CommonResponse testException() throws Exception {
        throw new Exception("This was meant to be thrown");
    }

    public Object testOtherResponse() throws Exception {
        return "Some String";
    }

    public Object bizException() throws Exception {
        throw new BizException("Biz Exception Thrown");
    }
}
