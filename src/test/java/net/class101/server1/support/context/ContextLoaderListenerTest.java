package net.class101.server1.support.context;

import net.class101.server1.domain.Item;
import net.class101.server1.domain.OrderItem;
import net.class101.server1.support.db.ConnectionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class ContextLoaderListenerTest {

    private ContextLoaderListener contextLoaderListener;

    @Test
    @DisplayName("앱 시작시 데이터 베이스가 h2에 잘 들어가는지 테스트")
    void contextInitializedTest() {
        contextLoaderListener = new ContextLoaderListener();
        contextLoaderListener.contextInitialized();

        JdbcTemplate jdbcTemplate = new JdbcTemplate(ConnectionManager.getDataSource());

        Item item = findById(16374, jdbcTemplate);

        assertThat(item).isEqualTo(
                new OrderItem(16374, "클래스", "스마트스토어로 월 100만원 만들기, 평범한 사람이 돈을 만드는 비법", 151950, 99999)
        );
    }

    private Item findById(int id, JdbcTemplate jdbcTemplate) {
        String query = "SELECT * FROM ITEMS WHERE id = ?";
        return jdbcTemplate.queryForObject(query, this::getOrderItem, id);
    }

    private Item getOrderItem(ResultSet rs, int rowNum) throws SQLException {
        return new OrderItem(rs.getInt("id"),
                rs.getString("kind"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getInt("stockNumber"));
    }
}