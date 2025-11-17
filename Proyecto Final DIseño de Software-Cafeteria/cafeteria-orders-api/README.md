# Cafeteria Orders API

Backend en Spring Boot para el Sistema de Gestión de Pedidos de Cafetería, usando Java 17 y MongoDB (Atlas o local).

## Requisitos

- JDK 17
- Maven 3.x
- Una instancia de MongoDB (Atlas recomendado)

## Configuración rápida

1. Configura la variable de entorno `MONGODB_URI` con la cadena de conexión de tu cluster de MongoDB Atlas:

   - En Windows (PowerShell):
     ```powershell
     $env:MONGODB_URI="mongodb+srv://usuario:password@cluster-url/cafeteria?retryWrites=true&w=majority"
     ```

2. Compila y ejecuta:

   ```bash
   mvn spring-boot:run
   ```

El backend se levantará en `http://localhost:8080`.

## Endpoints principales

- `POST /api/orders` Crear pedido
- `PUT /api/orders/{id}` Modificar pedido pendiente
- `PATCH /api/orders/{id}/status` Cambiar estado del pedido
- `GET /api/orders` Listar pedidos
- `GET /api/orders/{id}` Detalle de pedido

- `POST /api/products` Crear producto
- `PUT /api/products/{id}` Actualizar producto
- `GET /api/products` Listar productos activos
- `GET /api/products/category/{category}` Listar por categoría
- `DELETE /api/products/{id}` Eliminar producto

- `GET /api/reports/daily?date=YYYY-MM-DD` Resumen diario (pedidos e ingresos)
