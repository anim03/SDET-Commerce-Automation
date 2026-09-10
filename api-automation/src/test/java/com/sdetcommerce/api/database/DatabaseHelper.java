package com.sdetcommerce.api.database;

import com.sdetcommerce.api.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHelper {

    private DatabaseHelper() {
    }

    public static ProductDbRecord getProductById(
            Long productId) {

        String sql =
                """
                SELECT id, name, description, price, stock
                FROM products
                WHERE id = ?
                """;

        try (
                Connection connection =
                        DriverManager.getConnection(
                                DatabaseConfig.getDbUrl(),
                                DatabaseConfig.getDbUsername(),
                                DatabaseConfig.getDbPassword()
                        );

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setLong(
                    1,
                    productId
            );

            try (
                    ResultSet resultSet =
                            statement.executeQuery()
            ) {

                if (!resultSet.next()) {
                    return null;
                }

                return new ProductDbRecord(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getBigDecimal("price"),
                        resultSet.getInt("stock")
                );
            }

        } catch (SQLException exception) {

            throw new RuntimeException(
                    "Database query failed for product ID: "
                            + productId,
                    exception
            );
        }
    }
    public static CartDbRecord getCartItemById(Long cartItemId) {

    String sql = """
            SELECT id, user_id, product_id, quantity
            FROM cart_items
            WHERE id = ?
            """;

    try (
            Connection connection =
                    DriverManager.getConnection(
                            DatabaseConfig.getDbUrl(),
                            DatabaseConfig.getDbUsername(),
                            DatabaseConfig.getDbPassword()
                    );

            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setLong(
                1,
                cartItemId
        );

        try (
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (!resultSet.next()) {
                return null;
            }

            return new CartDbRecord(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("product_id"),
                    resultSet.getInt("quantity")
            );
        }

    } catch (SQLException exception) {

        throw new RuntimeException(
                "Database query failed for cart item ID: "
                        + cartItemId,
                exception
        );
    }
}
public static CartDbRecord getCartItemByProductId(
        Long productId) {

    String sql = """
            SELECT id, user_id, product_id, quantity
            FROM cart_items
            WHERE product_id = ?
            """;

    try (
            Connection connection =
                    DriverManager.getConnection(
                            DatabaseConfig.getDbUrl(),
                            DatabaseConfig.getDbUsername(),
                            DatabaseConfig.getDbPassword()
                    );

            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setLong(
                1,
                productId
        );

        try (
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (!resultSet.next()) {
                return null;
            }

            return new CartDbRecord(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getLong("product_id"),
                    resultSet.getInt("quantity")
            );
        }

    } catch (SQLException exception) {

        throw new RuntimeException(
                "Database query failed for cart product ID: "
                        + productId,
                exception
        );
    }
}
public static OrderDbRecord getOrderById(
        Long orderId) {

    String sql = """
            SELECT id, user_id, total_amount, status
            FROM orders
            WHERE id = ?
            """;

    try (
            Connection connection =
                    DriverManager.getConnection(
                            DatabaseConfig.getDbUrl(),
                            DatabaseConfig.getDbUsername(),
                            DatabaseConfig.getDbPassword()
                    );

            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setLong(
                1,
                orderId
        );

        try (
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (!resultSet.next()) {
                return null;
            }

            return new OrderDbRecord(
                    resultSet.getLong("id"),
                    resultSet.getLong("user_id"),
                    resultSet.getBigDecimal("total_amount"),
                    resultSet.getString("status")
            );
        }

    } catch (SQLException exception) {

        throw new RuntimeException(
                "Database query failed for order ID: "
                        + orderId,
                exception
        );
    }
}
public static OrderItemDbRecord getOrderItemByOrderId(
        Long orderId) {

    String sql = """
            SELECT id,
                   order_id,
                   product_id,
                   product_name,
                   price,
                   quantity,
                   subtotal
            FROM order_items
            WHERE order_id = ?
            ORDER BY id
            LIMIT 1
            """;

    try (
            Connection connection =
                    DriverManager.getConnection(
                            DatabaseConfig.getDbUrl(),
                            DatabaseConfig.getDbUsername(),
                            DatabaseConfig.getDbPassword()
                    );

            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setLong(
                1,
                orderId
        );

        try (
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (!resultSet.next()) {
                return null;
            }

            return new OrderItemDbRecord(
                    resultSet.getLong("id"),
                    resultSet.getLong("order_id"),
                    resultSet.getLong("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getBigDecimal("price"),
                    resultSet.getInt("quantity"),
                    resultSet.getBigDecimal("subtotal")
            );
        }

    } catch (SQLException exception) {

        throw new RuntimeException(
                "Database query failed for order item. Order ID: "
                        + orderId,
                exception
        );
    }
}
public static void deleteOrderById(
        Long orderId) {

    if (orderId == null) {
        return;
    }

    String deletePaymentsSql = """
            DELETE FROM payments
            WHERE order_id = ?
            """;

    String deleteOrderItemsSql = """
            DELETE FROM order_items
            WHERE order_id = ?
            """;

    String deleteOrderSql = """
            DELETE FROM orders
            WHERE id = ?
            """;

    try (
            Connection connection =
                    DriverManager.getConnection(
                            DatabaseConfig.getDbUrl(),
                            DatabaseConfig.getDbUsername(),
                            DatabaseConfig.getDbPassword()
                    )
    ) {

        connection.setAutoCommit(false);

        try (
                PreparedStatement deletePaymentsStatement =
                        connection.prepareStatement(
                                deletePaymentsSql
                        );

                PreparedStatement deleteItemsStatement =
                        connection.prepareStatement(
                                deleteOrderItemsSql
                        );

                PreparedStatement deleteOrderStatement =
                        connection.prepareStatement(
                                deleteOrderSql
                        )
        ) {

            deletePaymentsStatement.setLong(
                    1,
                    orderId
            );

            int deletedPayments =
                    deletePaymentsStatement
                            .executeUpdate();

            deleteItemsStatement.setLong(
                    1,
                    orderId
            );

            int deletedItems =
                    deleteItemsStatement
                            .executeUpdate();

            deleteOrderStatement.setLong(
                    1,
                    orderId
            );

            int deletedOrders =
                    deleteOrderStatement
                            .executeUpdate();

            connection.commit();

            System.out.println(
                    "Order cleanup successful. "
                            + "orderId=" + orderId
                            + ", paymentsDeleted="
                            + deletedPayments
                            + ", orderItemsDeleted="
                            + deletedItems
                            + ", ordersDeleted="
                            + deletedOrders
            );

        } catch (SQLException exception) {

            connection.rollback();

            throw exception;
        }

    } catch (SQLException exception) {

        throw new RuntimeException(
                "Database cleanup failed for order ID: "
                        + orderId,
                exception
        );
    }
}
public static PaymentDbRecord getPaymentByOrderId(
        Long orderId) {

    String sql = """
            SELECT id,
                   order_id,
                   amount,
                   payment_method,
                   status,
                   transaction_id
            FROM payments
            WHERE order_id = ?
            """;

    try (
            Connection connection =
                    DriverManager.getConnection(
                            DatabaseConfig.getDbUrl(),
                            DatabaseConfig.getDbUsername(),
                            DatabaseConfig.getDbPassword()
                    );

            PreparedStatement statement =
                    connection.prepareStatement(sql)
    ) {

        statement.setLong(
                1,
                orderId
        );

        try (
                ResultSet resultSet =
                        statement.executeQuery()
        ) {

            if (!resultSet.next()) {
                return null;
            }

            return new PaymentDbRecord(
                    resultSet.getLong("id"),
                    resultSet.getLong("order_id"),
                    resultSet.getBigDecimal("amount"),
                    resultSet.getString("payment_method"),
                    resultSet.getString("status"),
                    resultSet.getString("transaction_id")
            );
        }

    } catch (SQLException exception) {

        throw new RuntimeException(
                "Database query failed for payment. Order ID: "
                        + orderId,
                exception
        );
    }
}
}