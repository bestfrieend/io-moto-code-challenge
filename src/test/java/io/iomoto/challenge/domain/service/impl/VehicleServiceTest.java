package io.iomoto.challenge.domain.service.impl;

import com.mongodb.BasicDBObject;
import io.iomoto.challenge.application.params.FilterParam;
import io.iomoto.challenge.application.params.VehicleParam;
import io.iomoto.challenge.domain.entities.Vehicle;
import io.iomoto.challenge.domain.repositories.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    VehicleRepository vehicleRepository;
    @Mock
    MongoTemplate template;
    @InjectMocks
    VehicleServiceImpl vehicleService;

    private Vehicle generateVehicle() {
        return new Vehicle("12345678901234567", "ABC-1234-02", "Mazda1", new BasicDBObject().append("color", "red"));
    }

    private VehicleParam generateVehicleParam() {
        Vehicle vehicle = generateVehicle();
        VehicleParam param = new VehicleParam();
        param.setLicensePlateNumber(vehicle.getLicensePlateNumber());
        param.setName(vehicle.getName());
        param.setProperties(vehicle.getProperties().toString());
        param.setVin(vehicle.getVin());
        return param;
    }

    private VehicleParam generateUpdatedVehicleParam() {
        Vehicle vehicle = generateVehicle();
        VehicleParam param = new VehicleParam();
        param.setLicensePlateNumber(vehicle.getLicensePlateNumber());
        param.setName("Mazda 22");
        param.setProperties(vehicle.getProperties().toString());
        param.setVin(vehicle.getVin());
        return param;
    }

    @Test
    void fetchAllTest() {

        //given
        given(template.count(any(Query.class), eq(Vehicle.class))).willReturn(1L);
        given(template.find(any(Query.class), eq(Vehicle.class))).willReturn(List.of(generateVehicle()));
        //when
        FilterParam param = new FilterParam();
        param.setVin(generateVehicle().getVin());
        param.setPageSize(5);
        param.setPageNum(0);
        Page<Vehicle> page = vehicleService.findAll(param);

        //then
        assertNotNull(page);
        assertEquals(1, page.getTotalElements());

    }

    @Test
    void findByVinTest() {
        //given
        given(vehicleRepository.findById(eq(generateVehicle().getVin()))).willReturn(Optional.of(generateVehicle()));
        //when
        Vehicle vehicle = vehicleService.findByVin(generateVehicle().getVin());
        //then
        assertNotNull(vehicle);
    }

    @Test
    void deleteTest() {
        //when
        vehicleService.delete(generateVehicle().getVin());
        //then
        then(vehicleRepository).should().deleteById(eq(generateVehicle().getVin()));
    }

    @Test
    void saveTest() {
        //given
        given(vehicleRepository.save(any(Vehicle.class))).willReturn(generateVehicle());
        //when
        Vehicle vehicle = vehicleService.save(generateVehicle());
        //then
        assertEquals(generateVehicle().getVin(), vehicle.getVin());
    }

    @Test
    void updateTest() {
        //given
        given(vehicleRepository.save(any(Vehicle.class))).willReturn(generateVehicle());
        //when
        Vehicle vehicle = vehicleService.save(generateVehicle());
        //then
        assertEquals(generateVehicle().getVin(), vehicle.getVin());
    }


}