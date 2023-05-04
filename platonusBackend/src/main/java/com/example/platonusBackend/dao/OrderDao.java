package com.example.platonusBackend.dao;

import com.example.platonusBackend.entity.Order;
import com.example.util.ConnectionManager;

import java.sql.*;
import java.util.Optional;

public class OrderDao {
    private static final OrderDao INSTANCE = new OrderDao();

    public static OrderDao getInstance() {
        return INSTANCE;
    }


    private static final String SAVE_SQL = """
            INSERT INTO orders(name, quantity, price)
            VALUES(?, ?, ?)
            """;
    private static final String UPDATE_SQL = """
            update orders
            set name=?,
            quantity=?,
            price=?
            where id = ?
            """;

    private static final String FIND_BY_ID = """
            select id, name, quantity, price
            from orders
            where id = ?
            """;

    private static final String IS_PRESENT_SQL = """
            SELECT true FROM orders
            where id=?
            """;



    public Order create(Order order) {
        try (Connection connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, order.getName());
            prepareStatement.setInt(2, order.getQuantity());
            prepareStatement.setDouble(3, order.getPrice());
            prepareStatement.executeUpdate();

            var result = prepareStatement.getGeneratedKeys();
            if (result.next()) {
                order.setId(result.getLong("id"));
            }
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order update(Order order) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(UPDATE_SQL)) {
            prepareStatement.setString(1, order.getName());
            prepareStatement.setInt(2, order.getQuantity());
            prepareStatement.setDouble(3, order.getPrice());
            prepareStatement.setLong(4, order.getId());

            prepareStatement.executeUpdate();

            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Optional<Order> getById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(FIND_BY_ID)) {
            prepareStatement.setLong(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = new Order(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price")
                );
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isPresent(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement prepareStatement = connection.prepareStatement(IS_PRESENT_SQL)) {
            prepareStatement.setLong(1, id);

            ResultSet resultSet = prepareStatement.executeQuery();

            resultSet.next();
            return resultSet.rowUpdated();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
