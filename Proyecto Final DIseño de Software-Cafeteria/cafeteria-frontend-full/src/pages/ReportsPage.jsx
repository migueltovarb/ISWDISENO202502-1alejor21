import { useState, useEffect } from 'react'
import { reportsAPI, ordersAPI, handleAPIError } from '../services/api'
import { LoadingSpinner } from '../components/LoadingSpinner'
import { ErrorMessage } from '../components/ErrorMessage'

const ORDER_STATUSES = {
  PENDING: { label: 'Pendiente', color: '#f59e0b' },
  PREPARING: { label: 'Preparando', color: '#3b82f6' },
  READY: { label: 'Listo', color: '#10b981' },
  DELIVERED: { label: 'Entregado', color: '#6366f1' },
  CANCELLED: { label: 'Cancelado', color: '#ef4444' }
}

const PAYMENT_METHODS = {
  CASH: { label: 'Efectivo', icon: '💵' },
  CARD: { label: 'Tarjeta', icon: '💳' },
  ONLINE: { label: 'En Línea', icon: '📱' }
}

export const ReportsPage = () => {
  const [view, setView] = useState('daily') // 'daily' o 'history'
  
  // Estado para reporte diario
  const [selectedDate, setSelectedDate] = useState(() => {
    return new Date().toISOString().split('T')[0]
  })
  const [dailyReport, setDailyReport] = useState(null)
  const [dailyOrders, setDailyOrders] = useState([])
  const [dailyStats, setDailyStats] = useState(null)

  // Estado para historial
  const [allOrders, setAllOrders] = useState([])
  const [filteredOrders, setFilteredOrders] = useState([])
  const [startDate, setStartDate] = useState('')
  const [endDate, setEndDate] = useState('')
  const [statusFilter, setStatusFilter] = useState('ALL')

  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    if (view === 'daily') {
      loadDailyReport()
    } else {
      loadOrderHistory()
    }
  }, [view, selectedDate])

  useEffect(() => {
    if (view === 'history') {
      filterOrders()
    }
  }, [allOrders, startDate, endDate, statusFilter])

  const loadDailyReport = async () => {
    setLoading(true)
    setError('')
    try {
      // Cargar reporte diario
      const reportResponse = await reportsAPI.getDailyReport(selectedDate)
      setDailyReport(reportResponse.data)

      // Cargar todos los pedidos para filtrar por fecha
      const ordersResponse = await ordersAPI.getAll()
      const ordersOfDay = ordersResponse.data.filter(order => {
        const orderDate = new Date(order.createdAt || order.timestamp)
        return orderDate.toISOString().split('T')[0] === selectedDate
      })

      setDailyOrders(ordersOfDay)
      calculateDailyStats(ordersOfDay, reportResponse.data)
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const calculateDailyStats = (orders, report) => {
    if (!orders || orders.length === 0) {
      setDailyStats(null)
      return
    }

    // Estadísticas por método de pago
    const paymentMethods = orders.reduce((acc, order) => {
      const method = order.paymentMethod || 'UNKNOWN'
      if (!acc[method]) acc[method] = { count: 0, total: 0 }
      acc[method].count++
      acc[method].total += Number(order.totalAmount || 0)
      return acc
    }, {})

    // Estadísticas por estado
    const statuses = orders.reduce((acc, order) => {
      const status = order.status || 'UNKNOWN'
      if (!acc[status]) acc[status] = 0
      acc[status]++
      return acc
    }, {})

    // Productos más vendidos
    const productSales = {}
    orders.forEach(order => {
      order.items?.forEach(item => {
        const name = item.productName || 'Producto'
        if (!productSales[name]) {
          productSales[name] = { quantity: 0, revenue: 0 }
        }
        productSales[name].quantity += item.quantity || 0
        productSales[name].revenue += Number(item.unitPrice || 0) * (item.quantity || 0)
      })
    })

    const topProducts = Object.entries(productSales)
      .map(([name, data]) => ({ name, ...data }))
      .sort((a, b) => b.revenue - a.revenue)
      .slice(0, 5)

    // Ticket promedio
    const avgTicket = orders.length > 0 ? (report?.totalIncome || 0) / orders.length : 0

    // Tiempo promedio
    const avgTime = orders.reduce((acc, order) => 
      acc + (order.estimatedTimeMinutes || 0), 0) / orders.length || 0

    setDailyStats({
      paymentMethods,
      statuses,
      topProducts,
      avgTicket,
      avgTime
    })
  }

  const loadOrderHistory = async () => {
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
      setAllOrders(sortedOrders)
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const filterOrders = () => {
    let filtered = allOrders

    // Filtrar por rango de fechas
    if (startDate) {
      filtered = filtered.filter(order => {
        const orderDate = new Date(order.createdAt || order.timestamp)
        return orderDate >= new Date(startDate)
      })
    }

    if (endDate) {
      filtered = filtered.filter(order => {
        const orderDate = new Date(order.createdAt || order.timestamp)
        return orderDate <= new Date(endDate + 'T23:59:59')
      })
    }

    // Filtrar por estado
    if (statusFilter !== 'ALL') {
      filtered = filtered.filter(order => order.status === statusFilter)
    }

    setFilteredOrders(filtered)
  }

  return (
    <section className="section">
      <div className="page-header">
        <h1 className="page-title">📊 Reportes y Analíticas</h1>
        <p className="page-description">Análisis detallado del rendimiento del negocio</p>
      </div>

      {/* Toggle de vistas */}
      <div className="view-toggle">
        <button
          className={`button ${view === 'daily' ? 'primary' : 'secondary'}`}
          onClick={() => setView('daily')}
        >
          📅 Reporte Diario
        </button>
        <button
          className={`button ${view === 'history' ? 'primary' : 'secondary'}`}
          onClick={() => setView('history')}
        >
          📜 Historial de Pedidos
        </button>
      </div>

      {error && <ErrorMessage message={error} onClose={() => setError('')} />}

      {/* Vista de Reporte Diario */}
      {view === 'daily' && (
        <>
          <div className="card glass-card">
            <div className="card-header">
              <div className="card-icon">📅</div>
              <div>
                <div className="card-title">Reporte Diario de Ventas</div>
                <div className="card-subtitle">Selecciona una fecha para ver el reporte</div>
              </div>
            </div>

            <div className="date-selector">
              <label className="label">
                📅 Fecha:
                <input
                  type="date"
                  className="input"
                  value={selectedDate}
                  onChange={(e) => setSelectedDate(e.target.value)}
                  max={new Date().toISOString().split('T')[0]}
                />
              </label>
              <button className="button primary" onClick={loadDailyReport} disabled={loading}>
                🔍 Generar Reporte
              </button>
            </div>
          </div>

          {loading && <LoadingSpinner />}

          {dailyReport && !loading && (
            <>
              {/* Tarjetas de resumen */}
              <div className="stats-grid">
                <div className="stat-card">
                  <div className="stat-icon">🛒</div>
                  <div className="stat-value">{dailyReport.totalOrders || 0}</div>
                  <div className="stat-label">Total Pedidos</div>
                </div>
                <div className="stat-card">
                  <div className="stat-icon">💰</div>
                  <div className="stat-value">${(dailyReport.totalIncome || 0).toFixed(2)}</div>
                  <div className="stat-label">Ingresos Totales</div>
                </div>
                <div className="stat-card">
                  <div className="stat-icon">📈</div>
                  <div className="stat-value">${(dailyStats?.avgTicket || 0).toFixed(2)}</div>
                  <div className="stat-label">Ticket Promedio</div>
                </div>
                <div className="stat-card">
                  <div className="stat-icon">⏱️</div>
                  <div className="stat-value">{Math.round(dailyStats?.avgTime || 0)} min</div>
                  <div className="stat-label">Tiempo Promedio</div>
                </div>
              </div>

              {dailyStats && dailyOrders.length > 0 && (
                <>
                  {/* Top productos */}
                  {dailyStats.topProducts.length > 0 && (
                    <div className="card glass-card">
                      <div className="card-title">🏆 Top 5 Productos Más Vendidos</div>
                      <div className="products-chart">
                        {dailyStats.topProducts.map((product, idx) => {
                          const maxRevenue = dailyStats.topProducts[0].revenue
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

                  {/* Métodos de pago */}
                  <div className="card glass-card">
                    <div className="card-title">💳 Distribución por Método de Pago</div>
                    <div className="payment-grid">
                      {Object.entries(dailyStats.paymentMethods).map(([method, data]) => {
                        const methodData = PAYMENT_METHODS[method] || { icon: '💰', label: method }
                        return (
                          <div key={method} className="payment-card">
                            <div className="payment-icon">{methodData.icon}</div>
                            <div className="payment-method">{methodData.label}</div>
                            <div className="payment-count">{data.count} pedidos</div>
                            <div className="payment-total">${data.total.toFixed(2)}</div>
                          </div>
                        )
                      })}
                    </div>
                  </div>

                  {/* Estados */}
                  <div className="card glass-card">
                    <div className="card-title">📦 Distribución por Estado</div>
                    <div className="status-grid">
                      {Object.entries(dailyStats.statuses).map(([status, count]) => {
                        const statusData = ORDER_STATUSES[status] || { label: status, color: '#94a3b8' }
                        return (
                          <div key={status} className="status-item">
                            <div
                              className="status-indicator"
                              style={{ backgroundColor: statusData.color }}
                            />
                            <div className="status-info">
                              <div className="status-name">{statusData.label}</div>
                              <div className="status-count">{count} pedidos</div>
                            </div>
                          </div>
                        )
                      })}
                    </div>
                  </div>
                </>
              )}

              {(!dailyStats || dailyOrders.length === 0) && (
                <div className="card glass-card">
                  <div className="empty-state">
                    <div className="empty-icon">📭</div>
                    <p className="empty-text">No hay pedidos registrados para esta fecha</p>
                  </div>
                </div>
              )}
            </>
          )}
        </>
      )}

      {/* Vista de Historial */}
      {view === 'history' && (
        <>
          <div className="card glass-card">
            <div className="card-header">
              <div className="card-icon">📜</div>
              <div>
                <div className="card-title">Historial de Pedidos</div>
                <div className="card-subtitle">
                  {filteredOrders.length} pedidos{' '}
                  {startDate || endDate || statusFilter !== 'ALL' ? 'filtrados' : 'totales'}
                </div>
              </div>
              <button className="button primary" onClick={loadOrderHistory} disabled={loading}>
                🔄 Recargar
              </button>
            </div>

            {/* Filtros */}
            <div className="filters-section">
              <div className="form-grid">
                <label className="label">
                  📅 Desde:
                  <input
                    type="date"
                    className="input"
                    value={startDate}
                    onChange={(e) => setStartDate(e.target.value)}
                  />
                </label>

                <label className="label">
                  📅 Hasta:
                  <input
                    type="date"
                    className="input"
                    value={endDate}
                    onChange={(e) => setEndDate(e.target.value)}
                  />
                </label>

                <label className="label">
                  📦 Estado:
                  <select
                    className="select"
                    value={statusFilter}
                    onChange={(e) => setStatusFilter(e.target.value)}
                  >
                    <option value="ALL">Todos</option>
                    {Object.entries(ORDER_STATUSES).map(([value, data]) => (
                      <option key={value} value={value}>
                        {data.label}
                      </option>
                    ))}
                  </select>
                </label>

                <div className="label">
                  &nbsp;
                  <button
                    className="button secondary"
                    onClick={() => {
                      setStartDate('')
                      setEndDate('')
                      setStatusFilter('ALL')
                    }}
                  >
                    🔄 Limpiar Filtros
                  </button>
                </div>
              </div>
            </div>
          </div>

          {loading && <LoadingSpinner />}

          {!loading && filteredOrders.length === 0 && (
            <div className="card glass-card">
              <div className="empty-state">
                <div className="empty-icon">📭</div>
                <p className="empty-text">No se encontraron pedidos con estos filtros</p>
              </div>
            </div>
          )}

          {!loading && filteredOrders.length > 0 && (
            <div className="card glass-card">
              <div className="table-container">
                <table className="table">
                  <thead>
                    <tr>
                      <th>Nº Pedido</th>
                      <th>Cliente</th>
                      <th>Total</th>
                      <th>Pago</th>
                      <th>Estado</th>
                      <th>Fecha</th>
                      <th>Cambios de Estado</th>
                    </tr>
                  </thead>
                  <tbody>
                    {filteredOrders.map(order => {
                      const statusData = ORDER_STATUSES[order.status] || ORDER_STATUSES.PENDING
                      const paymentData = PAYMENT_METHODS[order.paymentMethod] || PAYMENT_METHODS.CASH
                      
                      return (
                        <tr key={order.id}>
                          <td>
                            <strong>#{order.orderNumber || order.id?.substring(0, 8)}</strong>
                          </td>
                          <td>{order.customerName}</td>
                          <td>${(order.totalAmount || 0).toFixed(2)}</td>
                          <td>
                            {paymentData.icon} {paymentData.label}
                          </td>
                          <td>
                            <span
                              className="status-badge"
                              style={{ backgroundColor: statusData.color }}
                            >
                              {statusData.label}
                            </span>
                          </td>
                          <td>
                            {order.createdAt
                              ? new Date(order.createdAt).toLocaleString('es-ES')
                              : 'N/A'}
                          </td>
                          <td>
                            {order.statusHistory && order.statusHistory.length > 0 ? (
                              <div className="status-history-mini">
                                {order.statusHistory.map((history, idx) => (
                                  <div key={idx} className="history-mini-item">
                                    <span className="history-mini-status">
                                      {ORDER_STATUSES[history.status]?.label || history.status}
                                    </span>
                                    <span className="history-mini-time">
                                      {history.timestamp
                                        ? new Date(history.timestamp).toLocaleTimeString('es-ES')
                                        : ''}
                                    </span>
                                  </div>
                                ))}
                              </div>
                            ) : (
                              <span className="text-muted">Sin historial</span>
                            )}
                          </td>
                        </tr>
                      )
                    })}
                  </tbody>
                </table>
              </div>
            </div>
          )}
        </>
      )}
    </section>
  )
}
