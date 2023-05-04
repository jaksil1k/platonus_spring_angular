package com.example.platonusBackend.serivce;

import com.example.platonusBackend.dao.OrderDao;
import com.example.platonusBackend.entity.Order;
import com.example.platonusBackend.exception.OrderNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    private final OrderDao orderDao = OrderDao.getInstance();

    public Order updateOrSave(Order order) {
        if (order.getId() != null) {
            if (orderDao.isPresent(order.getId())) {
                throw new OrderNotFoundException("no order with id = " + order.getId());
            }
            return orderDao.update(order);
        }
        return orderDao.create(order);
    }

    public Optional<Order> getById(Long id) {
        return orderDao.getById(id);
    }
}
