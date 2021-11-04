package com.iomoto.challenge.web.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iomoto.challenge.BaseTest;
import com.iomoto.challenge.dao.entities.Vehicle;
import com.iomoto.challenge.dao.repositories.IVehicleRepository;
import com.iomoto.challenge.web.params.VehicleParam;
import com.iomoto.challenge.web.response.CommonResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
public class VehicleControllerTest_backup extends BaseTest {
    //    @MockBean
//    private VehicleController vehicleController;
    @Autowired
    private VehicleController avehicleController;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IVehicleRepository vehicleRepository;

    @Test
    @Disabled
    public void fetchAllVehiclesTest() throws Exception {
        Vehicle arrival = new Vehicle();
        arrival.setVIN("AAABBB");

        List<Vehicle> allArrivals = singletonList(arrival);

        given(avehicleController.findAllVehicles()).willReturn(allArrivals);

//        mockMvc.perform(get("/vehicle/fetchAll")
//                .contentType(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(1)));
        MvcResult result = mockMvc.perform(get("/vehicle/fetchAll")).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String contentResult = response.getContentAsString();
        Assertions.assertTrue(mapToJson(allArrivals).equalsIgnoreCase(contentResult));
//                .andExpect(jsonPath("$[0].getVIN()", is(arrival.getVIN())));
    }

    @Test
    @Disabled
    public void deleteTest() {
        Vehicle vehicle = new Vehicle();
        vehicle.setVIN("AAABBB");
        vehicle.setVehicleID(vehicle.getVIN());
    }


    @Test
//    @Disabled
    public void findVehicleTest() throws Exception {
        Vehicle vehicle = new Vehicle();
        vehicle.setVIN("AAABBB");
        vehicle.setVehicleID(vehicle.getVIN());

        given(avehicleController.findVehicle(vehicle.getVIN())).willReturn(vehicle);

        MvcResult result = mockMvc.perform(get("/vehicle/find/" + vehicle.getVIN())).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String contentResult = response.getContentAsString();
        String jsonResponse = this.mapToJson(contentResult);
        Assertions.assertEquals(response.getStatus(), 200);
        Assertions.assertEquals(contentResult, mapToJson(vehicle));
    }

    @Test
    public void saveVehicleTest() throws JsonProcessingException {
        VehicleParam param = new VehicleParam();
        param.setVehicleName("My Vehicle");
        param.setVehicleProperties("{\"color\":\"red\"}");
        param.setVIN("AAABBB");
        param.setLicensePlateNumber("AAABBB");
        Vehicle vehicle = avehicleController.searchVehicle(param.getVIN());
        if (vehicle==null) {
            vehicle = avehicleController.saveVehicle(param);

            Assertions.assertNotNull(vehicle);
        }
        else{
            param.setVehicleID(param.getVIN());
            Assertions.assertEquals(mapToJson(vehicle), mapToJson(avehicleController.paramToEntity(param)));
        }
    }

//    @Test
//    public void saveTest() throws Exception {
//        VehicleParam param = new VehicleParam();
//        param.setVehicleName("My Vehicle");
//        param.setVehicleProperties("{\"color\":\"red\"}");
//        param.setVIN("AAABBB");
//        param.setLicensePlateNumber("AAABBB");
//        Vehicle vehicle = avehicleController.paramToEntity(param);
//        given(avehicleController.save(param)).willReturn(ResultBuilderUtil.buildSuccess(vehicle));
//        MvcResult result = mockMvc.perform(post("/vehicle/save").accept(MediaType.APPLICATION_JSON).content(mapToJson(param).getBytes(StandardCharsets.UTF_8)).contentType(MediaType.APPLICATION_JSON)).andReturn();
//        MockHttpServletResponse response = result.getResponse();
//        String contentResult = response.getContentAsString();
//        String jsonResponse = this.mapToJson(contentResult);
//    }

    @Test
    public void saveTest() throws Exception {
        VehicleParam param = new VehicleParam();
        param.setVehicleName("My Vehicle");
        param.setVehicleProperties("{\"color\":\"red\"}");
        param.setVIN("AAABBB");
        param.setLicensePlateNumber("AAABBB");
        vehicleRepository.deleteAll();
        CommonResponse commonResponse = avehicleController.save(param);
        Assertions.assertNotNull(commonResponse.getData());

    }

}
