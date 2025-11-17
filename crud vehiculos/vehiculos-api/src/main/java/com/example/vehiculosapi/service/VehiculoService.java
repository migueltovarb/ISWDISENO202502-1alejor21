package com.example.vehiculosapi.service;

import com.example.vehiculosapi.model.Vehiculo;
import com.example.vehiculosapi.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo findById(String id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    public Vehiculo create(Vehiculo vehiculo) {
        if (vehiculoRepository.existsByPlaca(vehiculo.getPlaca())) {
            throw new RuntimeException("La placa ya existe");
        }
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo update(String id, Vehiculo data) {
        Vehiculo existing = vehiculoRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setPlaca(data.getPlaca());
        existing.setMarca(data.getMarca());
        existing.setModelo(data.getModelo());
        existing.setAnio(data.getAnio());
        existing.setColor(data.getColor());
        return vehiculoRepository.save(existing);
    }

    public boolean delete(String id) {
        if (!vehiculoRepository.existsById(id)) {
            return false;
        }
        vehiculoRepository.deleteById(id);
        return true;
    }
}
