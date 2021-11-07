package io.iomoto.challenge.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.iomoto.challenge.BaseTest;
import io.iomoto.challenge.application.params.FilterParam;
import io.iomoto.challenge.application.params.VehicleParam;
import io.iomoto.challenge.domain.entities.Vehicle;
import io.iomoto.challenge.domain.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VehicleControllerTest extends BaseTest {

    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private VehicleServiceImpl vehicleService;


    private VehicleParam generateVehicleParamJSON() {
        return new VehicleParam("12345678901234568", "ABC-1234-02", "Mazda3", "{\"color\":\"red\", \"weight\":\"1000\"}");
    }

    private VehicleParam generateVehicleParamInvalidJSON() {
        return new VehicleParam("12345678901234568", "ABC-1234-02", "Mazda3", "{\"color\":\"red\", \"weight\":\"1000\"");
    }

    private VehicleParam generateVehicleParamXML() {
        return new VehicleParam("12345678901234568", "ABC-1234-02", "Mazda3", "<xml><color>red</color><weight>1000</weight></xml>");
    }

    private VehicleParam generateVehicleParamInvalidXML() {
        return new VehicleParam("12345678901234568", "ABC-1234-02", "Mazda3", "<xml><color>red</color><weight>1000</weight>");
    }

    private VehicleParam generateVehicleParam() {
        VehicleParam param = generateVehicleParamJSON();
        return param;
    }

    @Test
    public void fetchAllVehiclesTest() throws Exception {
        Vehicle vehicle = generateVehicleParamJSON().toVehicle();

        given(vehicleService.findAll(any(FilterParam.class))).willReturn(new PageImpl<>(List.of(vehicle)));

        mockMvc.perform(get("/vehicles?vin=" + vehicle.getVin())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        then(vehicleService).should().findAll(any(FilterParam.class));
    }

    @Test
    public void findVehicleTest() throws Exception {
        Vehicle vehicle = generateVehicleParamJSON().toVehicle();

        given(vehicleService.findByVin(eq(vehicle.getVin()))).willReturn(vehicle);

        MvcResult result = mockMvc.perform(get("/vehicles/" + vehicle.getVin())).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String contentResult = response.getContentAsString();
        Assertions.assertEquals(response.getStatus(), 200);
    }


    @Test
    public void given_vehicleParam_when_postVehicle_then_vehicleCreated() throws Exception {
        VehicleParam param = generateVehicleParamJSON();
        Vehicle vehicle = param.toVehicle();
        String jsonString = mapper.writeValueAsString(param);
        mockMvc.perform(post("/vehicles")
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated());

        //then
        then(vehicleService).should().save(any(Vehicle.class));
        Assertions.assertEquals(vehicle.getVin(), param.getVin());
    }

    @Test
    public void updateTest() throws Exception {
        VehicleParam param = generateVehicleParamJSON();
        Vehicle vehicle = param.toVehicle();
        String jsonString = mapper.writeValueAsString(param);
        mockMvc.perform(put("/vehicles/" + param.getVin())
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

        //then
        then(vehicleService).should().save(any(Vehicle.class));
        Assertions.assertEquals(vehicle.getVin(), param.getVin());
    }


    @Test
    public void deleteTest() throws Exception {
        VehicleParam param = generateVehicleParamJSON();
        Vehicle vehicle = param.toVehicle();
        String jsonString = mapper.writeValueAsString(param);
        mockMvc.perform(delete("/vehicles/" + param.getVin())
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        //then
        then(vehicleService).should().delete(vehicle.getVin());
    }


}
