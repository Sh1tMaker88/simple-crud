package com.godel.simplecrud.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "product")
@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name")
    @Pattern(regexp =  "[A-Z]\\D{1,}", message = "Product name must start with capital letter, have length 2+ and contains no numbers")
    private String name;

    @JsonIgnoreProperties("products")
//    @JsonIdentityReference(alwaysAsId = true)
//    @JsonIdentityInfo(property = "orderId", generator = ObjectIdGenerators.PropertyGenerator.class)
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE},
            fetch = FetchType.EAGER, mappedBy = "products")
    private List<Order> orders = new ArrayList<>();

    @Positive(message = "Price can not be less than 0")
    @Column(name = "price")
    private Double price;

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", ordersID=" + orders.stream().map(Order::getOrderId).collect(Collectors.toList()) +
                ", price=" + price +
                '}';
    }
}