package sgu.beo.event;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import sgu.beo.DTO.CartItemDTO;
import sgu.beo.model.Supplier;

@Data
@AllArgsConstructor
public class ConfirmImportEvent {
    private List<CartItemDTO> items;
    private Supplier supplier;
}
