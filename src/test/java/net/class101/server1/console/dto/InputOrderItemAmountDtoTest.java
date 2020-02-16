package net.class101.server1.console.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class InputOrderItemAmountDtoTest {

    private InputOrderItemAmountDto inputOrderItemAmountDto;

    @Test
    @DisplayName("상품 양 숫자 형식 테스트")
    void checkValidItemAmountTest() {
        inputOrderItemAmountDto = new InputOrderItemAmountDto("1");
        assertThat(inputOrderItemAmountDto.getItemAmount()).isEqualTo(1);
    }

    @Test
    @DisplayName("상품 양 숫자 외 형식 예외처리 테스트")
    void checkValidItemAmountNumberFormatExceptionTest() {
        assertThrows(NumberFormatException.class, () -> inputOrderItemAmountDto = new InputOrderItemAmountDto("a"));
        assertThrows(NumberFormatException.class, () -> inputOrderItemAmountDto = new InputOrderItemAmountDto("1.1"));
    }
}