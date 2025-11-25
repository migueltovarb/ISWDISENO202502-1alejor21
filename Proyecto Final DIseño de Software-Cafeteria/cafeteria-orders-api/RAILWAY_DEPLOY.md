# 🚀 Guía de Despliegue en Railway

## Paso 1: Preparar tu cuenta de Railway

1. Ve a [Railway.app](https://railway.app/)
2. Inicia sesión con GitHub
3. Autoriza Railway a acceder a tus repositorios

## Paso 2: Crear Nuevo Proyecto

1. Click en **"New Project"**
2. Selecciona **"Deploy from GitHub repo"**
3. Busca y selecciona: **`alejor21/Sistema-Cafeteria-`**
4. Railway detectará automáticamente que es un proyecto Java/Maven

## Paso 3: Configurar Root Directory

⚠️ **IMPORTANTE**: Como el backend está en una subcarpeta:

1. En el dashboard de Railway, selecciona tu servicio
2. Ve a **Settings** (⚙️)
3. Busca la sección **"Root Directory"**
4. Ingresa: `cafeteria-orders-api`
5. Click en **"Save"**

## Paso 4: Configurar Variables de Entorno

1. Ve a la pestaña **"Variables"** en tu servicio
2. Agrega las siguientes variables:

```bash
# MongoDB URI (usa tu conexión de MongoDB Atlas)
MONGODB_URI=mongodb+srv://brayanrojass_db_user:Alejo.20324@cluster0.qnjkzbf.mongodb.net/cafeteria_db?retryWrites=true&w=majority&appName=Cluster0

# Base de datos
MONGODB_DATABASE=cafeteria_db

# Puerto (Railway lo asigna automáticamente)
PORT=8080
```

3. Click en **"Add Variable"** para cada una

## Paso 5: Configurar MongoDB Atlas

⚠️ **Permitir acceso desde Railway**:

1. Ve a [MongoDB Atlas](https://cloud.mongodb.com/)
2. Selecciona tu cluster
3. Click en **"Network Access"**
4. Click en **"Add IP Address"**
5. Selecciona **"Allow Access from Anywhere"** (0.0.0.0/0)
6. Click en **"Confirm"**

## Paso 6: Desplegar

Railway desplegará automáticamente. Puedes ver el progreso en:

1. Pestaña **"Deployments"**
2. Click en el deployment activo para ver los logs

Los logs mostrarán:
```
✓ Building...
✓ Building cafeteria-orders-api with Maven
✓ Starting application...
✓ Application started on port $PORT
```

## Paso 7: Obtener la URL Pública

1. Ve a **Settings** > **Networking**
2. Click en **"Generate Domain"**
3. Copia la URL generada (ej: `https://cafeteria-api-production.up.railway.app`)

## Paso 8: Probar el Deployment

Abre tu navegador y prueba:

```
https://TU-URL.up.railway.app/api/products
```

Deberías ver la lista de productos.

## Paso 9: Actualizar el Frontend

Actualiza la variable de entorno en Vercel:

1. Ve a tu proyecto en [Vercel](https://vercel.com/)
2. Settings > Environment Variables
3. Edita `VITE_API_URL` con tu nueva URL de Railway:
   ```
   https://TU-URL.up.railway.app
   ```
4. Redeploy el frontend

## 📝 Comandos Útiles (Railway CLI)

Si prefieres usar la línea de comandos:

```bash
# Instalar Railway CLI
npm i -g @railway/cli

# Login
railway login

# Vincular proyecto
cd cafeteria-orders-api
railway link

# Ver logs
railway logs

# Ver variables
railway variables

# Abrir en navegador
railway open
```

## ✅ Verificación Final

- [ ] Backend desplegado en Railway
- [ ] Variables de entorno configuradas
- [ ] MongoDB Atlas permite conexiones desde Railway (0.0.0.0/0)
- [ ] URL pública generada y funcionando
- [ ] Frontend en Vercel apunta a la nueva URL
- [ ] Endpoints respondiendo correctamente

## 🐛 Solución de Problemas

### Error: "Application failed to start"
- Verifica que el Root Directory sea `cafeteria-orders-api`
- Revisa las variables de entorno (especialmente MONGODB_URI)
- Verifica los logs en Railway

### Error: "MongoTimeoutException"
- Asegúrate de que MongoDB Atlas permite acceso desde 0.0.0.0/0
- Verifica que la URI de MongoDB sea correcta

### Error: "Port already in use"
- Railway asigna el puerto automáticamente mediante `$PORT`
- Verifica que `application.properties` use `${PORT:8080}`

## 📚 Referencias

- [Railway Documentation](https://docs.railway.app/)
- [Railway Java Guide](https://docs.railway.app/guides/java)
- [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
