package com.iomoto.challenge.dao.repositories;

import com.iomoto.challenge.dao.entities.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IVehicleRepository extends MongoRepository<Vehicle, String> {
    public Vehicle findByVehicleID(String vehicleID);
}
