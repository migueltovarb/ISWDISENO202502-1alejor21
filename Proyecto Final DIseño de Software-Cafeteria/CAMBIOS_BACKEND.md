# ✅ Sistema de Cafetería - Backend Actualizado

## Cambios Realizados

### 1. **Autenticación y Registro de Usuarios**

#### Modelo User (User.java)
- ✅ Agregado campo `email` al modelo User
- ✅ Actualizado constructor para incluir el email
- ✅ Agregados getters y setters para email

#### AuthController (AuthController.java)
- ✅ Endpoint POST `/api/auth/login` - Login de usuarios
- ✅ Endpoint POST `/api/auth/register` - **NUEVO** Registro de usuarios
- ✅ Clase `RegisterRequest` para recibir datos de registro (username, password, fullName, email, role)
- ✅ Clase `LoginResponse` para devolver datos del usuario autenticado

#### UserService (UserService.java)
- ✅ Método `register()` - **NUEVO** Registra nuevos usuarios
- ✅ Validación de username único
- ✅ Validación de campos obligatorios para registro
- ✅ Asignación automática de rol EMPLOYEE si no se especifica
- ✅ Actualizado método `update()` para incluir email

### 2. **Datos de Prueba**

#### DataInitializer (config/DataInitializer.java) - **NUEVO ARCHIVO**
Este archivo inicializa automáticamente la base de datos con datos de prueba cuando está vacía.

**Usuarios de prueba:**
- **Admin:** 
  - Username: `admin`
  - Password: `admin123`
  - Email: `admin@cafeteria.com`
  - Rol: ADMIN

- **Empleado:**
  - Username: `empleado`
  - Password: `emp123`
  - Email: `empleado@cafeteria.com`
  - Rol: EMPLOYEE

**Productos de prueba (9 productos):**
- Café Americano - $2,500 (Bebida Caliente)
- Cappuccino - $3,500 (Bebida Caliente)
- Latte - $3,800 (Bebida Caliente)
- Café Frío - $4,000 (Bebida Fría)
- Smoothie de Frutas - $5,000 (Bebida Fría)
- Sandwich de Pollo - $6,500 (Comida Rápida)
- Hamburguesa Clásica - $8,000 (Comida Rápida)
- Cheesecake - $4,500 (Postre)
- Brownie con Helado - $5,500 (Postre)

## Endpoints Disponibles

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/register` - Registrar nuevo usuario

### Usuarios
- `GET /api/users` - Listar usuarios
- `GET /api/users/{id}` - Obtener usuario por ID
- `POST /api/users` - Crear usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Desactivar usuario

### Productos
- `GET /api/products` - Listar productos
- `GET /api/products/{id}` - Obtener producto por ID
- `POST /api/products` - Crear producto
- `PUT /api/products/{id}` - Actualizar producto
- `DELETE /api/products/{id}` - Eliminar producto

### Pedidos
- `GET /api/orders` - Listar pedidos
- `GET /api/orders/{id}` - Obtener pedido por ID
- `POST /api/orders` - Crear pedido
- `PUT /api/orders/{id}` - Actualizar pedido
- `PATCH /api/orders/{id}/status` - Actualizar estado del pedido
- `DELETE /api/orders/{id}` - Eliminar pedido

### Reportes
- `GET /api/reports/daily?date={date}` - Reporte diario

## Cómo Ejecutar

### Backend (Puerto 8080)

```bash
cd cafeteria-orders-api
mvn clean install
mvn spring-boot:run
```

El backend estará disponible en: `http://localhost:8080`

### Frontend (Puerto 5173)

```bash
cd cafeteria-frontend-full
npm install
npm run dev
```

El frontend estará disponible en: `http://localhost:5173`

## Base de Datos

El proyecto usa MongoDB Atlas. La conexión está configurada en `application.properties`:

```
spring.data.mongodb.uri=mongodb+srv://brayanrojass_db_user:Alejo.20324@cluster0.qnjkzbf.mongodb.net/cafeteria_db
spring.data.mongodb.database=cafeteria_db
```

## Prueba del Sistema

1. **Ejecuta el backend** - Los usuarios y productos de prueba se crearán automáticamente
2. **Ejecuta el frontend**
3. **Opciones de acceso:**
   - **Registrarse:** Crea un nuevo usuario desde la pantalla de login
   - **Login con usuario admin:** username: `admin`, password: `admin123`
   - **Login con empleado:** username: `empleado`, password: `emp123`

## CORS

Todos los controladores tienen CORS habilitado con `@CrossOrigin(origins = "*")` para permitir peticiones desde el frontend.

## Características Implementadas

✅ Registro de usuarios (HU009)
✅ Login de usuarios (HU009)
✅ Gestión de productos (HU001, HU002)
✅ Gestión de pedidos (HU003-HU006, HU010)
✅ Gestión de usuarios (HU009)
✅ Reportes diarios (HU007, HU008)
✅ Datos de prueba automáticos
✅ Validaciones de negocio
✅ Manejo de errores

## Notas Importantes

- El campo `email` ahora es **obligatorio** para el registro
- Los nuevos usuarios se crean con rol `EMPLOYEE` por defecto
- Las contraseñas se almacenan en texto plano (⚠️ En producción deberías usar encriptación)
- El sistema verifica que el username sea único al registrarse
