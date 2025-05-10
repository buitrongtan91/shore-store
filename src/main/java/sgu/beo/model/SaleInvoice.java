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
@Table(name = "sale_invoice")
public class SaleInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "customer_id", nullable = false)
    private int customer_id;

    @Column(name = "employee_id", nullable = false)
    private int employee_id;

    @Column(name = "sale_date", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime sale_date;

    @Column(name = "total_amount", nullable = false)
    private long total_amount;

    @Column(name = "promotion_id")
    private Integer promotion_id;

    @Column(name = "promotion_amount")
    private long promotion_amount;

    @Column(name = "final_amount", nullable = false)
    private long final_amount;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean is_deleted = false;

    @OneToMany
    @JoinColumn(name = "sale_invoice_id")
    private List<SaleInvoiceDetail> saleInvoiceDetail;
}
