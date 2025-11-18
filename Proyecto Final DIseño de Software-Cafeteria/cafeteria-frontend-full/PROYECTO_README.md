# Sistema de Gestión de Pedidos para Cafetería - Frontend

Frontend completo construido con **React 18**, **React Router DOM** y **Axios** para el sistema de gestión de pedidos de cafetería.

## 🏗️ Arquitectura del Proyecto

```
cafeteria-frontend-full/
├── src/
│   ├── components/           # Componentes reutilizables
│   │   ├── ErrorMessage.jsx  # Mensajes de error
│   │   ├── Layout.jsx        # Layout principal con Outlet
│   │   ├── LoadingSpinner.jsx # Indicador de carga
│   │   ├── Navbar.jsx        # Barra de navegación con roles
│   │   └── ProtectedRoute.jsx # HOC para protección de rutas
│   │
│   ├── contexts/             # Contextos de React
│   │   └── AuthContext.jsx   # Contexto de autenticación (usuario, rol, login/logout)
│   │
│   ├── pages/                # Páginas principales
│   │   ├── LoginPage.jsx     # HU009: Página de inicio de sesión
│   │   ├── UsersPage.jsx     # HU009: CRUD de usuarios (ADMIN)
│   │   ├── ProductsPage.jsx  # HU001/HU002: CRUD de productos con filtros
│   │   ├── OrdersPage.jsx    # HU003-HU006: Creación y gestión de pedidos
│   │   ├── OrderDetailPage.jsx # HU010: Detalle/comprobante de pedido
│   │   └── ReportsPage.jsx   # HU007/HU008: Reportes diarios e historial
│   │
│   ├── services/             # Servicios de API
│   │   └── api.js            # Configuración de axios y endpoints
│   │
│   ├── hooks/                # Custom hooks (vacío por ahora)
│   ├── utils/                # Utilidades (vacío por ahora)
│   │
│   ├── App.jsx               # Configuración de rutas con React Router
│   ├── main.jsx              # Punto de entrada
│   ├── styles.css            # Estilos base
│   └── additional-styles.css # Estilos adicionales para nuevos componentes
│
├── index.html
├── package.json
└── vite.config.js
```

## 🚀 Características Implementadas

### ✅ HU001 y HU002: Gestión del Menú de Productos
- **Ubicación**: `src/pages/ProductsPage.jsx`
- **Funcionalidades**:
  - ✅ Listado de productos con tarjetas visuales
  - ✅ Creación de productos (nombre, categoría, precio)
  - ✅ Edición de productos existentes
  - ✅ Eliminación de productos
  - ✅ Filtros por categoría (HOT_DRINK, COLD_DRINK, FAST_FOOD, DESSERT)
  - ✅ Búsqueda por nombre
  - ✅ Validaciones (nombre obligatorio, precio > 0)
- **Acceso**: Solo ADMIN

### ✅ HU003-HU006: Gestión de Pedidos
- **Ubicación**: `src/pages/OrdersPage.jsx`
- **Funcionalidades**:
  - ✅ Vista dual: creación y listado de pedidos
  - ✅ Selección de productos del menú
  - ✅ Carrito con cantidades ajustables
  - ✅ Captura de datos del cliente (nombre, ID, método de pago)
  - ✅ Cálculo automático de totales
  - ✅ Gestión de estados: PENDING → PREPARING → READY → DELIVERED
  - ✅ Cambio de estado con PATCH a `/api/orders/{id}/status`
  - ✅ Indicadores visuales para pedidos READY y DELIVERED
  - ✅ Vista detallada de cada pedido
- **Acceso**: ADMIN y EMPLOYEE

### ✅ HU010: Detalle y Comprobante de Pedido
- **Ubicación**: `src/pages/OrderDetailPage.jsx`
- **Funcionalidades**:
  - ✅ Vista completa del pedido (ID, productos, cantidades, totales)
  - ✅ Información del cliente y método de pago
  - ✅ Fecha y hora del pedido
  - ✅ Estado actual con indicador visual
  - ✅ Historial de cambios de estado (si existe)
  - ✅ Botón de impresión con estilos optimizados
  - ✅ Diseño de comprobante profesional
- **Acceso**: ADMIN y EMPLOYEE

### ✅ HU007 y HU008: Reportes y Historial
- **Ubicación**: `src/pages/ReportsPage.jsx`
- **Funcionalidades**:
  - ✅ **Reporte Diario**:
    - Selector de fecha
    - Total de pedidos e ingresos
    - Ticket promedio y tiempo promedio
    - Top 5 productos más vendidos con gráficos de barras
    - Distribución por método de pago
    - Distribución por estado de pedidos
  - ✅ **Historial de Pedidos**:
    - Tabla completa de todos los pedidos
    - Filtros por rango de fechas (desde/hasta)
    - Filtro por estado
    - Visualización de historial de cambios de estado
    - Información detallada de cada pedido
- **Acceso**: Solo ADMIN

### ✅ HU009: Gestión de Usuarios y Autenticación
- **Ubicación**: `src/pages/LoginPage.jsx` y `src/pages/UsersPage.jsx`
- **Funcionalidades**:
  - ✅ **Login**:
    - Formulario de usuario/contraseña
    - Llamada a POST `/api/auth/login`
    - Almacenamiento en localStorage
    - Redirección según rol (ADMIN → /products, EMPLOYEE → /orders)
  - ✅ **Gestión de Usuarios** (solo ADMIN):
    - Listado de todos los usuarios
    - Creación de usuarios con rol seleccionable (ADMIN/EMPLOYEE)
    - Edición de usuarios (nombre, username, contraseña, rol, activo)
    - Desactivación de usuarios (DELETE `/api/users/{id}`)
    - Estado activo/inactivo
- **Acceso**: Login (público), Gestión (solo ADMIN)

## 🔐 Sistema de Autenticación y Roles

### AuthContext (`src/contexts/AuthContext.jsx`)
- **Estado Global**: Maneja usuario logueado, rol y token
- **Funciones**:
  - `login(credentials)`: Autentica al usuario
  - `logout()`: Cierra sesión y limpia localStorage
  - `isAdmin()`: Verifica si el usuario es ADMIN
  - `isEmployee()`: Verifica si el usuario es EMPLOYEE
  - `isAuthenticated`: Boolean indicando si hay sesión activa

### ProtectedRoute (`src/components/ProtectedRoute.jsx`)
- Protege rutas que requieren autenticación
- Parámetro `requireAdmin`: Restringe acceso solo a ADMIN
- Redirecciona a `/login` si no hay sesión
- Redirecciona a `/orders` si un EMPLOYEE intenta acceder a ruta de ADMIN

### Navbar (`src/components/Navbar.jsx`)
- Muestra opciones según el rol:
  - **ADMIN**: Pedidos, Productos, Usuarios, Reportes
  - **EMPLOYEE**: Solo Pedidos
- Muestra información del usuario logueado
- Botón de cerrar sesión

## 🌐 Endpoints del Backend

Todos los endpoints usan como base: `http://localhost:8080/api`

### Autenticación
```
POST /auth/login
Body: { "username": "...", "password": "..." }
Response: { "id", "username", "fullName", "role" }
```

### Usuarios
```
GET    /users
GET    /users/{id}
POST   /users
PUT    /users/{id}
DELETE /users/{id}
```

### Productos
```
GET    /products
GET    /products/{id}
POST   /products
PUT    /products/{id}
DELETE /products/{id}
```

### Pedidos
```
GET    /orders
GET    /orders/{id}
POST   /orders
PUT    /orders/{id}
DELETE /orders/{id}
PATCH  /orders/{id}/status
Body: { "status": "PENDING|PREPARING|READY|DELIVERED", "changedBy": "userId" }
```

### Reportes
```
GET /reports/daily?date=YYYY-MM-DD
Response: { "date": "...", "totalOrders": 10, "totalIncome": 123.45 }
```

## 🎨 Tecnologías Utilizadas

- **React 18.3.1**: Biblioteca principal
- **React Router DOM 6**: Sistema de rutas
- **Axios**: Cliente HTTP para peticiones al backend
- **Vite**: Build tool y dev server
- **CSS Moderno**: Estilos con variables CSS, gradientes, glassmorphism

## 📦 Instalación y Ejecución

### Instalar dependencias
```bash
npm install
```

### Ejecutar en desarrollo
```bash
npm run dev
```

### Compilar para producción
```bash
npm run build
```

### Vista previa de la build
```bash
npm run preview
```

## 🔧 Configuración

El backend debe estar ejecutándose en `http://localhost:8080`

Si necesitas cambiar la URL del backend, edita:
```javascript
// src/services/api.js
const API_BASE_URL = 'http://localhost:8080/api'
```

## 📱 Rutas de la Aplicación

| Ruta | Componente | Acceso | Descripción |
|------|-----------|--------|-------------|
| `/login` | LoginPage | Público | Inicio de sesión |
| `/` | Redirect | Autenticado | Redirecciona a /orders |
| `/orders` | OrdersPage | Autenticado | Creación y gestión de pedidos |
| `/orders/:id` | OrderDetailPage | Autenticado | Detalle/comprobante de pedido |
| `/products` | ProductsPage | Solo ADMIN | Gestión del menú |
| `/users` | UsersPage | Solo ADMIN | Gestión de usuarios |
| `/reports` | ReportsPage | Solo ADMIN | Reportes e historial |

## 🎯 Flujo de Navegación

### ADMIN
1. Login → Redirección a `/products`
2. Puede navegar a: Pedidos, Productos, Usuarios, Reportes
3. Tiene acceso completo a todas las funcionalidades

### EMPLOYEE
1. Login → Redirección a `/orders`
2. Solo puede navegar a: Pedidos
3. Puede crear pedidos y gestionar estados
4. No tiene acceso a productos, usuarios ni reportes

## 🛠️ Componentes Reutilizables

- **Layout**: Envoltorio con Navbar y Outlet
- **Navbar**: Navegación con opciones según rol
- **ProtectedRoute**: HOC para protección de rutas
- **LoadingSpinner**: Indicador de carga en 3 tamaños
- **ErrorMessage**: Mensajes de error con cierre opcional

## 📝 Notas Importantes

1. **Estados de Pedidos**: El flujo es secuencial y unidireccional
   - PENDING → PREPARING → READY → DELIVERED

2. **Persistencia**: El usuario se guarda en localStorage como `cafeteria_user`

3. **Validaciones**:
   - Productos: nombre obligatorio, precio > 0
   - Pedidos: al menos un producto en el carrito
   - Usuarios: todos los campos obligatorios al crear

4. **Responsividad**: La aplicación es responsive y se adapta a móviles

5. **Impresión**: La página de detalle de pedido tiene estilos especiales para impresión

## 🚨 Manejo de Errores

Todas las llamadas API incluyen manejo de errores con:
- Estados de loading
- Mensajes de error descriptivos
- Función `handleAPIError` en `src/services/api.js`

## 🎨 Estilo Visual

- **Tema**: Dark mode con glassmorphism
- **Colores primarios**: Púrpura (#8b5cf6) y Rosa (#ec4899)
- **Efectos**: Gradientes, sombras, animaciones suaves
- **Tipografía**: Inter, San Francisco, Segoe UI

---

**Desarrollado para el curso de Diseño de Software - 2025**
