package com.godel.simplecrud.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "ordering")
@Entity
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @JsonIgnoreProperties("orders")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonIdentityInfo(property = "employeeId", generator = ObjectIdGenerators.PropertyGenerator.class)
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE}
//            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "customer_id")
    private Employee customer;

    @JsonIgnoreProperties("orders")
//    @JsonIdentityReference(alwaysAsId = true)
//    @JsonIdentityInfo(property = "productId", generator = ObjectIdGenerators.PropertyGenerator.class)
    @ManyToMany(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE},
            fetch = FetchType.EAGER)
    @JoinTable(name = "ordering_product",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "product_id"))
    private List<Product> products = new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @Column(name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    @Positive(message = "Total price can not be less than 0")
    @Column(name = "total_price")
    private Double totalPrice;

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customer.getEmployeeId() +
                ", productsID=" + products.stream().map(Product::getProductId).collect(Collectors.toList()) +
                ", orderDate=" + orderDate +
                ", totalPrice=" + totalPrice +
                '}';
    }
}