package io.iomoto.challenge.domain.repositories;

import io.iomoto.challenge.domain.entities.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
}
