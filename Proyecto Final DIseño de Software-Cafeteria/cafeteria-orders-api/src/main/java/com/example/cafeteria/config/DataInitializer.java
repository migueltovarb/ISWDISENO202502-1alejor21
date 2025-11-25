package com.example.cafeteria.config;

import com.example.cafeteria.domain.*;
import com.example.cafeteria.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            ProductRepository productRepository) {
        return args -> {
            // Inicializar usuarios de prueba si no existen
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setFullName("Administrador del Sistema");
                admin.setEmail("admin@cafeteria.com");
                admin.setRole(UserRole.ADMIN);
                admin.setActive(true);

                User employee = new User();
                employee.setUsername("empleado");
                employee.setPassword("emp123");
                employee.setFullName("Juan Empleado Cajero");
                employee.setEmail("empleado@cafeteria.com");
                employee.setRole(UserRole.EMPLOYEE);
                employee.setActive(true);

                User student = new User();
                student.setUsername("estudiante");
                student.setPassword("est123");
                student.setFullName("María Estudiante");
                student.setEmail("maria.estudiante@campus.edu");
                student.setRole(UserRole.STUDENT);
                student.setActive(true);

                User staff = new User();
                staff.setUsername("personal");
                staff.setPassword("per123");
                staff.setFullName("Carlos Personal Campus");
                staff.setEmail("carlos.personal@campus.edu");
                staff.setRole(UserRole.STAFF);
                staff.setActive(true);

                userRepository.saveAll(List.of(admin, employee, student, staff));
                System.out.println("✅ Usuarios de prueba creados:");
                System.out.println("   - Admin: username=admin, password=admin123");
                System.out.println("   - Empleado/Cajero: username=empleado, password=emp123");
                System.out.println("   - Estudiante: username=estudiante, password=est123");
                System.out.println("   - Personal Campus: username=personal, password=per123");
            }

            // Inicializar productos de prueba si no existen
            if (productRepository.count() == 0) {
                Product coffee = new Product();
                coffee.setName("Café Americano");
                coffee.setCategory(ProductCategory.HOT_DRINK);
                coffee.setPrice(new BigDecimal("2500.00"));
                coffee.setActive(true);

                Product cappuccino = new Product();
                cappuccino.setName("Cappuccino");
                cappuccino.setCategory(ProductCategory.HOT_DRINK);
                cappuccino.setPrice(new BigDecimal("3500.00"));
                cappuccino.setActive(true);

                Product latte = new Product();
                latte.setName("Latte");
                latte.setCategory(ProductCategory.HOT_DRINK);
                latte.setPrice(new BigDecimal("3800.00"));
                latte.setActive(true);

                Product icedCoffee = new Product();
                icedCoffee.setName("Café Frío");
                icedCoffee.setCategory(ProductCategory.COLD_DRINK);
                icedCoffee.setPrice(new BigDecimal("4000.00"));
                icedCoffee.setActive(true);

                Product smoothie = new Product();
                smoothie.setName("Smoothie de Frutas");
                smoothie.setCategory(ProductCategory.COLD_DRINK);
                smoothie.setPrice(new BigDecimal("5000.00"));
                smoothie.setActive(true);

                Product sandwich = new Product();
                sandwich.setName("Sandwich de Pollo");
                sandwich.setCategory(ProductCategory.FAST_FOOD);
                sandwich.setPrice(new BigDecimal("6500.00"));
                sandwich.setActive(true);

                Product burger = new Product();
                burger.setName("Hamburguesa Clásica");
                burger.setCategory(ProductCategory.FAST_FOOD);
                burger.setPrice(new BigDecimal("8000.00"));
                burger.setActive(true);

                Product cheesecake = new Product();
                cheesecake.setName("Cheesecake");
                cheesecake.setCategory(ProductCategory.DESSERT);
                cheesecake.setPrice(new BigDecimal("4500.00"));
                cheesecake.setActive(true);

                Product brownie = new Product();
                brownie.setName("Brownie con Helado");
                brownie.setCategory(ProductCategory.DESSERT);
                brownie.setPrice(new BigDecimal("5500.00"));
                brownie.setActive(true);

                productRepository.saveAll(List.of(
                    coffee, cappuccino, latte, icedCoffee, smoothie,
                    sandwich, burger, cheesecake, brownie
                ));
                
                System.out.println("✅ Productos de prueba creados (9 productos)");
            }
        };
    }
}
