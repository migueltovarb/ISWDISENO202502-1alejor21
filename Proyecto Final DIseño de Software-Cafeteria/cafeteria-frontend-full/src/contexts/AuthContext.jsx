import { createContext, useContext, useState, useEffect } from 'react'
import { authAPI } from '../services/api'

const AuthContext = createContext(null)

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(true)

  // Cargar usuario del localStorage al iniciar
  useEffect(() => {
    const storedUser = localStorage.getItem('cafeteria_user')
    if (storedUser) {
      try {
        setUser(JSON.parse(storedUser))
      } catch (e) {
        localStorage.removeItem('cafeteria_user')
      }
    }
    setLoading(false)
  }, [])

  const login = async (credentials) => {
    try {
      const response = await authAPI.login(credentials)
      const userData = response.data
      setUser(userData)
      localStorage.setItem('cafeteria_user', JSON.stringify(userData))
      return { success: true, user: userData }
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || 'Error al iniciar sesión'
      }
    }
  }

  const logout = () => {
    setUser(null)
    localStorage.removeItem('cafeteria_user')
  }

  const isAdmin = () => {
    return user?.role === 'ADMIN'
  }

  const isEmployee = () => {
    return user?.role === 'EMPLOYEE'
  }

  const value = {
    user,
    loading,
    login,
    logout,
    isAdmin,
    isEmployee,
    isAuthenticated: !!user
  }

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}

export const useAuth = () => {
  const context = useContext(AuthContext)
  if (!context) {
    throw new Error('useAuth debe usarse dentro de un AuthProvider')
  }
  return context
}
