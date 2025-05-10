package sgu.beo.event;

import sgu.beo.model.Customer;

public class CustomerCreatedEvent {
    private final Customer customer;

    public CustomerCreatedEvent(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}