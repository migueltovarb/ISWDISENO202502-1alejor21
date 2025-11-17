import { useEffect, useState } from 'react'

const API_BASE = 'http://localhost:8080'

function App() {
  const [tab, setTab] = useState('products')

  return (
    <div className="app">
      <header className="app-header">
        <div className="header-content">
          <div className="logo-container">
            <div className="logo-icon">☕</div>
            <div className="logo-text">
              <div className="app-title">Cafetería</div>
              <div className="app-tagline">Sistema de gestión</div>
            </div>
          </div>
          <div className="header-actions">
            <div className="time-display">{new Date().toLocaleDateString('es-ES', { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}</div>
          </div>
        </div>
      </header>

      <nav className="nav-tabs">
        <button
          className={`nav-tab ${tab === 'products' ? 'active' : ''}`}
          onClick={() => setTab('products')}
        >
          <span className="tab-icon">🍽️</span>
          <span className="tab-label">Menú</span>
        </button>
        <button
          className={`nav-tab ${tab === 'orders' ? 'active' : ''}`}
          onClick={() => setTab('orders')}
        >
          <span className="tab-icon">🛍️</span>
          <span className="tab-label">Pedidos</span>
        </button>
        <button
          className={`nav-tab ${tab === 'reports' ? 'active' : ''}`}
          onClick={() => setTab('reports')}
        >
          <span className="tab-icon">📊</span>
          <span className="tab-label">Reportes</span>
        </button>
      </nav>

      {tab === 'products' && <ProductsSection />}
      {tab === 'orders' && <OrdersSection />}
      {tab === 'reports' && <ReportsSection />}
    </div>
  )
}

function ProductsSection() {
  const [products, setProducts] = useState([])
  const [form, setForm] = useState({
    name: '',
    category: 'HOT_DRINK',
    unitPrice: 0,
    active: true
  })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const getCategoryEmoji = (category) => {
    const emojis = {
      HOT_DRINK: '☕',
      COLD_DRINK: '🥤',
      FAST_FOOD: '🍔',
      DESSERT: '🍰'
    }
    return emojis[category] || '🍽️'
  }

  const formatCategory = (category) => {
    const names = {
      HOT_DRINK: 'Bebida Caliente',
      COLD_DRINK: 'Bebida Fría',
      FAST_FOOD: 'Comida Rápida',
      DESSERT: 'Postre'
    }
    return names[category] || category
  }

  const loadProducts = async () => {
    setLoading(true)
    setError('')
    try {
      const res = await fetch(`${API_BASE}/api/products`)
      const data = await res.json()
      setProducts(data)
    } catch (e) {
      setError('Error cargando productos')
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadProducts()
  }, [])

  const handleChange = e => {
    const { name, value } = e.target
    setForm(prev => ({
      ...prev,
      [name]: name === 'unitPrice' ? Number(value) : value
    }))
  }

  const handleSubmit = async e => {
    e.preventDefault()
    setLoading(true)
    setError('')
    try {
      const res = await fetch(`${API_BASE}/api/products`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(form)
      })
      if (!res.ok) throw new Error()
      setForm({
        name: '',
        category: 'HOT_DRINK',
        unitPrice: 0,
        active: true
      })
      await loadProducts()
    } catch (e) {
      setError('Error creando producto')
    } finally {
      setLoading(false)
    }
  }

  return (
    <section className="section">
      <div className="page-header">
        <h1 className="page-title">🍽️ Gestión de Menú</h1>
        <p className="page-description">Administra los productos de tu cafetería</p>
      </div>

      <div className="card glass-card">
        <div className="card-header">
          <div className="card-icon">➕</div>
          <div>
            <div className="card-title">Nuevo Producto</div>
            <div className="card-subtitle">Agrega un nuevo item al menú</div>
          </div>
        </div>
        <form onSubmit={handleSubmit}>
          <div className="form-grid">
            <label className="label">
              Nombre
              <input
                className="input"
                name="name"
                value={form.name}
                onChange={handleChange}
                required
              />
            </label>
            <label className="label">
              Categoría
              <select
                className="select"
                name="category"
                value={form.category}
                onChange={handleChange}
              >
                <option value="HOT_DRINK">Bebida caliente</option>
                <option value="COLD_DRINK">Bebida fría</option>
                <option value="FAST_FOOD">Comida rápida</option>
                <option value="DESSERT">Postre</option>
              </select>
            </label>
            <label className="label">
              Precio unitario
              <input
                type="number"
                min="0"
                className="input"
                name="unitPrice"
                value={form.unitPrice}
                onChange={handleChange}
                required
              />
            </label>
          </div>
          <div className="button-row">
            <button className="button primary" type="submit" disabled={loading}>
              Guardar producto
            </button>
            <button
              className="button secondary"
              type="button"
              onClick={loadProducts}
              disabled={loading}
            >
              Recargar
            </button>
          </div>
          {error && <div className="error">{error}</div>}
        </form>
      </div>

      <div className="card glass-card">
        <div className="card-header">
          <div className="card-icon">📋</div>
          <div>
            <div className="card-title">Productos Disponibles</div>
            <div className="card-subtitle">{products.length} productos en el menú</div>
          </div>
        </div>
        {loading && products.length === 0 && (
          <div className="empty-state">
            <div className="spinner-large"></div>
            <p>Cargando productos...</p>
          </div>
        )}
        {!loading && products.length === 0 && (
          <div className="empty-state">
            <div className="empty-icon">🍽️</div>
            <p className="empty-text">No hay productos en el menú</p>
            <p className="empty-hint">Crea tu primer producto arriba</p>
          </div>
        )}
        {products.length > 0 && (
          <div className="products-grid">
            {products.map(p => (
              <div key={p.id} className="product-card">
                <div className="product-emoji">{getCategoryEmoji(p.category)}</div>
                <div className="product-info">
                  <h3 className="product-name">{p.name}</h3>
                  <div className="product-category">{formatCategory(p.category)}</div>
                </div>
                <div className="product-price">${p.unitPrice}</div>
                <div className="product-id">ID: {p.id}</div>
              </div>
            ))}
          </div>
        )}
      </div>
    </section>
  )
}

function OrdersSection() {
  const [products, setProducts] = useState([])
  const [orders, setOrders] = useState([])
  const [selectedProductId, setSelectedProductId] = useState('')
  const [quantity, setQuantity] = useState(1)
  const [items, setItems] = useState([])
  const [form, setForm] = useState({
    customerName: '',
    customerId: '',
    paymentMethod: 'ONLINE',
    employeeId: '',
    shift: 'Mañana'
  })
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const loadProducts = async () => {
    try {
      const res = await fetch(`${API_BASE}/api/products`)
      const data = await res.json()
      setProducts(data)
      if (data.length > 0) setSelectedProductId(data[0].id)
    } catch {
      setError('Error cargando productos')
    }
  }

  const loadOrders = async () => {
    try {
      const res = await fetch(`${API_BASE}/api/orders`)
      const data = await res.json()
      setOrders(data)
    } catch {
      setError('Error cargando pedidos')
    }
  }

  useEffect(() => {
    loadProducts()
    loadOrders()
  }, [])

  const handleFormChange = e => {
    const { name, value } = e.target
    setForm(prev => ({ ...prev, [name]: value }))
  }

  const addItem = () => {
    if (!selectedProductId || quantity <= 0) return
    const product = products.find(p => p.id === selectedProductId)
    if (!product) return
    setItems(prev => [
      ...prev,
      {
        productId: product.id,
        productName: product.name,
        quantity,
        unitPrice: product.unitPrice
      }
    ])
    setQuantity(1)
  }

  const removeItem = idx => {
    setItems(prev => prev.filter((_, i) => i !== idx))
  }

  const handleSubmit = async e => {
    e.preventDefault()
    if (items.length === 0) {
      setError('Agrega al menos un producto al pedido')
      return
    }
    setLoading(true)
    setError('')
    try {
      const payload = {
        ...form,
        items
      }
      const res = await fetch(`${API_BASE}/api/orders`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
      })
      if (!res.ok) throw new Error()
      setForm({
        customerName: '',
        customerId: '',
        paymentMethod: 'ONLINE',
        employeeId: '',
        shift: 'Mañana'
      })
      setItems([])
      await loadOrders()
    } catch {
      setError('Error creando pedido')
    } finally {
      setLoading(false)
    }
  }

  const updateStatus = async (orderId, status) => {
    setLoading(true)
    setError('')
    try {
      const res = await fetch(`${API_BASE}/api/orders/${orderId}/status`, {
        method: 'PATCH',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ status })
      })
      if (!res.ok) throw new Error()
      await loadOrders()
    } catch {
      setError('Error actualizando estado')
    } finally {
      setLoading(false)
    }
  }

  const totalPreview = items.reduce(
    (acc, item) => acc + Number(item.unitPrice) * item.quantity,
    0
  )

  return (
    <section className="section">
      <div className="page-header">
        <h1 className="page-title">🛍️ Gestión de Pedidos</h1>
        <p className="page-description">Crea y administra los pedidos de tus clientes</p>
      </div>

      <div className="card glass-card">
        <div className="card-header">
          <div className="card-icon">🆕</div>
          <div>
            <div className="card-title">Nuevo Pedido</div>
            <div className="card-subtitle">Registra un pedido de cliente</div>
          </div>
        </div>

        <form onSubmit={handleSubmit}>
          <div className="form-grid">
            <label className="label">
              Nombre cliente
              <input
                className="input"
                name="customerName"
                value={form.customerName}
                onChange={handleFormChange}
                required
              />
            </label>
            <label className="label">
              ID cliente
              <input
                className="input"
                name="customerId"
                value={form.customerId}
                onChange={handleFormChange}
              />
            </label>
            <label className="label">
              Método de pago
              <select
                className="select"
                name="paymentMethod"
                value={form.paymentMethod}
                onChange={handleFormChange}
              >
                <option value="ONLINE">Online</option>
                <option value="CARD">Tarjeta</option>
                <option value="CASH">Efectivo</option>
              </select>
            </label>
            <label className="label">
              Empleado
              <input
                className="input"
                name="employeeId"
                value={form.employeeId}
                onChange={handleFormChange}
              />
            </label>
            <label className="label">
              Turno
              <select
                className="select"
                name="shift"
                value={form.shift}
                onChange={handleFormChange}
              >
                <option>Mañana</option>
                <option>Tarde</option>
                <option>Noche</option>
              </select>
            </label>
          </div>

          <div className="card-subtitle" style={{ marginTop: 12 }}>
            Productos del pedido
          </div>

          <div className="form-grid">
            <label className="label">
              Producto
              <select
                className="select"
                value={selectedProductId}
                onChange={e => setSelectedProductId(e.target.value)}
              >
                {products.map(p => (
                  <option key={p.id} value={p.id}>
                    {p.name} (${p.unitPrice})
                  </option>
                ))}
              </select>
            </label>
            <label className="label">
              Cantidad
              <input
                type="number"
                min="1"
                className="input"
                value={quantity}
                onChange={e => setQuantity(Number(e.target.value))}
              />
            </label>
            <div className="label">
              &nbsp;
              <button
                type="button"
                className="button secondary"
                onClick={addItem}
                disabled={products.length === 0}
              >
                Añadir al pedido
              </button>
            </div>
          </div>

          {items.length > 0 && (
            <table className="table">
              <thead>
                <tr>
                  <th>Producto</th>
                  <th>Cant.</th>
                  <th>Precio</th>
                  <th>Subtotal</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {items.map((item, idx) => (
                  <tr key={idx}>
                    <td>{item.productName}</td>
                    <td>{item.quantity}</td>
                    <td>${item.unitPrice}</td>
                    <td>${Number(item.unitPrice) * item.quantity}</td>
                    <td>
                      <button
                        type="button"
                        className="button small secondary"
                        onClick={() => removeItem(idx)}
                      >
                        Quitar
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}

          <div className="helper">
            Total estimado: <strong>${totalPreview}</strong>
          </div>

          <div className="button-row">
            <button className="button primary" type="submit" disabled={loading}>
              Crear pedido
            </button>
            <button
              className="button secondary"
              type="button"
              onClick={loadOrders}
              disabled={loading}
            >
              Recargar pedidos
            </button>
          </div>
          {error && <div className="error">{error}</div>}
        </form>
      </div>

      <div className="card glass-card">
        <div className="card-header">
          <div className="card-icon">📦</div>
          <div>
            <div className="card-title">Pedidos Activos</div>
            <div className="card-subtitle">{orders.length} pedidos registrados</div>
          </div>
        </div>
        {orders.length === 0 && (
          <div className="empty-state">
            <div className="empty-icon">📝</div>
            <p className="empty-text">No hay pedidos registrados</p>
            <p className="empty-hint">Crea tu primer pedido arriba</p>
          </div>
        )}
        {orders.map(order => (
          <div key={order.id} className="order-card-detail" style={{ marginBottom: 16 }}>
            <div className="card-title">
              {order.orderNumber || '(sin número)'}{' '}
              <span className={`tag status-${order.status}`}>{order.status}</span>
            </div>
            <div className="card-subtitle">
              Cliente: {order.customerName} • Total: ${order.totalAmount} • Estimado:{' '}
              {order.estimatedTimeMinutes} min
            </div>
            <div className="chip-row">
              <span className="chip">Pago: {order.paymentMethod}</span>
              {order.shift && <span className="chip">Turno: {order.shift}</span>}
              {order.promotionDescription && (
                <span className="chip">Promo: {order.promotionDescription}</span>
              )}
            </div>
            <table className="table">
              <thead>
                <tr>
                  <th>Producto</th>
                  <th>Cant.</th>
                  <th>Subtotal</th>
                </tr>
              </thead>
              <tbody>
                {order.items?.map((it, idx) => (
                  <tr key={idx}>
                    <td>{it.productName}</td>
                    <td>{it.quantity}</td>
                    <td>${it.subtotal}</td>
                  </tr>
                ))}
              </tbody>
            </table>
            <div className="button-row">
              <button
                className="button small secondary"
                onClick={() => updateStatus(order.id, 'PENDING')}
              >
                Pendiente
              </button>
              <button
                className="button small secondary"
                onClick={() => updateStatus(order.id, 'PREPARING')}
              >
                Preparando
              </button>
              <button
                className="button small secondary"
                onClick={() => updateStatus(order.id, 'READY')}
              >
                Listo
              </button>
              <button
                className="button small secondary"
                onClick={() => updateStatus(order.id, 'DELIVERED')}
              >
                Entregado
              </button>
            </div>
          </div>
        ))}
      </div>
    </section>
  )
}

function ReportsSection() {
  const [date, setDate] = useState(() => new Date().toISOString().slice(0, 10))
  const [report, setReport] = useState(null)
  const [orders, setOrders] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [stats, setStats] = useState(null)

  const loadReport = async () => {
    setLoading(true)
    setError('')
    try {
      const res = await fetch(`${API_BASE}/api/reports/daily?date=${date}`)
      if (!res.ok) throw new Error()
      const data = await res.json()
      setReport(data)
      
      // Cargar pedidos del día para análisis detallado
      const ordersRes = await fetch(`${API_BASE}/api/orders`)
      const ordersData = await ordersRes.json()
      
      // Filtrar pedidos por fecha
      const dateOrders = ordersData.filter(order => {
        const orderDate = new Date(order.createdAt || order.timestamp)
        return orderDate.toISOString().slice(0, 10) === date
      })
      
      setOrders(dateOrders)
      calculateStats(dateOrders, data)
    } catch {
      setError('Error cargando reporte')
    } finally {
      setLoading(false)
    }
  }

  const calculateStats = (ordersData, reportData) => {
    if (!ordersData || ordersData.length === 0) {
      setStats(null)
      return
    }

    // Estadísticas por método de pago
    const paymentMethods = ordersData.reduce((acc, order) => {
      const method = order.paymentMethod || 'UNKNOWN'
      if (!acc[method]) acc[method] = { count: 0, total: 0 }
      acc[method].count++
      acc[method].total += Number(order.totalAmount || 0)
      return acc
    }, {})

    // Estadísticas por estado
    const statuses = ordersData.reduce((acc, order) => {
      const status = order.status || 'UNKNOWN'
      if (!acc[status]) acc[status] = 0
      acc[status]++
      return acc
    }, {})

    // Estadísticas por turno
    const shifts = ordersData.reduce((acc, order) => {
      const shift = order.shift || 'No especificado'
      if (!acc[shift]) acc[shift] = { count: 0, total: 0 }
      acc[shift].count++
      acc[shift].total += Number(order.totalAmount || 0)
      return acc
    }, {})

    // Productos más vendidos
    const productSales = {}
    ordersData.forEach(order => {
      order.items?.forEach(item => {
        const name = item.productName || 'Producto'
        if (!productSales[name]) {
          productSales[name] = { quantity: 0, revenue: 0 }
        }
        productSales[name].quantity += item.quantity || 0
        productSales[name].revenue += Number(item.subtotal || 0)
      })
    })

    const topProducts = Object.entries(productSales)
      .map(([name, data]) => ({ name, ...data }))
      .sort((a, b) => b.revenue - a.revenue)
      .slice(0, 5)

    // Ticket promedio
    const avgTicket = ordersData.length > 0 
      ? (reportData?.totalIncome || 0) / ordersData.length 
      : 0

    // Tiempo promedio de preparación
    const avgTime = ordersData.reduce((acc, order) => 
      acc + (order.estimatedTimeMinutes || 0), 0) / ordersData.length || 0

    setStats({
      paymentMethods,
      statuses,
      shifts,
      topProducts,
      avgTicket,
      avgTime
    })
  }

  useEffect(() => {
    loadReport()
  }, [])

  const getStatusColor = (status) => {
    const colors = {
      PENDING: '#f59e0b',
      PREPARING: '#3b82f6',
      READY: '#10b981',
      DELIVERED: '#6366f1',
      CANCELLED: '#ef4444'
    }
    return colors[status] || '#94a3b8'
  }

  const getPaymentIcon = (method) => {
    const icons = {
      CASH: '💵',
      CARD: '💳',
      ONLINE: '📱'
    }
    return icons[method] || '💰'
  }

  return (
    <section className="section">
      <div className="page-header">
        <h1 className="page-title">📊 Reportes y Analíticas</h1>
        <p className="page-description">Análisis detallado del rendimiento diario</p>
      </div>

      <div className="card glass-card">
        <div className="card-header">
          <div className="card-icon">📅</div>
          <div>
            <div className="card-title">Reporte Diario</div>
            <div className="card-subtitle">Visualiza las métricas de un día específico</div>
          </div>
        </div>

        <div className="form-grid">
          <label className="label">
            📅 Seleccionar Fecha
            <input
              type="date"
              className="input"
              value={date}
              onChange={e => setDate(e.target.value)}
            />
          </label>
        </div>
        <div className="button-row">
          <button className="button primary" onClick={loadReport} disabled={loading}>
            {loading ? 'Cargando...' : '🔍 Generar Reporte'}
          </button>
        </div>
        {error && <div className="error">{error}</div>}
      </div>

      {report && (
        <>
          {/* Resumen General */}
          <div className="stats-grid">
            <div className="stat-card">
              <div className="stat-icon">🛒</div>
              <div className="stat-value">{report.totalOrders || 0}</div>
              <div className="stat-label">Total Pedidos</div>
            </div>
            <div className="stat-card">
              <div className="stat-icon">💰</div>
              <div className="stat-value">${report.totalIncome?.toFixed(2) || '0.00'}</div>
              <div className="stat-label">Ingresos Totales</div>
            </div>
            <div className="stat-card">
              <div className="stat-icon">📈</div>
              <div className="stat-value">${stats?.avgTicket?.toFixed(2) || '0.00'}</div>
              <div className="stat-label">Ticket Promedio</div>
            </div>
            <div className="stat-card">
              <div className="stat-icon">⏱️</div>
              <div className="stat-value">{stats?.avgTime?.toFixed(0) || '0'} min</div>
              <div className="stat-label">Tiempo Promedio</div>
            </div>
          </div>

          {stats && (
            <>
              {/* Productos más vendidos */}
              {stats.topProducts.length > 0 && (
                <div className="card">
                  <div className="card-title">🏆 Top 5 Productos Más Vendidos</div>
                  <div className="products-chart">
                    {stats.topProducts.map((product, idx) => {
                      const maxRevenue = stats.topProducts[0].revenue
                      const percentage = (product.revenue / maxRevenue) * 100
                      return (
                        <div key={idx} className="product-bar-item">
                          <div className="product-bar-header">
                            <span className="product-rank">#{idx + 1}</span>
                            <span className="product-name">{product.name}</span>
                            <span className="product-stats">
                              {product.quantity} unidades • ${product.revenue.toFixed(2)}
                            </span>
                          </div>
                          <div className="product-bar-container">
                            <div 
                              className="product-bar-fill" 
                              style={{ width: `${percentage}%` }}
                            />
                          </div>
                        </div>
                      )
                    })}
                  </div>
                </div>
              )}

              {/* Métodos de Pago */}
              <div className="card">
                <div className="card-title">💳 Distribución por Método de Pago</div>
                <div className="payment-grid">
                  {Object.entries(stats.paymentMethods).map(([method, data]) => (
                    <div key={method} className="payment-card">
                      <div className="payment-icon">{getPaymentIcon(method)}</div>
                      <div className="payment-method">{method}</div>
                      <div className="payment-count">{data.count} pedidos</div>
                      <div className="payment-total">${data.total.toFixed(2)}</div>
                    </div>
                  ))}
                </div>
              </div>

              {/* Estados de Pedidos */}
              <div className="card">
                <div className="card-title">📦 Estado de Pedidos</div>
                <div className="status-grid">
                  {Object.entries(stats.statuses).map(([status, count]) => (
                    <div key={status} className="status-item">
                      <div 
                        className="status-indicator" 
                        style={{ backgroundColor: getStatusColor(status) }}
                      />
                      <div className="status-info">
                        <div className="status-name">{status}</div>
                        <div className="status-count">{count} pedidos</div>
                      </div>
                    </div>
                  ))}
                </div>
              </div>

              {/* Turnos */}
              {Object.keys(stats.shifts).length > 0 && (
                <div className="card">
                  <div className="card-title">🕐 Análisis por Turno</div>
                  <table className="table">
                    <thead>
                      <tr>
                        <th>Turno</th>
                        <th>Pedidos</th>
                        <th>Ingresos</th>
                        <th>Promedio</th>
                      </tr>
                    </thead>
                    <tbody>
                      {Object.entries(stats.shifts).map(([shift, data]) => (
                        <tr key={shift}>
                          <td><strong>{shift}</strong></td>
                          <td>{data.count}</td>
                          <td>${data.total.toFixed(2)}</td>
                          <td>${(data.total / data.count).toFixed(2)}</td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}

              {/* Lista de pedidos del día */}
              {orders.length > 0 && (
                <div className="card">
                  <div className="card-title">📋 Detalle de Pedidos ({orders.length})</div>
                  <div className="orders-list">
                    {orders.map((order, idx) => (
                      <div key={order.id} className="order-item">
                        <div className="order-header">
                          <span className="order-number">#{idx + 1} - {order.orderNumber}</span>
                          <span className={`order-status status-${order.status}`}>
                            {order.status}
                          </span>
                        </div>
                        <div className="order-details">
                          <span>👤 {order.customerName}</span>
                          <span>{getPaymentIcon(order.paymentMethod)} {order.paymentMethod}</span>
                          <span>💵 ${order.totalAmount}</span>
                          <span>⏱️ {order.estimatedTimeMinutes} min</span>
                        </div>
                        {order.items && order.items.length > 0 && (
                          <div className="order-items">
                            {order.items.map((item, itemIdx) => (
                              <span key={itemIdx} className="order-item-chip">
                                {item.quantity}x {item.productName}
                              </span>
                            ))}
                          </div>
                        )}
                      </div>
                    ))}
                  </div>
                </div>
              )}
            </>
          )}

          {(!stats || orders.length === 0) && (
            <div className="card">
              <div className="helper">📭 No hay pedidos registrados para esta fecha</div>
            </div>
          )}
        </>
      )}

      {!report && !loading && (
        <div className="card">
          <div className="helper">👆 Selecciona una fecha y haz clic en "Generar Reporte"</div>
        </div>
      )}
    </section>
  )
}

export default App
