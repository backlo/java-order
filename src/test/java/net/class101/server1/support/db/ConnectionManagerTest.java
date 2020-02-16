package net.class101.server1.support.db;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectionManagerTest {

    @Test
    @DisplayName("데이터 베이스 연결 테스트")
    void getDataSourceTest() {
        try (Connection con = ConnectionManager.getDataSource().getConnection()) {
            String[] conInfo = con.toString().split(", ");
            assertThat(conInfo[1]).isEqualTo("URL=jdbc:h2:mem:server1");
            assertThat(conInfo[2]).isEqualTo("UserName=SA");
            assertThat(conInfo[3]).isEqualTo("H2 JDBC Driver");
        } catch (Exception e) {
            System.out.println("커넥션 실패");
        }
    }
}