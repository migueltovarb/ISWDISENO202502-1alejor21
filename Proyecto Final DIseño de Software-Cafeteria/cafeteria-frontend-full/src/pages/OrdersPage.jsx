import { useState, useEffect } from 'react'
import { useNavigate } from 'react-router-dom'
import { ordersAPI, productsAPI, handleAPIError } from '../services/api'
import { LoadingSpinner } from '../components/LoadingSpinner'
import { ErrorMessage } from '../components/ErrorMessage'
import { useAuth } from '../contexts/AuthContext'

const ORDER_STATUSES = [
  { value: 'PENDING', label: 'Pendiente', color: '#f59e0b', nextStatus: 'PREPARING' },
  { value: 'PREPARING', label: 'Preparando', color: '#3b82f6', nextStatus: 'READY' },
  { value: 'READY', label: 'Listo', color: '#10b981', nextStatus: 'DELIVERED' },
  { value: 'DELIVERED', label: 'Entregado', color: '#6366f1', nextStatus: null }
]

const PAYMENT_METHODS = [
  { value: 'CASH', label: 'Efectivo', icon: '💵' },
  { value: 'CARD', label: 'Tarjeta', icon: '💳' },
  { value: 'ONLINE', label: 'En Línea', icon: '📱' }
]

export const OrdersPage = () => {
  const [view, setView] = useState('list') // 'list' o 'create'
  const [orders, setOrders] = useState([])
  const [products, setProducts] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const navigate = useNavigate()
  const { user } = useAuth()

  // Form state para crear pedido
  const [orderForm, setOrderForm] = useState({
    customerName: '',
    customerId: '',
    paymentMethod: 'CASH'
  })
  const [cart, setCart] = useState([])
  const [selectedProductId, setSelectedProductId] = useState('')
  const [quantity, setQuantity] = useState(1)

  useEffect(() => {
    loadOrders()
    loadProducts()
  }, [])

  const loadOrders = async () => {
    setLoading(true)
    setError('')
    try {
      const response = await ordersAPI.getAll()
      // Ordenar por fecha descendente
      const sortedOrders = response.data.sort((a, b) => {
        const dateA = new Date(a.createdAt || 0)
        const dateB = new Date(b.createdAt || 0)
        return dateB - dateA
      })
      setOrders(sortedOrders)
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const loadProducts = async () => {
    try {
      const response = await productsAPI.getAll()
      const activeProducts = response.data.filter(p => p.active)
      setProducts(activeProducts)
      if (activeProducts.length > 0) {
        setSelectedProductId(activeProducts[0].id)
      }
    } catch (err) {
      setError(handleAPIError(err))
    }
  }

  const handleOrderFormChange = (e) => {
    const { name, value } = e.target
    setOrderForm(prev => ({ ...prev, [name]: value }))
  }

  const addToCart = () => {
    if (!selectedProductId || quantity <= 0) return

    const product = products.find(p => p.id === selectedProductId)
    if (!product) return

    // Verificar si ya existe en el carrito
    const existingIndex = cart.findIndex(item => item.productId === product.id)

    if (existingIndex >= 0) {
      // Actualizar cantidad
      const newCart = [...cart]
      newCart[existingIndex].quantity += quantity
      setCart(newCart)
    } else {
      // Agregar nuevo
      setCart(prev => [
        ...prev,
        {
          productId: product.id,
          productName: product.name,
          quantity,
          unitPrice: product.price || 0
        }
      ])
    }

    setQuantity(1)
  }

  const removeFromCart = (index) => {
    setCart(prev => prev.filter((_, i) => i !== index))
  }

  const updateCartQuantity = (index, newQuantity) => {
    if (newQuantity <= 0) {
      removeFromCart(index)
      return
    }
    const newCart = [...cart]
    newCart[index].quantity = newQuantity
    setCart(newCart)
  }

  const getTotalAmount = () => {
    return cart.reduce((sum, item) => sum + ((item.unitPrice || 0) * item.quantity), 0)
  }

  const handleCreateOrder = async (e) => {
    e.preventDefault()

    if (cart.length === 0) {
      setError('Agrega al menos un producto al pedido')
      return
    }

    setLoading(true)
    setError('')

    try {
      const orderData = {
        ...orderForm,
        items: cart
      }

      await ordersAPI.create(orderData)
      
      // Resetear formulario
      setOrderForm({
        customerName: '',
        customerId: '',
        paymentMethod: 'CASH'
      })
      setCart([])
      setQuantity(1)

      // Volver a la lista y recargar
      setView('list')
      await loadOrders()
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const handleUpdateStatus = async (orderId, currentStatus) => {
    const statusData = ORDER_STATUSES.find(s => s.value === currentStatus)
    if (!statusData || !statusData.nextStatus) return

    setLoading(true)
    setError('')

    try {
      await ordersAPI.updateStatus(orderId, {
        status: statusData.nextStatus,
        changedBy: user?.id || 'unknown'
      })
      await loadOrders()
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const getStatusData = (status) => {
    return ORDER_STATUSES.find(s => s.value === status) || ORDER_STATUSES[0]
  }

  const getPaymentMethodData = (method) => {
    return PAYMENT_METHODS.find(m => m.value === method) || PAYMENT_METHODS[0]
  }

  return (
    <section className="section">
      <div className="page-header">
        <h1 className="page-title">🛍️ Gestión de Pedidos</h1>
        <p className="page-description">Crea y administra los pedidos de tus clientes</p>
      </div>

      {/* Botones de navegación */}
      <div className="view-toggle">
        <button
          className={`button ${view === 'list' ? 'primary' : 'secondary'}`}
          onClick={() => setView('list')}
        >
          📋 Lista de Pedidos
        </button>
        <button
          className={`button ${view === 'create' ? 'primary' : 'secondary'}`}
          onClick={() => setView('create')}
        >
          ➕ Crear Nuevo Pedido
        </button>
      </div>

      {error && <ErrorMessage message={error} onClose={() => setError('')} />}

      {/* Vista de creación de pedido */}
      {view === 'create' && (
        <div className="card glass-card">
          <div className="card-header">
            <div className="card-icon">🆕</div>
            <div>
              <div className="card-title">Nuevo Pedido</div>
              <div className="card-subtitle">Registra un pedido de cliente</div>
            </div>
          </div>

          <form onSubmit={handleCreateOrder}>
            {/* Información del cliente */}
            <div className="form-section">
              <h3 className="form-section-title">📝 Información del Cliente</h3>
              <div className="form-grid">
                <label className="label">
                  Nombre del cliente *
                  <input
                    type="text"
                    name="customerName"
                    className="input"
                    value={orderForm.customerName}
                    onChange={handleOrderFormChange}
                    placeholder="Ej: Juan Pérez"
                    required
                  />
                </label>

                <label className="label">
                  Identificación
                  <input
                    type="text"
                    name="customerId"
                    className="input"
                    value={orderForm.customerId}
                    onChange={handleOrderFormChange}
                    placeholder="Opcional"
                  />
                </label>

                <label className="label">
                  Método de pago *
                  <select
                    name="paymentMethod"
                    className="select"
                    value={orderForm.paymentMethod}
                    onChange={handleOrderFormChange}
                    required
                  >
                    {PAYMENT_METHODS.map(method => (
                      <option key={method.value} value={method.value}>
                        {method.icon} {method.label}
                      </option>
                    ))}
                  </select>
                </label>
              </div>
            </div>

            {/* Productos */}
            <div className="form-section">
              <h3 className="form-section-title">🍽️ Productos del Pedido</h3>
              
              <div className="add-product-bar">
                <select
                  className="select flex-grow"
                  value={selectedProductId}
                  onChange={(e) => setSelectedProductId(e.target.value)}
                  disabled={products.length === 0}
                >
                  {products.length === 0 ? (
                    <option value="">No hay productos disponibles</option>
                  ) : (
                    products.map(product => (
                      <option key={product.id} value={product.id}>
                        {product.name} - ${(product.price || 0).toFixed(2)}
                      </option>
                    ))
                  )}
                </select>

                <input
                  type="number"
                  className="input quantity-input"
                  value={quantity}
                  onChange={(e) => setQuantity(Number(e.target.value))}
                  min="1"
                />

                <button
                  type="button"
                  className="button primary"
                  onClick={addToCart}
                  disabled={products.length === 0}
                >
                  ➕ Agregar
                </button>
              </div>

              {/* Carrito */}
              {cart.length > 0 ? (
                <div className="cart-container">
                  <table className="table">
                    <thead>
                      <tr>
                        <th>Producto</th>
                        <th>Precio Unit.</th>
                        <th>Cantidad</th>
                        <th>Subtotal</th>
                        <th>Acciones</th>
                      </tr>
                    </thead>
                    <tbody>
                      {cart.map((item, index) => (
                        <tr key={index}>
                          <td>{item.productName}</td>
                          <td>${(item.unitPrice || 0).toFixed(2)}</td>
                          <td>
                            <input
                              type="number"
                              className="input quantity-input-small"
                              value={item.quantity}
                              onChange={(e) => updateCartQuantity(index, Number(e.target.value))}
                              min="1"
                            />
                          </td>
                          <td>
                            <strong>${((item.unitPrice || 0) * item.quantity).toFixed(2)}</strong>
                          </td>
                          <td>
                            <button
                              type="button"
                              className="button small danger"
                              onClick={() => removeFromCart(index)}
                            >
                              🗑️
                            </button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                    <tfoot>
                      <tr>
                        <td colSpan="3" className="text-right"><strong>Total:</strong></td>
                        <td colSpan="2">
                          <strong className="total-amount">${getTotalAmount().toFixed(2)}</strong>
                        </td>
                      </tr>
                    </tfoot>
                  </table>
                </div>
              ) : (
                <div className="empty-cart">
                  <p>🛍️ El carrito está vacío</p>
                  <p className="empty-hint">
                    {products.length === 0 
                      ? 'No hay productos disponibles. Si eres ADMIN, ve a "Productos" para crear el menú.' 
                      : 'Agrega productos para crear el pedido'}
                  </p>
                </div>
              )}
            </div>

            <div className="button-row">
              <button
                type="submit"
                className="button primary"
                disabled={loading || cart.length === 0}
              >
                {loading ? 'Creando...' : '✅ Crear Pedido'}
              </button>
              <button
                type="button"
                className="button secondary"
                onClick={() => setView('list')}
                disabled={loading}
              >
                Cancelar
              </button>
            </div>
          </form>
        </div>
      )}

      {/* Vista de lista de pedidos */}
      {view === 'list' && (
        <div className="card glass-card">
          <div className="card-header">
            <div className="card-icon">📦</div>
            <div>
              <div className="card-title">Pedidos Activos</div>
              <div className="card-subtitle">{orders.length} pedidos registrados</div>
            </div>
            <button className="button primary" onClick={loadOrders} disabled={loading}>
              🔄 Recargar
            </button>
          </div>

          {loading && orders.length === 0 && <LoadingSpinner />}

          {!loading && orders.length === 0 && (
            <div className="empty-state">
              <div className="empty-icon">📝</div>
              <p className="empty-text">No hay pedidos registrados</p>
              <p className="empty-hint">Crea tu primer pedido usando el botón de arriba</p>
            </div>
          )}

          {orders.length > 0 && (
            <div className="orders-list">
              {orders.map(order => {
                const statusData = getStatusData(order.status)
                const paymentData = getPaymentMethodData(order.paymentMethod)
                const canAdvance = statusData.nextStatus !== null

                return (
                  <div key={order.id} className="order-card">
                    <div className="order-header">
                      <div className="order-number">
                        <span className="order-icon">🧾</span>
                        <span className="order-title">
                          Pedido #{order.orderNumber || order.id?.substring(0, 8)}
                        </span>
                      </div>
                      <span
                        className="order-status-badge"
                        style={{ backgroundColor: statusData.color }}
                      >
                        {statusData.label}
                      </span>
                    </div>

                    <div className="order-details">
                      <div className="order-detail-row">
                        <span className="detail-label">👤 Cliente:</span>
                        <span className="detail-value">{order.customerName}</span>
                      </div>
                      {order.customerId && (
                        <div className="order-detail-row">
                          <span className="detail-label">🆔 ID:</span>
                          <span className="detail-value">{order.customerId}</span>
                        </div>
                      )}
                      <div className="order-detail-row">
                        <span className="detail-label">{paymentData.icon} Pago:</span>
                        <span className="detail-value">{paymentData.label}</span>
                      </div>
                      <div className="order-detail-row">
                        <span className="detail-label">💰 Total:</span>
                        <span className="detail-value total">${order.totalAmount?.toFixed(2) || '0.00'}</span>
                      </div>
                      {order.estimatedTimeMinutes && (
                        <div className="order-detail-row">
                          <span className="detail-label">⏱️ Tiempo estimado:</span>
                          <span className="detail-value">{order.estimatedTimeMinutes} min</span>
                        </div>
                      )}
                      {order.createdAt && (
                        <div className="order-detail-row">
                          <span className="detail-label">📅 Fecha:</span>
                          <span className="detail-value">
                            {new Date(order.createdAt).toLocaleString('es-ES')}
                          </span>
                        </div>
                      )}
                    </div>

                    {/* Items del pedido */}
                    {order.items && order.items.length > 0 && (
                      <div className="order-items-summary">
                        <strong>Productos:</strong>
                        <div className="items-chips">
                          {order.items.map((item, idx) => (
                            <span key={idx} className="item-chip">
                              {item.quantity}x {item.productName}
                            </span>
                          ))}
                        </div>
                      </div>
                    )}

                    <div className="order-actions">
                      <button
                        className="button small secondary"
                        onClick={() => navigate(`/orders/${order.id}`)}
                      >
                        👁️ Ver Detalle
                      </button>
                      
                      {canAdvance && (
                        <button
                          className="button small primary"
                          onClick={() => handleUpdateStatus(order.id, order.status)}
                          disabled={loading}
                        >
                          ➡️ Avanzar a {getStatusData(statusData.nextStatus).label}
                        </button>
                      )}

                      {order.status === 'READY' && (
                        <div className="ready-indicator">
                          ✅ Listo para recoger
                        </div>
                      )}

                      {order.status === 'DELIVERED' && (
                        <div className="delivered-indicator">
                          ✔️ Completado
                        </div>
                      )}
                    </div>
                  </div>
                )
              })}
            </div>
          )}
        </div>
      )}
    </section>
  )
}
