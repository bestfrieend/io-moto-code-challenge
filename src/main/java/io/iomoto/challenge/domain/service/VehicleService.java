package io.iomoto.challenge.domain.service;

import io.iomoto.challenge.application.params.FilterParam;
import io.iomoto.challenge.domain.entities.Vehicle;
import org.springframework.data.domain.Page;

public interface VehicleService {
    Page<Vehicle> findAll(FilterParam filter);

    Vehicle findByVin(String vin);

    Vehicle save(Vehicle vehicle);

    void delete(String vin);
}
