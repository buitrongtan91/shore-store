package sgu.beo.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import sgu.beo.DTO.CartItemDTO;

@Data
@AllArgsConstructor
public class ChangeQuantityInCart {
    private CartItemDTO item;
    private int newQuantity;
}
