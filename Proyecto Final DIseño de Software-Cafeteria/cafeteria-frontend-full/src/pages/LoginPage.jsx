import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../contexts/AuthContext'
import { ErrorMessage } from '../components/ErrorMessage'

export const LoginPage = () => {
  const [mode, setMode] = useState('login') // 'login' o 'register'
  const [credentials, setCredentials] = useState({
    username: '',
    password: '',
    fullName: '',
    email: '',
    role: 'STUDENT' // Por defecto estudiante
  })
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const { login } = useAuth()
  const navigate = useNavigate()

  const handleChange = (e) => {
    const { name, value } = e.target
    setCredentials(prev => ({ ...prev, [name]: value }))
    setError('')
  }

  const handleRegister = async (e) => {
    e.preventDefault()
    setLoading(true)
    setError('')

    try {
      const { authAPI } = await import('../services/api')
      
      console.log('🔵 Registrando usuario con datos:', {
        username: credentials.username,
        fullName: credentials.fullName,
        email: credentials.email,
        role: credentials.role
      })
      
      const registerResponse = await authAPI.register({
        username: credentials.username,
        password: credentials.password,
        fullName: credentials.fullName,
        email: credentials.email,
        role: credentials.role
      })
      
      console.log('✅ Usuario registrado:', registerResponse.data)

      // Después de registrarse, hacer login automático
      const result = await login({
        username: credentials.username,
        password: credentials.password
      })
      
      console.log('✅ Login automático:', result)

      if (result.success) {
        // Redirigir según el rol
        if (result.user.role === 'ADMIN') {
          navigate('/products')
        } else if (result.user.role === 'EMPLOYEE') {
          navigate('/orders')
        } else {
          // STUDENT o STAFF van a hacer pedidos
          navigate('/orders')
        }
      } else {
        setError(result.error)
      }
    } catch (err) {
      console.error('❌ Error en registro:', err)
      setError(err.response?.data?.message || 'Error al registrar usuario')
    }

    setLoading(false)
  }

  const handleGuestAccess = () => {
    // Acceso como invitado - ir directamente a ver productos sin autenticación
    navigate('/orders')
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    setError('')

    const result = await login(credentials)

    if (result.success) {
      // Redirigir según el rol
      if (result.user.role === 'ADMIN') {
        navigate('/products')
      } else if (result.user.role === 'EMPLOYEE') {
        navigate('/orders')
      } else {
        // STUDENT o STAFF
        navigate('/orders')
      }
    } else {
      setError(result.error)
    }

    setLoading(false)
  }

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <div className="login-logo">☕</div>
          <h1 className="login-title">Sistema de Cafetería</h1>
          <p className="login-subtitle">
            {mode === 'login' 
              ? 'Ingresa tus credenciales para continuar' 
              : 'Crea tu cuenta para empezar'}
          </p>
        </div>

        <form onSubmit={mode === 'login' ? handleSubmit : handleRegister} className="login-form">
          {mode === 'register' && (
            <>
              <div className="form-group">
                <label className="label">
                  <span className="label-icon">✨</span>
                  Nombre Completo
                </label>
                <input
                  type="text"
                  name="fullName"
                  className="input"
                  value={credentials.fullName}
                  onChange={handleChange}
                  placeholder="Ej: Juan Pérez"
                  required
                  autoFocus
                />
              </div>

              <div className="form-group">
                <label className="label">
                  <span className="label-icon">📧</span>
                  Email
                </label>
                <input
                  type="email"
                  name="email"
                  className="input"
                  value={credentials.email}
                  onChange={handleChange}
                  placeholder="tu@email.com"
                  required
                />
              </div>

              <div className="form-group">
                <label className="label">
                  <span className="label-icon">👥</span>
                  Tipo de Usuario
                </label>
                <select
                  name="role"
                  className="input"
                  value={credentials.role}
                  onChange={handleChange}
                  required
                >
                  <option value="STUDENT">🎓 Estudiante (con promociones)</option>
                  <option value="STAFF">👔 Personal del Campus</option>
                  <option value="EMPLOYEE">👤 Empleado/Cajero</option>
                  <option value="ADMIN">⚙️ Administrador</option>
                </select>
              </div>
            </>
          )}

          <div className="form-group">
            <label className="label">
              <span className="label-icon">👤</span>
              Usuario
            </label>
            <input
              type="text"
              name="username"
              className="input"
              value={credentials.username}
              onChange={handleChange}
              placeholder="Ingresa tu usuario"
              required
              autoFocus={mode === 'login'}
            />
          </div>

          <div className="form-group">
            <label className="label">
              <span className="label-icon">🔒</span>
              Contraseña
            </label>
            <input
              type="password"
              name="password"
              className="input"
              value={credentials.password}
              onChange={handleChange}
              placeholder="Ingresa tu contraseña"
              required
            />
          </div>

          <ErrorMessage message={error} onClose={() => setError('')} />

          <button
            type="submit"
            className="button primary login-button"
            disabled={loading}
          >
            {loading 
              ? (mode === 'login' ? 'Iniciando sesión...' : 'Registrando...') 
              : (mode === 'login' ? 'Iniciar Sesión' : 'Registrarse')}
          </button>
        </form>

        <div className="login-divider">
          <span>o</span>
        </div>

        <div className="login-actions">
          <button
            type="button"
            className="button secondary"
            onClick={handleGuestAccess}
          >
            🎫 Continuar como Invitado
          </button>

          <button
            type="button"
            className="button-link"
            onClick={() => {
              setMode(mode === 'login' ? 'register' : 'login')
              setError('')
              setCredentials({
                username: '',
                password: '',
                fullName: '',
                email: '',
                role: 'STUDENT'
              })
            }}
          >
            {mode === 'login' 
              ? '¿No tienes cuenta? Regístrate' 
              : '¿Ya tienes cuenta? Inicia sesión'}
          </button>
        </div>

        <div className="login-footer">
          <p className="login-help">
            Sistema de Gestión de Pedidos v1.0
          </p>
        </div>
      </div>
    </div>
  )
}
