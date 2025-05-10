package sgu.beo.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.common.eventbus.Subscribe;

import javafx.beans.binding.Bindings;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sgu.beo.App;
import sgu.beo.AppEventBus;
import sgu.beo.DTO.CartItemDTO;
import sgu.beo.event.AddToCartEvent;
import sgu.beo.event.ChangeQuantityInCart;
import sgu.beo.event.ConfirmInvoiceEvent;
import sgu.beo.event.CustomerCreatedEvent;
import sgu.beo.event.RemoveFromCartEvent;
import sgu.beo.model.Customer;
import sgu.beo.model.Employee;
import sgu.beo.model.Product;
import sgu.beo.model.Promotion;
import sgu.beo.service.CustomerService;
import sgu.beo.service.ProductService;
import sgu.beo.service.PromotionService;
import sgu.beo.service.SaleInvoiceService;
import sgu.beo.service.StockService;

public class SaleController implements Initializable {
    @FXML
    private Label finalAmountLabel;

    @FXML
    private Label promotionLabel;

    @FXML
    private Button clearBtt;

    @FXML
    private Button paymentBtt;

    @FXML
    private Button newCustomerBtt;

    @FXML
    private TextField searchField;

    @FXML
    private Label customerNameLbl;

    @FXML
    private Label customerPhoneLbl;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private Label totalLabel;

    @FXML
    private VBox cartItemVBox;

    @FXML
    private VBox productVBox;
    private List<CartItemDTO> cartItems = new ArrayList<>();
    private List<Product> products = ProductService.getAll();
    private LongProperty total = new SimpleLongProperty(0);
    private LongProperty finalAmount = new SimpleLongProperty(0);
    private List<Customer> customers = CustomerService.getCustomerList();
    private ObservableList<Customer> observableCustomerList = FXCollections.observableArrayList(customers);
    private Customer customer;
    private Employee employee;
    private Promotion promotion;

    public void setData(Employee employee) {
        this.employee = employee;
        System.out.println(employee);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        AppEventBus.getInstance().register(this);
        totalLabel.textProperty().bind(Bindings.format("%,d VNĐ", total));
        finalAmountLabel.textProperty().bind(Bindings.format("%,d VNĐ", finalAmount));

        FilteredList<Customer> filteredList = new FilteredList<>(observableCustomerList);
        customerComboBox.setItems(filteredList);

        searchField.textProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue == null || newValue.isEmpty()) {
                filteredList.setPredicate(item -> true);
            } else {
                filteredList.setPredicate(item -> item.getPhone().toLowerCase().contains(newValue.toLowerCase())
                        || item.getName().toLowerCase().contains(newValue.toLowerCase()));
            }
        });

        customerComboBox.valueProperty().addListener((obs, oldVal, newValue) -> {
            if (newValue != null) {
                customer = newValue;
                customerNameLbl.setText(newValue.getName());
                customerPhoneLbl.setText(newValue.getPhone());
            } else {
                customerNameLbl.setText("");
                customerPhoneLbl.setText("");
            }
        });

        newCustomerBtt.setOnAction(event -> handleNewCustomerBttClick());
        paymentBtt.setOnAction(event -> handlePaymentBttClick());
        clearBtt.setOnAction(event -> handleClearBttClick());

        loadProducts();
        loadCart();
    }

    private void loadProducts() {
        productVBox.getChildren().clear();
        for (Product product : products) {
            try {
                FXMLLoader loader = App.getLoader("view/productCardSale");
                Parent p = loader.load();
                ProductCardSaleController controller = loader.getController();
                controller.setProduct(product);
                productVBox.getChildren().add(p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadCart() {
        cartItemVBox.getChildren().clear();
        cartItems.forEach(i -> {
            try {
                FXMLLoader loader = App.getLoader("view/cartItem");
                Parent p = loader.load();
                CartItemController controller = loader.getController();
                controller.setData(i);
                cartItemVBox.getChildren().add(p);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        calculateTotalPrice();
    }

    @Subscribe
    public void onAddToCartEvent(AddToCartEvent event) {
        CartItemDTO newItem = event.getItem();
        if (!cartItems.contains(newItem)) {
            cartItems.add(newItem);
            loadCart();
        }
    }

    @Subscribe
    public void onRemoveFromCartEvent(RemoveFromCartEvent event) {
        cartItems.remove(event.getItem());
        loadCart();
    }

    @Subscribe
    public void onChangeQuantityInCart(ChangeQuantityInCart event) {
        int newQuantity = event.getNewQuantity();
        CartItemDTO item = event.getItem();

        if (newQuantity > StockService.getAvailableQuantity(item.getProductVariant().getId(), item.getSize())) {
            loadCart();
            return;
        }

        cartItems.stream()
                .filter(i -> i.getProductVariant().getId() == item.getProductVariant().getId()
                        && i.getSize().equals(item.getSize()))
                .findFirst()
                .ifPresent(i -> i.setQuantity(newQuantity));
        loadCart();
    }

    private void calculateTotalPrice() {
        promotionLabel.setText("");
        total.setValue(cartItems.stream()
                .mapToLong(i -> i.getTotalPrice())
                .sum());
        ;
        finalAmount.setValue(total.getValue());
        promotion = PromotionService.getBestPromotion(total.getValue());
        if (promotion != null) {
            if (promotion.getPromotion_type().equalsIgnoreCase("percent")) {
                promotionLabel.setText(String.valueOf(promotion.getPromotion_value() + "%"));
                long discountValue = total.getValue() * promotion.getPromotion_value() / 100;
                if (discountValue > promotion.getMax_discount_value()) {
                    discountValue = promotion.getMax_discount_value();
                }
                finalAmount.setValue(total.getValue() - discountValue);
            } else {
                promotionLabel.setText(String.valueOf(promotion.getPromotion_value() + " VNĐ"));
                finalAmount.setValue(
                        total.getValue() - promotion.getPromotion_value());
            }
        }
    }

    private void handleNewCustomerBttClick() {
        try {
            FXMLLoader loader = App.getLoader("view/createNewCustomerModal");
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Thêm vào giỏ hàng");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            CreateNewCustomerModalController controller = loader.getController();
            controller.setData(modalStage);

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onCustomerCreatedEvent(CustomerCreatedEvent event) {
        customers = CustomerService.getCustomerList();
        observableCustomerList.setAll(customers);
    }

    private void handlePaymentBttClick() {
        if (customer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng chon khách hàng!");
            alert.showAndWait();
            return;
        }

        if (cartItems.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Chưa có sản phẩm nào!");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = App.getLoader("view/confirmInvoice");
            Parent root = loader.load();

            Stage modalStage = new Stage();
            modalStage.setTitle("Thêm vào giỏ hàng");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));

            ConfirmInvoiceController controller = loader.getController();
            controller.setData(cartItems, customer, totalLabel.getText(), promotionLabel.getText(),
                    finalAmountLabel.getText(), modalStage);

            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClearBttClick() {
        cartItems.clear();
        customer = null;
        customerComboBox.setValue(null);
        loadCart();
    }

    @Subscribe
    public void onConfirmInvoice(ConfirmInvoiceEvent event) {
        boolean rs = SaleInvoiceService.addSaleInvoice(employee, customer, cartItems, promotion);
        if (rs) {
            products = ProductService.getAll();
            loadProducts();
            Alert alert = new Alert(AlertType.INFORMATION, "Tạo hóa đơn thành công!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.ERROR, "Tạo hóa đơn thất bại!");
            alert.showAndWait();
        }
    }
}
