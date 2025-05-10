package sgu.beo.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product_variant")
public class ProductVariant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_id", nullable = false)
    private int product_id;

    @Column(name = "color", nullable = false, length = 255)
    private String color;

    @Column(name = "price", nullable = false)
    private long price;

    @Column(name = "cost", nullable = false)
    private long cost;

    @Column(name = "img_url", length = 255)
    private String img_url;

    @Column(name = "status", nullable = false, length = 255)
    private String status;

    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    private boolean is_deleted = false;

    @OneToMany
    @JoinColumn(name = "product_variant_id")
    private List<Stock> stocks;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variant_id")
    private List<ProductVariantHistory> productVariantHistories;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variant_id")
    private List<ImportInvoiceDetail> importInvoiceDetail;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_variant_id")
    private List<SaleInvoiceDetail> saleInvoiceDetail;

}
