import { useState, useEffect } from 'react'
import { productsAPI, handleAPIError } from '../services/api'
import { LoadingSpinner } from '../components/LoadingSpinner'
import { ErrorMessage } from '../components/ErrorMessage'

const CATEGORIES = [
  { value: 'HOT_DRINK', label: 'Bebida Caliente', emoji: '☕' },
  { value: 'COLD_DRINK', label: 'Bebida Fría', emoji: '🥤' },
  { value: 'FAST_FOOD', label: 'Comida Rápida', emoji: '🍔' },
  { value: 'DESSERT', label: 'Postre', emoji: '🍰' }
]

export const ProductsPage = () => {
  const [products, setProducts] = useState([])
  const [filteredProducts, setFilteredProducts] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [showForm, setShowForm] = useState(false)
  const [editingProduct, setEditingProduct] = useState(null)
  const [searchTerm, setSearchTerm] = useState('')
  const [categoryFilter, setCategoryFilter] = useState('ALL')
  const [formData, setFormData] = useState({
    name: '',
    category: 'HOT_DRINK',
    price: 0,
    active: true
  })

  useEffect(() => {
    loadProducts()
  }, [])

  useEffect(() => {
    filterProducts()
  }, [products, searchTerm, categoryFilter])

  const loadProducts = async () => {
    setLoading(true)
    setError('')
    try {
      const response = await productsAPI.getAll()
      setProducts(response.data)
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const filterProducts = () => {
    let filtered = products

    // Filtrar por categoría
    if (categoryFilter !== 'ALL') {
      filtered = filtered.filter(p => p.category === categoryFilter)
    }

    // Filtrar por búsqueda
    if (searchTerm) {
      filtered = filtered.filter(p =>
        p.name.toLowerCase().includes(searchTerm.toLowerCase())
      )
    }

    setFilteredProducts(filtered)
  }

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : type === 'number' ? Number(value) : value
    }))
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    if (formData.price <= 0) {
      setError('El precio debe ser mayor a 0')
      return
    }

    setLoading(true)
    setError('')

    try {
      if (editingProduct) {
        await productsAPI.update(editingProduct.id, formData)
      } else {
        await productsAPI.create(formData)
      }
      await loadProducts()
      resetForm()
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const handleEdit = (product) => {
    setEditingProduct(product)
    setFormData({
      name: product.name,
      category: product.category,
      price: product.price || 0,
      active: product.active
    })
    setShowForm(true)
  }

  const handleDelete = async (id) => {
    if (!confirm('¿Estás seguro de eliminar este producto?')) return

    setLoading(true)
    setError('')
    try {
      await productsAPI.delete(id)
      await loadProducts()
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const resetForm = () => {
    setFormData({
      name: '',
      category: 'HOT_DRINK',
      price: 0,
      active: true
    })
    setEditingProduct(null)
    setShowForm(false)
  }

  const getCategoryData = (category) => {
    return CATEGORIES.find(c => c.value === category) || { emoji: '🍽️', label: category }
  }

  return (
    <section className="section">
      <div className="page-header">
        <h1 className="page-title">🍽️ Gestión de Productos</h1>
        <p className="page-description">Administra el menú de tu cafetería</p>
      </div>

      <div className="card glass-card">
        <div className="card-header">
          <div>
            <div className="card-title">
              {showForm ? (editingProduct ? 'Editar Producto' : 'Nuevo Producto') : 'Productos'}
            </div>
            <div className="card-subtitle">{products.length} productos en el menú</div>
          </div>
          <button
            className="button primary"
            onClick={() => setShowForm(!showForm)}
          >
            {showForm ? '← Cancelar' : '+ Nuevo Producto'}
          </button>
        </div>

        {showForm && (
          <form onSubmit={handleSubmit} className="product-form">
            <div className="form-grid">
              <label className="label">
                Nombre del producto
                <input
                  type="text"
                  name="name"
                  className="input"
                  value={formData.name}
                  onChange={handleChange}
                  placeholder="Ej: Café Americano"
                  required
                />
              </label>

              <label className="label">
                Categoría
                <select
                  name="category"
                  className="select"
                  value={formData.category}
                  onChange={handleChange}
                  required
                >
                  {CATEGORIES.map(cat => (
                    <option key={cat.value} value={cat.value}>
                      {cat.emoji} {cat.label}
                    </option>
                  ))}
                </select>
              </label>

              <label className="label">
                Precio unitario
                <input
                  type="number"
                  name="price"
                  className="input"
                  value={formData.price}
                  onChange={handleChange}
                  min="0"
                  step="0.01"
                  placeholder="0.00"
                  required
                />
              </label>

              <label className="label checkbox-label">
                <input
                  type="checkbox"
                  name="active"
                  checked={formData.active}
                  onChange={handleChange}
                />
                <span>Producto activo</span>
              </label>
            </div>

            <ErrorMessage message={error} onClose={() => setError('')} />

            <div className="button-row">
              <button type="submit" className="button primary" disabled={loading}>
                {loading ? 'Guardando...' : editingProduct ? 'Actualizar' : 'Crear'}
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
            {/* Filtros */}
            <div className="filters-bar">
              <div className="search-box">
                <span className="search-icon">🔍</span>
                <input
                  type="text"
                  className="input search-input"
                  placeholder="Buscar producto..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                />
              </div>

              <div className="category-filters">
                <button
                  className={`filter-chip ${categoryFilter === 'ALL' ? 'active' : ''}`}
                  onClick={() => setCategoryFilter('ALL')}
                >
                  Todos
                </button>
                {CATEGORIES.map(cat => (
                  <button
                    key={cat.value}
                    className={`filter-chip ${categoryFilter === cat.value ? 'active' : ''}`}
                    onClick={() => setCategoryFilter(cat.value)}
                  >
                    {cat.emoji} {cat.label}
                  </button>
                ))}
              </div>
            </div>

            {loading && products.length === 0 && <LoadingSpinner />}

            {!loading && filteredProducts.length === 0 && (
              <div className="empty-state">
                <div className="empty-icon">🍽️</div>
                <p className="empty-text">
                  {searchTerm || categoryFilter !== 'ALL'
                    ? 'No se encontraron productos con estos filtros'
                    : 'No hay productos en el menú'}
                </p>
                <p className="empty-hint">
                  {searchTerm || categoryFilter !== 'ALL'
                    ? 'Intenta con otros criterios de búsqueda'
                    : 'Crea el primer producto'}
                </p>
              </div>
            )}

            {filteredProducts.length > 0 && (
              <div className="products-grid">
                {filteredProducts.map(product => {
                  const categoryData = getCategoryData(product.category)
                  return (
                    <div key={product.id} className="product-card">
                      <div className="product-emoji">{categoryData.emoji}</div>
                      <div className="product-info">
                        <h3 className="product-name">{product.name}</h3>
                        <div className="product-category">{categoryData.label}</div>
                      </div>
                      <div className="product-price">${(product.price || 0).toFixed(2)}</div>
                      <div className="product-status">
                        <span className={`tag ${product.active ? 'tag-active' : 'tag-inactive'}`}>
                          {product.active ? 'Activo' : 'Inactivo'}
                        </span>
                      </div>
                      <div className="product-actions">
                        <button
                          className="button small secondary"
                          onClick={() => handleEdit(product)}
                        >
                          ✏️ Editar
                        </button>
                        <button
                          className="button small danger"
                          onClick={() => handleDelete(product.id)}
                        >
                          🗑️ Eliminar
                        </button>
                      </div>
                    </div>
                  )
                })}
              </div>
            )}
          </>
        )}
      </div>
    </section>
  )
}
