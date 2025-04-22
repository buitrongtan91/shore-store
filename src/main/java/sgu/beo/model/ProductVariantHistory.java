package sgu.beo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "product_variant_history")
public class ProductVariantHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_variant_id", nullable = false)
    private int product_variant_id;

    @Column(name = "old_price", nullable = false)
    private double old_price;

    @Column(name = "new_price", nullable = false)
    private double new_price;

    @Column(name = "changed_at", nullable = false)
    private LocalDateTime changed_at;

}
