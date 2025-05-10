package sgu.beo.DTO;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import sgu.beo.model.ProductVariant;

@Data
@AllArgsConstructor
public class CartItemDTO {
    private ProductVariant productVariant;
    private String name;
    private String brand;
    private String category;
    private String size;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CartItemDTO))
            return false;
        CartItemDTO that = (CartItemDTO) o;
        return that.size.equals(this.size) && Objects.equals(productVariant.getId(), that.productVariant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(productVariant.getId());
    }

    public long getTotalPrice() {
        return quantity * productVariant.getPrice();
    }

    public long getTotalCost() {
        return quantity * productVariant.getCost();
    }

}
