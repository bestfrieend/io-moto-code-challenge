package com.iomoto.challenge.web.controllers;

import com.iomoto.challenge.dao.entities.Vehicle;
import com.iomoto.challenge.dao.repositories.IVehicleRepository;
import com.iomoto.challenge.enums.ResultCodeEnum;
import com.iomoto.challenge.exceptions.BizException;
import com.iomoto.challenge.utils.StringUtil;
import com.iomoto.challenge.web.aspect.CommonAspectController;
import com.iomoto.challenge.web.params.VehicleParam;
import com.iomoto.challenge.web.response.CommonResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    VehicleController vehicleController;

    @Autowired
    IVehicleRepository vehicleRepository;

    private Vehicle generateVehicle() {
        Vehicle vehicle = new Vehicle("ABC123", "ABC123", "Mazda", "ADDD-3434", "{\"color\":\"red\"}");
        return vehicle;
    }

    private VehicleParam generateVehicleParam() {
        Vehicle vehicle = generateVehicle();
        VehicleParam param = new VehicleParam();
        param.setVehicleID(vehicle.getVehicleID());
        param.setLicensePlateNumber(vehicle.getLicensePlateNumber());
        param.setVehicleName(vehicle.getVehicleName());
        param.setVehicleProperties(vehicle.getVehicleProperties());
        param.setVIN(vehicle.getVIN());
        return param;
    }

    private VehicleParam generateUpdatedVehicleParam() {
        Vehicle vehicle = generateVehicle();
        VehicleParam param = new VehicleParam();
        param.setVehicleID(vehicle.getVehicleID());
        param.setLicensePlateNumber(vehicle.getLicensePlateNumber());
        param.setVehicleName("Mazda 22");
        param.setVehicleProperties(vehicle.getVehicleProperties());
        param.setVIN(vehicle.getVIN());
        return param;
    }

    @Test
    void delete() {
        Vehicle vehicle = generateVehicle();
        vehicleController.delete(vehicle.getVIN());
        Assertions.assertNull(vehicleRepository.findByVehicleID(vehicle.getVIN()));
    }

    @Test
    void save() {
        delete();
        VehicleParam param = generateVehicleParam();
        CommonResponse response = vehicleController.save(param);
        Vehicle vehicle = (Vehicle) response.getData();
        Assertions.assertEquals(response.getStatus(), 200);
        assertTrue(StringUtil.isNotBlank(vehicle.toString()));
        assertTrue(!StringUtil.isBlank(vehicle.toString()));
        Assertions.assertEquals(vehicle.getVehicleID(), generateVehicle().getVehicleID());

        response = vehicleController.save(param);
        assertNotEquals(response.getStatus(), 200);
    }

    @Test
    void update() {
        save();
        VehicleParam param = generateUpdatedVehicleParam();
        CommonResponse response = vehicleController.update(param.getVIN(), param);
        Vehicle vehicle = (Vehicle) response.getData();
        Assertions.assertEquals(response.getStatus(), 200);
        assertTrue(StringUtil.isNotBlank(vehicle.toString()));
        Assertions.assertEquals(vehicle.getVehicleName(), param.getVehicleName());
        delete();
        response = vehicleController.update(param.getVIN(), param);
        Assertions.assertEquals(response.getStatus(), ResultCodeEnum.UPDATE_FAILED.getStatus());
    }

    @Test
    void findAllVehicles() {
        save();
        CommonResponse response = (CommonResponse) vehicleController.findAllVehicles();
        List<Vehicle> vehicles = (List<Vehicle>) response.getData();
        Assertions.assertEquals(response.getStatus(), 200);
        assertNotEquals(vehicles.size(), 0);
        vehicleRepository.deleteAll();
        response = (CommonResponse) vehicleController.findAllVehicles();
        vehicles = (List<Vehicle>) response.getData();
        Assertions.assertEquals(response.getStatus(), 200);
        assertEquals(vehicles.size(), 0);
    }

    @Test
    void findVehicle() {
        save();
        CommonResponse response = (CommonResponse) vehicleController.findVehicle(generateVehicleParam().getVIN());
        Vehicle vehicle = (Vehicle) response.getData();
        Assertions.assertEquals(response.getStatus(), 200);
        assertEquals(vehicle.getLicensePlateNumber(), generateVehicleParam().getLicensePlateNumber());
        delete();
        response = (CommonResponse) vehicleController.findVehicle(generateVehicleParam().getVIN());
        vehicle = (Vehicle) response.getData();
        Assertions.assertEquals(response.getStatus(), 200);
        assertNull(vehicle);
    }

    @Test
    void testExceptionTest() throws Exception {
        vehicleController.testException();
    }

    @Test
    void testBizException() throws Exception {
        vehicleController.bizException();
    }

    @Test
    void testOtherResponseTest() throws Exception {
        vehicleController.testOtherResponse();
    }
}