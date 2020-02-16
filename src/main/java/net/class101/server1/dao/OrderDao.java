package net.class101.server1.dao;

import net.class101.server1.domain.Item;
import net.class101.server1.domain.MenuItem;
import net.class101.server1.domain.OrderItem;
import net.class101.server1.support.db.ConnectionManager;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDao {
    private final JdbcTemplate jdbcTemplate;

    public OrderDao() {
        this.jdbcTemplate = new JdbcTemplate(ConnectionManager.getDataSource());
    }

    public List<Item> findAll() {
        String query = "SELECT id, name, price, stockNumber FROM ITEMS";
        return jdbcTemplate.query(query, this::getMenuItem);
    }

    public Item findById(int id) {
        String query = "SELECT * FROM ITEMS WHERE id = ?";
        return jdbcTemplate.queryForObject(query, this::getOrderItem, id);
    }

    public int update(Item findItem) {
        String query = "UPDATE ITEMS SET stockNumber = ? WHERE id = ?";
        return jdbcTemplate.update(query, findItem.getStockNumber(), findItem.getId());
    }

    private Item getMenuItem(ResultSet rs, int rowNum) throws SQLException {
        return new MenuItem(rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("stockNumber"));
    }

    private Item getOrderItem(ResultSet rs, int rowNum) throws SQLException {
        return new OrderItem(rs.getInt("id"),
                rs.getString("kind"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("stockNumber"));
    }
}
