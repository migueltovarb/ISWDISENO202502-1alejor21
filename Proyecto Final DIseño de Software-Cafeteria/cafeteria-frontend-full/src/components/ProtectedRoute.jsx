import { Navigate } from 'react-router-dom'
import { useAuth } from '../contexts/AuthContext'
import { LoadingSpinner } from './LoadingSpinner'

export const ProtectedRoute = ({ children, requireAdmin = false, allowGuest = false }) => {
  const { user, loading, isAdmin } = useAuth()

  if (loading) {
    return <LoadingSpinner size="large" />
  }

  // Si no permite invitados y no hay usuario, redirigir al login
  if (!allowGuest && !user) {
    return <Navigate to="/login" replace />
  }

  // Si requiere admin y el usuario no es admin, redirigir a pedidos
  if (requireAdmin && !isAdmin()) {
    return <Navigate to="/orders" replace />
  }

  return children
}
