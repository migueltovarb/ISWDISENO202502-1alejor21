# ☕ Sistema de Cafetería

Sistema completo de gestión de cafetería con frontend en React + Vite y backend en Spring Boot con MongoDB.

## 📁 Estructura del Proyecto

```
Sistema-Cafeteria-/
├── cafeteria-frontend-full/    # Frontend (React + Vite)
│   ├── src/
│   ├── package.json
│   └── vercel.json
├── cafeteria-orders-api/        # Backend (Spring Boot + MongoDB)
│   ├── src/
│   ├── pom.xml
│   └── application.properties
└── README.md
```

## 🚀 Despliegue

### Frontend en Vercel

1. Ve a [Vercel](https://vercel.com/new)
2. Importa el repositorio `alejor21/Sistema-Cafeteria-`
3. Configura:
   - **Root Directory:** `cafeteria-frontend-full`
   - **Framework Preset:** Vite
   - **Build Command:** `npm run build`
   - **Output Directory:** `dist`
4. Agrega variable de entorno:
   - `VITE_API_URL` = URL del backend desplegado
5. Deploy

### Backend en Railway/Render

1. Ve a [Railway.app](https://railway.app/) o [Render.com](https://render.com/)
2. Importa el repositorio
3. Configura:
   - **Root Directory:** `cafeteria-orders-api`
   - **Build Command:** `mvn clean install`
   - **Start Command:** `java -jar target/*.jar`
4. Deploy

## 💻 Desarrollo Local

### Backend
```bash
cd cafeteria-orders-api
mvn spring-boot:run
```
Backend en: `http://localhost:8080`

### Frontend
```bash
cd cafeteria-frontend-full
npm install
npm run dev
```
Frontend en: `http://localhost:5173`

## 🔑 Usuarios de Prueba

- **Admin:** username: `admin` | password: `admin123`
- **Empleado:** username: `empleado` | password: `emp123`

## 🛠️ Tecnologías

- **Frontend:** React 18, Vite 5, React Router, Axios
- **Backend:** Spring Boot 3.3, MongoDB, Java 17
- **Base de Datos:** MongoDB Atlas

## 📝 Funcionalidades

✅ Autenticación y registro de usuarios  
✅ Gestión de productos (CRUD)  
✅ Gestión de pedidos con estados  
✅ Gestión de usuarios  
✅ Reportes diarios de ventas  
✅ Interfaz responsive  
✅ Datos de prueba precargados
