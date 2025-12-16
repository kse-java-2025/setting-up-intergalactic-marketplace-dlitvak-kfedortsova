package com.cosmocats.intergalactic_market.entity;

import com.cosmocats.intergalactic_market.domain.order.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderEntryEntity> orderEntries;

    Double totalPrice;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    LocalDateTime orderTime;
}
