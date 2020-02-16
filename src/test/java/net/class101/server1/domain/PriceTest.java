package net.class101.server1.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PriceTest {
    private static final int UPPER_DELIVERY = 50000;
    private static final int UNDER_DELIVERY = 49999;
    private Price price;

    @Test
    @DisplayName("가격이 50000원 미만이면 배달료가 붙는 테스트")
    void price_under_50000() {
        price = new Price(UNDER_DELIVERY);
        assertThat(price.getPurchasePrice()).isEqualTo(49999);
        assertThat(price.getTotalPrice()).isEqualTo(54999);
    }

    @Test
    @DisplayName("가격이 50000원 이이상면 배달료가 안붙는 테스트")
    void price_upper_50000() {
        price = new Price(UPPER_DELIVERY);
        assertThat(price.getPurchasePrice()).isEqualTo(50000);
        assertThat(price.getTotalPrice()).isEqualTo(50000);
    }
}