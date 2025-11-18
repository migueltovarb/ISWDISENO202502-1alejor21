import { useState, useEffect } from 'react'
import { usersAPI, handleAPIError } from '../services/api'
import { LoadingSpinner } from '../components/LoadingSpinner'
import { ErrorMessage } from '../components/ErrorMessage'

export const UsersPage = () => {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [showForm, setShowForm] = useState(false)
  const [editingUser, setEditingUser] = useState(null)
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    fullName: '',
    role: 'EMPLOYEE',
    active: true
  })

  useEffect(() => {
    loadUsers()
  }, [])

  const loadUsers = async () => {
    setLoading(true)
    setError('')
    try {
      const response = await usersAPI.getAll()
      setUsers(response.data)
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setLoading(true)
    setError('')

    try {
      if (editingUser) {
        await usersAPI.update(editingUser.id, formData)
      } else {
        await usersAPI.create(formData)
      }
      await loadUsers()
      resetForm()
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const handleEdit = (user) => {
    setEditingUser(user)
    setFormData({
      username: user.username,
      password: '', // No mostrar la contraseña
      fullName: user.fullName,
      role: user.role,
      active: user.active
    })
    setShowForm(true)
  }

  const handleDelete = async (id) => {
    if (!confirm('¿Estás seguro de desactivar este usuario?')) return

    setLoading(true)
    setError('')
    try {
      await usersAPI.delete(id)
      await loadUsers()
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const resetForm = () => {
    setFormData({
      username: '',
      password: '',
      fullName: '',
      role: 'EMPLOYEE',
      active: true
    })
    setEditingUser(null)
    setShowForm(false)
  }

  return (
    <section className="section">
      <div className="page-header">
        <h1 className="page-title">👥 Gestión de Usuarios</h1>
        <p className="page-description">Administra los usuarios del sistema</p>
      </div>

      <div className="card glass-card">
        <div className="card-header">
          <div>
            <div className="card-title">
              {showForm ? (editingUser ? 'Editar Usuario' : 'Nuevo Usuario') : 'Usuarios'}
            </div>
            <div className="card-subtitle">{users.length} usuarios registrados</div>
          </div>
          <button
            className="button primary"
            onClick={() => setShowForm(!showForm)}
          >
            {showForm ? '← Cancelar' : '+ Nuevo Usuario'}
          </button>
        </div>

        {showForm && (
          <form onSubmit={handleSubmit} className="user-form">
            <div className="form-grid">
              <label className="label">
                Nombre completo
                <input
                  type="text"
                  name="fullName"
                  className="input"
                  value={formData.fullName}
                  onChange={handleChange}
                  required
                />
              </label>

              <label className="label">
                Usuario
                <input
                  type="text"
                  name="username"
                  className="input"
                  value={formData.username}
                  onChange={handleChange}
                  required
                />
              </label>

              <label className="label">
                {editingUser ? 'Nueva contraseña (dejar vacío para no cambiar)' : 'Contraseña'}
                <input
                  type="password"
                  name="password"
                  className="input"
                  value={formData.password}
                  onChange={handleChange}
                  required={!editingUser}
                />
              </label>

              <label className="label">
                Rol
                <select
                  name="role"
                  className="select"
                  value={formData.role}
                  onChange={handleChange}
                  required
                >
                  <option value="EMPLOYEE">Empleado</option>
                  <option value="ADMIN">Administrador</option>
                </select>
              </label>

              <label className="label checkbox-label">
                <input
                  type="checkbox"
                  name="active"
                  checked={formData.active}
                  onChange={handleChange}
                />
                <span>Usuario activo</span>
              </label>
            </div>

            <ErrorMessage message={error} onClose={() => setError('')} />

            <div className="button-row">
              <button type="submit" className="button primary" disabled={loading}>
                {loading ? 'Guardando...' : editingUser ? 'Actualizar' : 'Crear'}
              </button>
              <button
                type="button"
                className="button secondary"
                onClick={resetForm}
                disabled={loading}
              >
                Cancelar
              </button>
            </div>
          </form>
        )}

        {!showForm && (
          <>
            {loading && users.length === 0 && <LoadingSpinner />}

            {!loading && users.length === 0 && (
              <div className="empty-state">
                <div className="empty-icon">👥</div>
                <p className="empty-text">No hay usuarios registrados</p>
                <p className="empty-hint">Crea el primer usuario</p>
              </div>
            )}

            {users.length > 0 && (
              <div className="table-container">
                <table className="table">
                  <thead>
                    <tr>
                      <th>Nombre completo</th>
                      <th>Usuario</th>
                      <th>Rol</th>
                      <th>Estado</th>
                      <th>Acciones</th>
                    </tr>
                  </thead>
                  <tbody>
                    {users.map(user => (
                      <tr key={user.id}>
                        <td>{user.fullName}</td>
                        <td>{user.username}</td>
                        <td>
                          <span className={`tag ${user.role === 'ADMIN' ? 'tag-admin' : 'tag-employee'}`}>
                            {user.role}
                          </span>
                        </td>
                        <td>
                          <span className={`tag ${user.active ? 'tag-active' : 'tag-inactive'}`}>
                            {user.active ? 'Activo' : 'Inactivo'}
                          </span>
                        </td>
                        <td>
                          <div className="action-buttons">
                            <button
                              className="button small secondary"
                              onClick={() => handleEdit(user)}
                            >
                              ✏️ Editar
                            </button>
                            <button
                              className="button small danger"
                              onClick={() => handleDelete(user.id)}
                              disabled={!user.active}
                            >
                              🗑️ Desactivar
                            </button>
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            )}
          </>
        )}
      </div>
    </section>
  )
}
