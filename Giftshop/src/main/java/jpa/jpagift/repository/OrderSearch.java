package jpa.jpagift.repository;

import jpa.jpagift.domain.status.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearch {

    private String name;
    private OrderStatus orderStatus;
}
