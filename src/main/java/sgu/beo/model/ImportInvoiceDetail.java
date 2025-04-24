package sgu.beo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "import_invoice_detail")
public class ImportInvoiceDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "import_invoice_id", nullable = false)
    private int import_invoice_id;

    @Column(name = "product_variant_id", nullable = false)
    private int product_variant_id;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private double unit_price;

    @Column(name = "total_price", nullable = false)
    private double total_price;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean is_deleted = false;
}
