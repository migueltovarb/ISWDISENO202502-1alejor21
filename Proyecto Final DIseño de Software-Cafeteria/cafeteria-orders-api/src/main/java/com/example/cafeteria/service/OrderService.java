package com.example.cafeteria.service;

import com.example.cafeteria.domain.Order;
import com.example.cafeteria.domain.OrderItem;
import com.example.cafeteria.domain.OrderStatus;
import com.example.cafeteria.domain.OrderStatusChange;
import com.example.cafeteria.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    // Flujo permitido de estados
    private static final List<OrderStatus> ORDER_FLOW = Arrays.asList(
            OrderStatus.PENDING,
            OrderStatus.PREPARING,
            OrderStatus.READY,
            OrderStatus.DELIVERED
    );

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
    }

    // 🔎 HU007: buscar por estado
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    // 🔎 HU007: buscar por rango de fechas
    public List<Order> findByDateRange(Instant start, Instant end) {
        return orderRepository.findByCreatedAtBetween(start, end);
    }

    // Crear pedido (HU003, HU004, HU006, HU005 inicio)
    public Order create(Order order) {
        order.setId(null);
        Instant now = Instant.now();
        order.setCreatedAt(now);
        order.setUpdatedAt(now);
        order.setStatus(OrderStatus.PENDING);

        // Generar número de pedido único
        order.setOrderNumber(generateOrderNumber());

        // Calcular y asignar turno automáticamente
        order.setShift(calculateShift(now));

        if (order.getItems() == null) {
            order.setItems(new ArrayList<>());
        }

        // Total con BigDecimal (unitPrice es BigDecimal)
        BigDecimal total = order.getItems().stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalAmount(total.doubleValue());

        // Aplicar descuento para estudiantes frecuentes (5to pedido = 10% descuento)
        if (order.getCustomerId() != null && !order.getCustomerId().isEmpty()) {
            applyStudentDiscount(order);
        }

        // Tiempo estimado muy simple
        int baseTime = 5;   // minutos base
        int perItem = 2;    // minutos por unidad
        int itemCount = order.getItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
        order.setEstimatedTimeMinutes(baseTime + perItem * itemCount);

        // Historial de estados
        if (order.getStatusHistory() == null) {
            order.setStatusHistory(new ArrayList<>());
        }

        OrderStatusChange firstChange = OrderStatusChange.builder()
                .fromStatus(null)
                .toStatus(OrderStatus.PENDING)
                .changedAt(now)
                .changedBy(order.getEmployeeId())
                .build();

        order.getStatusHistory().add(firstChange);

        return orderRepository.save(order);
    }

    // Aplicar descuento a estudiantes frecuentes
    private void applyStudentDiscount(Order order) {
        // Contar pedidos completados del cliente
        List<Order> customerOrders = orderRepository.findAll().stream()
                .filter(o -> order.getCustomerId().equals(o.getCustomerId()))
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .filter(o -> !o.isCancelled())
                .toList();

        int completedOrders = customerOrders.size();

        // Cada 5 pedidos completados = 10% descuento en el siguiente
        if ((completedOrders + 1) % 5 == 0) {
            double discountPercentage = 10.0;
            double discountAmount = order.getTotalAmount() * (discountPercentage / 100.0);
            
            order.setDiscountPercentage(discountPercentage);
            order.setDiscountAmount(discountAmount);
            order.setTotalAmount(order.getTotalAmount() - discountAmount);
            order.setPromotionDescription("🎉 Descuento estudiante frecuente: " + discountPercentage + "% OFF");
        }
    }

    // Generar número de pedido único (ORD-20231124-001)
    private String generateOrderNumber() {
        Instant now = Instant.now();
        String datePart = now.toString().substring(0, 10).replace("-", "");
        
        // Contar pedidos del día para generar secuencial
        Instant startOfDay = now.truncatedTo(java.time.temporal.ChronoUnit.DAYS);
        Instant endOfDay = startOfDay.plus(1, java.time.temporal.ChronoUnit.DAYS);
        
        long todayCount = orderRepository.findByCreatedAtBetween(startOfDay, endOfDay).size();
        String sequential = String.format("%03d", todayCount + 1);
        
        return "ORD-" + datePart + "-" + sequential;
    }

    // Calcular turno basado en la hora
    private String calculateShift(Instant time) {
        int hour = time.atZone(java.time.ZoneId.systemDefault()).getHour();
        
        if (hour >= 6 && hour < 14) {
            return "MAÑANA";
        } else if (hour >= 14 && hour < 22) {
            return "TARDE";
        } else {
            return "NOCHE";
        }
    }

    // Editar pedido solo si está en PENDING (HU004)
    public Order update(String id, Order updated) {
        Order existing = findById(id);

        if (existing.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Solo se pueden modificar pedidos en estado PENDING");
        }

        existing.setCustomerName(updated.getCustomerName());
        existing.setCustomerId(updated.getCustomerId());
        existing.setPaymentMethod(updated.getPaymentMethod());
        existing.setItems(updated.getItems());

        if (existing.getItems() == null) {
            existing.setItems(new ArrayList<>());
        }

        BigDecimal total = existing.getItems().stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        existing.setTotalAmount(total.doubleValue());

        int baseTime = 5;
        int perItem = 2;
        int itemCount = existing.getItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
        existing.setEstimatedTimeMinutes(baseTime + perItem * itemCount);

        existing.setUpdatedAt(Instant.now());

        return orderRepository.save(existing);
    }

    // ❌ HU004: ahora sí, solo borrar si está en PENDING
    public void delete(String id) {
        Order order = findById(id);
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Solo se pueden eliminar pedidos en estado PENDING");
        }
        orderRepository.deleteById(id);
    }

    // Cambiar estado respetando el flujo y registrando historial
    public Order updateStatus(String orderId, OrderStatus newStatus, String changedBy) {
        Order order = findById(orderId);

        OrderStatus current = order.getStatus();
        Assert.notNull(current, "Current order status must not be null");

        int currentIndex = ORDER_FLOW.indexOf(current);
        int newIndex = ORDER_FLOW.indexOf(newStatus);

        if (newIndex < 0) {
            throw new IllegalArgumentException("Estado inválido: " + newStatus);
        }

        if (newIndex < currentIndex) {
            throw new IllegalStateException("No se puede retroceder el estado del pedido");
        }

        if (newIndex == currentIndex) {
            return order;
        }

        order.setStatus(newStatus);
        order.setUpdatedAt(Instant.now());

        if (order.getStatusHistory() == null) {
            order.setStatusHistory(new ArrayList<>());
        }

        OrderStatusChange change = OrderStatusChange.builder()
                .fromStatus(current)
                .toStatus(newStatus)
                .changedAt(order.getUpdatedAt())
                .changedBy(changedBy)
                .build();

        order.getStatusHistory().add(change);

        if (newStatus == OrderStatus.READY) {
            order.setEstimatedTimeMinutes(0);
        }

        return orderRepository.save(order);
    }

    // Cancelar pedido (solo PENDING)
    public Order cancelOrder(String orderId, String cancelledBy, String reason) {
        Order order = findById(orderId);

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Solo se pueden cancelar pedidos en estado PENDING");
        }

        if (order.isCancelled()) {
            throw new IllegalStateException("El pedido ya está cancelado");
        }

        order.setCancelled(true);
        order.setCancelledBy(cancelledBy);
        order.setCancelReason(reason);
        order.setUpdatedAt(Instant.now());

        return orderRepository.save(order);
    }
}
