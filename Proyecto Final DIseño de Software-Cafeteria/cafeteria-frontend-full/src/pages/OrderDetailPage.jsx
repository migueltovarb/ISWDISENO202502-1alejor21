import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { ordersAPI, handleAPIError } from '../services/api'
import { LoadingSpinner } from '../components/LoadingSpinner'
import { ErrorMessage } from '../components/ErrorMessage'

const ORDER_STATUSES = {
  PENDING: { label: 'Pendiente', color: '#f59e0b', emoji: '⏳' },
  PREPARING: { label: 'Preparando', color: '#3b82f6', emoji: '👨‍🍳' },
  READY: { label: 'Listo', color: '#10b981', emoji: '✅' },
  DELIVERED: { label: 'Entregado', color: '#6366f1', emoji: '🎉' }
}

const PAYMENT_METHODS = {
  CASH: { label: 'Efectivo', icon: '💵' },
  CARD: { label: 'Tarjeta', icon: '💳' },
  ONLINE: { label: 'En Línea', icon: '📱' }
}

export const OrderDetailPage = () => {
  const { id } = useParams()
  const navigate = useNavigate()
  const [order, setOrder] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    loadOrder()
  }, [id])

  const loadOrder = async () => {
    setLoading(true)
    setError('')
    try {
      const response = await ordersAPI.getById(id)
      setOrder(response.data)
    } catch (err) {
      setError(handleAPIError(err))
    } finally {
      setLoading(false)
    }
  }

  const handlePrint = () => {
    window.print()
  }

  if (loading) {
    return (
      <section className="section">
        <LoadingSpinner size="large" />
      </section>
    )
  }

  if (error) {
    return (
      <section className="section">
        <ErrorMessage message={error} />
        <button className="button secondary" onClick={() => navigate('/orders')}>
          ← Volver a Pedidos
        </button>
      </section>
    )
  }

  if (!order) {
    return (
      <section className="section">
        <div className="empty-state">
          <p>Pedido no encontrado</p>
        </div>
      </section>
    )
  }

  const statusData = ORDER_STATUSES[order.status] || ORDER_STATUSES.PENDING
  const paymentData = PAYMENT_METHODS[order.paymentMethod] || PAYMENT_METHODS.CASH

  return (
    <section className="section">
      {/* Botones de acción (no se imprimen) */}
      <div className="page-actions no-print">
        <button className="button secondary" onClick={() => navigate('/orders')}>
          ← Volver a Pedidos
        </button>
        <button className="button primary" onClick={handlePrint}>
          🖨️ Imprimir Comprobante
        </button>
      </div>

      {/* Comprobante */}
      <div className="receipt-container">
        <div className="receipt">
          {/* Encabezado */}
          <div className="receipt-header">
            <div className="receipt-logo">☕</div>
            <h1 className="receipt-title">Cafetería</h1>
            <p className="receipt-subtitle">Sistema de Gestión de Pedidos</p>
            <div className="receipt-divider"></div>
          </div>

          {/* Información del pedido */}
          <div className="receipt-section">
            <h2 className="receipt-section-title">Comprobante de Pedido</h2>
            <div className="receipt-info-grid">
              <div className="receipt-info-row">
                <span className="info-label">Número de pedido:</span>
                <span className="info-value">
                  #{order.orderNumber || order.id?.substring(0, 8)}
                </span>
              </div>
              <div className="receipt-info-row">
                <span className="info-label">Estado:</span>
                <span className="info-value">
                  <span
                    className="receipt-status"
                    style={{ backgroundColor: statusData.color }}
                  >
                    {statusData.emoji} {statusData.label}
                  </span>
                </span>
              </div>
              <div className="receipt-info-row">
                <span className="info-label">Fecha y hora:</span>
                <span className="info-value">
                  {order.createdAt
                    ? new Date(order.createdAt).toLocaleString('es-ES', {
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric',
                        hour: '2-digit',
                        minute: '2-digit'
                      })
                    : 'N/A'}
                </span>
              </div>
              {order.estimatedTimeMinutes && (
                <div className="receipt-info-row">
                  <span className="info-label">⏱️ Tiempo estimado:</span>
                  <span className="info-value">{order.estimatedTimeMinutes} minutos</span>
                </div>
              )}
              {order.paymentMethod && (
                <div className="receipt-info-row">
                  <span className="info-label">Método de pago:</span>
                  <span className="info-value">{order.paymentMethod}</span>
                </div>
              )}
            </div>
          </div>

          <div className="receipt-divider"></div>

          {/* Información del cliente */}
          <div className="receipt-section">
            <h3 className="receipt-section-title">Cliente</h3>
            <div className="receipt-info-grid">
              <div className="receipt-info-row">
                <span className="info-label">Nombre:</span>
                <span className="info-value">{order.customerName}</span>
              </div>
              {order.customerId && (
                <div className="receipt-info-row">
                  <span className="info-label">Identificación:</span>
                  <span className="info-value">{order.customerId}</span>
                </div>
              )}
            </div>
          </div>

          <div className="receipt-divider"></div>

          {/* Productos */}
          <div className="receipt-section">
            <h3 className="receipt-section-title">Productos</h3>
            <table className="receipt-table">
              <thead>
                <tr>
                  <th>Producto</th>
                  <th className="text-center">Cant.</th>
                  <th className="text-right">Precio</th>
                  <th className="text-right">Subtotal</th>
                </tr>
              </thead>
              <tbody>
                {order.items?.map((item, index) => (
                  <tr key={index}>
                    <td>{item.productName || 'Producto'}</td>
                    <td className="text-center">{item.quantity}</td>
                    <td className="text-right">${item.unitPrice?.toFixed(2) || '0.00'}</td>
                    <td className="text-right">
                      ${(item.quantity * (item.unitPrice || 0)).toFixed(2)}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          <div className="receipt-divider"></div>

          {/* Totales */}
          <div className="receipt-section">
            <div className="receipt-totals">
              <div className="total-row">
                <span className="total-label">Subtotal:</span>
                <span className="total-value">${order.totalAmount?.toFixed(2) || '0.00'}</span>
              </div>
              <div className="total-row final-total">
                <span className="total-label">TOTAL:</span>
                <span className="total-value">${order.totalAmount?.toFixed(2) || '0.00'}</span>
              </div>
            </div>
          </div>

          <div className="receipt-divider"></div>

          {/* Información de pago */}
          <div className="receipt-section">
            <h3 className="receipt-section-title">Información de Pago</h3>
            <div className="receipt-info-grid">
              <div className="receipt-info-row">
                <span className="info-label">Método de pago:</span>
                <span className="info-value">
                  {paymentData.icon} {paymentData.label}
                </span>
              </div>
              {order.estimatedTimeMinutes && (
                <div className="receipt-info-row">
                  <span className="info-label">Tiempo estimado:</span>
                  <span className="info-value">{order.estimatedTimeMinutes} minutos</span>
                </div>
              )}
            </div>
          </div>

          {/* Historial de estados (si existe) */}
          {order.statusHistory && order.statusHistory.length > 0 && (
            <>
              <div className="receipt-divider"></div>
              <div className="receipt-section">
                <h3 className="receipt-section-title">Historial de Estados</h3>
                <div className="status-history">
                  {order.statusHistory.map((history, index) => (
                    <div key={index} className="history-item">
                      <div className="history-status">
                        {ORDER_STATUSES[history.status]?.emoji || '•'}{' '}
                        {ORDER_STATUSES[history.status]?.label || history.status}
                      </div>
                      <div className="history-timestamp">
                        {history.timestamp
                          ? new Date(history.timestamp).toLocaleString('es-ES')
                          : 'N/A'}
                      </div>
                      {history.changedBy && (
                        <div className="history-user">Por: {history.changedBy}</div>
                      )}
                    </div>
                  ))}
                </div>
              </div>
            </>
          )}

          {/* Pie de página */}
          <div className="receipt-footer">
            <p className="receipt-thank-you">¡Gracias por su preferencia!</p>
            <p className="receipt-footer-text">
              Este documento es un comprobante de su pedido.
            </p>
            {order.status === 'READY' && (
              <div className="receipt-ready-notice">
                <strong>✅ Su pedido está listo para recoger</strong>
              </div>
            )}
            {order.status === 'DELIVERED' && (
              <div className="receipt-delivered-notice">
                <strong>✔️ Pedido entregado</strong>
              </div>
            )}
          </div>
        </div>
      </div>
    </section>
  )
}
