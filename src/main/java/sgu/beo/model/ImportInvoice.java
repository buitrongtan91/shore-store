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
@Table(name = "import_invoice")
public class ImportInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "supplier_id", nullable = false)
    private int supplier_id;

    @Column(name = "employee_id", nullable = false)
    private int employee_id;

    @Column(name = "import_date", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime import_date;

    @Column(name = "total_amount", nullable = false)
    private long total_amount;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean is_deleted = false;

    @OneToMany
    @JoinColumn(name = "import_invoice_id")
    private List<ImportInvoiceDetail> importInvoiceDetail;
}
