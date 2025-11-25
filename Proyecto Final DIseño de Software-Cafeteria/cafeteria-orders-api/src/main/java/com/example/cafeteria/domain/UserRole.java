package com.example.cafeteria.domain;

public enum UserRole {
    ADMIN,      // Administrador: gestión completa del sistema
    EMPLOYEE,   // Empleado/Cajero: gestiona pedidos y estados
    STUDENT,    // Estudiante: hace pedidos, aplica promociones
    STAFF       // Personal del campus: hace pedidos sin promociones
}
