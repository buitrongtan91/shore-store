package sgu.beo.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "discount")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "discount_value", nullable = false)
    private Long discount_value;

    @Column(name = "start_date", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime start_date;

    @Column(name = "end_date", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime end_date;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private boolean is_active = true;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean is_deleted = false;

    @ManyToMany
    @JoinTable(name = "discount_product", joinColumns = @JoinColumn(name = "discount_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
}
