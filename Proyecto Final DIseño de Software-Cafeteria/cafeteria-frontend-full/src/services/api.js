import axios from 'axios'

const API_BASE_URL = 'http://localhost:8080/api'

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

// ============================================
// AUTENTICACIÓN Y USUARIOS (HU009)
// ============================================

export const authAPI = {
  login: (credentials) => api.post('/auth/login', credentials),
  register: (userData) => api.post('/auth/register', userData),
}

export const usersAPI = {
  getAll: () => api.get('/users'),
  getById: (id) => api.get(`/users/${id}`),
  create: (userData) => api.post('/users', userData),
  update: (id, userData) => api.put(`/users/${id}`, userData),
  delete: (id) => api.delete(`/users/${id}`), // Desactiva el usuario
}

// ============================================
// PRODUCTOS / MENÚ (HU001, HU002)
// ============================================

export const productsAPI = {
  getAll: () => api.get('/products'),
  getById: (id) => api.get(`/products/${id}`),
  create: (productData) => api.post('/products', productData),
  update: (id, productData) => api.put(`/products/${id}`, productData),
  delete: (id) => api.delete(`/products/${id}`),
}

// ============================================
// PEDIDOS (HU003, HU004, HU005, HU006, HU010)
// ============================================

export const ordersAPI = {
  getAll: () => api.get('/orders'),
  getById: (id) => api.get(`/orders/${id}`),
  create: (orderData) => api.post('/orders', orderData),
  update: (id, orderData) => api.put(`/orders/${id}`, orderData),
  delete: (id) => api.delete(`/orders/${id}`),
  updateStatus: (id, statusData) => api.patch(`/orders/${id}/status`, statusData),
}

// ============================================
// REPORTES (HU007, HU008)
// ============================================

export const reportsAPI = {
  getDailyReport: (date) => api.get(`/reports/daily?date=${date}`),
}

// ============================================
// UTILIDADES
// ============================================

export const handleAPIError = (error) => {
  if (error.response) {
    // El servidor respondió con un código de estado fuera del rango 2xx
    return error.response.data?.message || `Error: ${error.response.status}`
  } else if (error.request) {
    // La solicitud se realizó pero no se recibió respuesta
    return 'No se pudo conectar con el servidor'
  } else {
    // Algo pasó al configurar la solicitud
    return error.message || 'Error desconocido'
  }
}

export default api
