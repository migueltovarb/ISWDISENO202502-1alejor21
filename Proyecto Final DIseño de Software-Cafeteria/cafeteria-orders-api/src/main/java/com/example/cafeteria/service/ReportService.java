package com.example.cafeteria.service;

import com.example.cafeteria.domain.Order;
import com.example.cafeteria.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final OrderRepository orderRepository;

    public ReportService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Map<String, Object> dailyReport(LocalDate date) {
        Instant start = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant end = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);

        List<Order> orders = orderRepository.findByCreatedAtBetween(start, end);

        int totalOrders = orders.size();

        // totalAmount es double → convertimos a BigDecimal
        BigDecimal totalIncome = orders.stream()
                .map(order -> BigDecimal.valueOf(order.getTotalAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Ventas por turno
        Map<String, Object> shiftSales = calculateShiftSales(orders);

        // Ventas por empleado
        Map<String, Object> employeeSales = calculateEmployeeSales(orders);

        // Productos más vendidos
        Map<String, Object> topProducts = calculateTopProducts(orders);

        Map<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("totalOrders", totalOrders);
        result.put("totalIncome", totalIncome);
        result.put("shiftSales", shiftSales);
        result.put("employeeSales", employeeSales);
        result.put("topProducts", topProducts);
        return result;
    }

    private Map<String, Object> calculateShiftSales(List<Order> orders) {
        Map<String, Integer> shiftCount = new HashMap<>();
        Map<String, BigDecimal> shiftIncome = new HashMap<>();

        for (Order order : orders) {
            String shift = order.getShift() != null ? order.getShift() : "SIN_TURNO";
            shiftCount.put(shift, shiftCount.getOrDefault(shift, 0) + 1);
            
            BigDecimal current = shiftIncome.getOrDefault(shift, BigDecimal.ZERO);
            shiftIncome.put(shift, current.add(BigDecimal.valueOf(order.getTotalAmount())));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("count", shiftCount);
        result.put("income", shiftIncome);
        return result;
    }

    private Map<String, Object> calculateEmployeeSales(List<Order> orders) {
        Map<String, Integer> employeeCount = new HashMap<>();
        Map<String, BigDecimal> employeeIncome = new HashMap<>();

        for (Order order : orders) {
            String employee = order.getEmployeeId() != null ? order.getEmployeeId() : "SIN_ASIGNAR";
            employeeCount.put(employee, employeeCount.getOrDefault(employee, 0) + 1);
            
            BigDecimal current = employeeIncome.getOrDefault(employee, BigDecimal.ZERO);
            employeeIncome.put(employee, current.add(BigDecimal.valueOf(order.getTotalAmount())));
        }

        Map<String, Object> result = new HashMap<>();
        result.put("count", employeeCount);
        result.put("income", employeeIncome);
        return result;
    }

    private Map<String, Object> calculateTopProducts(List<Order> orders) {
        Map<String, Integer> productCount = new HashMap<>();
        Map<String, BigDecimal> productRevenue = new HashMap<>();

        for (Order order : orders) {
            if (order.getItems() != null) {
                for (var item : order.getItems()) {
                    String productName = item.getProductName() != null ? item.getProductName() : "Producto";
                    productCount.put(productName, productCount.getOrDefault(productName, 0) + item.getQuantity());
                    
                    BigDecimal revenue = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                    BigDecimal current = productRevenue.getOrDefault(productName, BigDecimal.ZERO);
                    productRevenue.put(productName, current.add(revenue));
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("count", productCount);
        result.put("revenue", productRevenue);
        return result;
    }
}
