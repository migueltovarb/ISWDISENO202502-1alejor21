# Instrucciones de Despliegue en Vercel

## ✅ Cambios Realizados para Vercel

1. **✅ Eliminado TypeScript** del comando build (no estabas usando TypeScript)
2. **✅ Creado `vercel.json`** para manejar las rutas de React Router
3. **✅ Configurado variables de entorno** para la URL de la API

## 🚀 Pasos para Desplegar en Vercel

### 1. Desplegar el Backend Primero

El backend (Spring Boot) NO se puede desplegar en Vercel. Opciones:

- **Railway.app** (Recomendado - Gratis)
- **Render.com** (Gratis)
- **Heroku** (Gratis con límites)
- **AWS/Azure/GCP** (Requiere configuración)

#### Ejemplo con Railway:
1. Ve a https://railway.app/
2. Conecta tu repositorio de GitHub
3. Selecciona la carpeta `cafeteria-orders-api`
4. Railway detectará Spring Boot automáticamente
5. Obtendrás una URL como: `https://tu-app.railway.app`

### 2. Configurar Variable de Entorno en Vercel

Cuando despliegues el frontend en Vercel:

1. Ve a tu proyecto en Vercel
2. Settings → Environment Variables
3. Agrega:
   - **Name:** `VITE_API_URL`
   - **Value:** `https://tu-backend-url.com/api` (la URL de tu backend desplegado)
   - **Environment:** Production, Preview, Development

### 3. Desplegar Frontend en Vercel

```bash
# Si no tienes Vercel CLI instalado
npm i -g vercel

# En la carpeta del frontend
cd cafeteria-frontend-full
vercel
```

O desde GitHub:
1. Ve a https://vercel.com/
2. Import Project
3. Selecciona tu repositorio
4. **Root Directory:** `cafeteria-frontend-full`
5. **Framework Preset:** Vite
6. Deploy

### 4. Commit y Push

```bash
git add .
git commit -m "Configuración para despliegue en Vercel"
git push
```

## ⚠️ Importante: CORS en el Backend

Cuando despliegues el backend, actualiza el CORS para permitir tu dominio de Vercel:

En cada controlador (`AuthController.java`, etc.), cambia:

```java
@CrossOrigin(origins = "*")  // Desarrollo
```

A:

```java
@CrossOrigin(origins = "https://tu-frontend.vercel.app")  // Producción
```

O mejor, configura CORS globalmente en `application.properties`:

```properties
# Permitir CORS desde Vercel
spring.web.cors.allowed-origins=https://tu-frontend.vercel.app,http://localhost:5173
spring.web.cors.allowed-methods=GET,POST,PUT,PATCH,DELETE,OPTIONS
spring.web.cors.allowed-headers=*
```

## 🔍 Troubleshooting

### Error: "No se puede conectar con el servidor"
- ✅ Verifica que el backend esté desplegado y funcionando
- ✅ Verifica la variable de entorno `VITE_API_URL` en Vercel
- ✅ Verifica que el backend tenga CORS habilitado para tu dominio de Vercel

### Error: "404 al refrescar la página"
- ✅ Ya está solucionado con `vercel.json`

### Error de build en Vercel
- ✅ Ya está solucionado (eliminado `tsc` del build)

## 📝 Checklist

- [x] Eliminar TypeScript del comando build
- [x] Crear `vercel.json` para SPA routing
- [x] Configurar variables de entorno para API URL
- [ ] Desplegar backend en Railway/Render
- [ ] Copiar URL del backend desplegado
- [ ] Configurar `VITE_API_URL` en Vercel
- [ ] Desplegar frontend en Vercel
- [ ] Actualizar CORS en el backend con la URL de Vercel
- [ ] Probar login y registro en producción

## 🎯 URLs Finales

- **Frontend:** `https://tu-proyecto.vercel.app`
- **Backend:** `https://tu-backend.railway.app` (o Render/Heroku)
- **Base de Datos:** Ya está en MongoDB Atlas (no requiere cambios)
