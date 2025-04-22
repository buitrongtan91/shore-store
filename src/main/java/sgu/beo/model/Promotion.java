package sgu.beo.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "promotion")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "promotion_type", nullable = false)
    private String promotion_type;

    @Column(name = "promotion_value", nullable = false)
    private double promotion_value;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime start_date;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime end_date;

    @Column(name = "min_invoice_value", nullable = false)
    private double min_invoice_value;

    @Column(name = "max_discount_value", nullable = false)
    private double max_discount_value;

    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    private boolean is_active = true;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean is_deleted = false;

    @OneToMany
    @JoinColumn(name = "promotion_id")
    private List<SaleInvoice> saleInvoice;
}
