package com.example.vehiculosapi.controller;

import com.example.vehiculosapi.model.Vehiculo;
import com.example.vehiculosapi.service.VehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @GetMapping
    public ResponseEntity<List<Vehiculo>> getAll() {
        return ResponseEntity.ok(vehiculoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getById(@PathVariable String id) {
        Vehiculo v = vehiculoService.findById(id);
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(v);
    }

    @PostMapping
    public ResponseEntity<Vehiculo> create(@RequestBody Vehiculo v) {
        Vehiculo created = vehiculoService.create(v);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> update(@PathVariable String id, @RequestBody Vehiculo v) {
        Vehiculo updated = vehiculoService.update(id, v);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        boolean deleted = vehiculoService.delete(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
