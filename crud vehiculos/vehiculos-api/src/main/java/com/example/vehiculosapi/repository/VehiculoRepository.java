package com.example.vehiculosapi.repository;

import com.example.vehiculosapi.model.Vehiculo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehiculoRepository extends MongoRepository<Vehiculo, String> {
    boolean existsByPlaca(String placa);
}
