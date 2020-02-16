package net.class101.server1.console.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputOrderItemIdDtoTest {

    private InputOrderItemIdDto inputOrderItemIdDto;

    @Test
    @DisplayName("id 형식이 숫자가 맞는지 테스트")
    void checkValidIdTest() {
        inputOrderItemIdDto = new InputOrderItemIdDto("12345");
        assertThat(inputOrderItemIdDto.getItemId()).isEqualTo(12345);
    }

    @Test
    @DisplayName("빈칸 입력시 -1이 되는 테스")
    void checkExitTest() {
        inputOrderItemIdDto = new InputOrderItemIdDto("");
        assertThat(inputOrderItemIdDto.getItemId()).isEqualTo(-1);
    }

    @Test
    @DisplayName("id 형식이 정수 형식이 아닌 다른 형식이 오면 예외처리 하는 테스트")
    void checkValidIdExceptionTest() {
        assertThrows(NumberFormatException.class, () -> inputOrderItemIdDto = new InputOrderItemIdDto("asdf"));
        assertThrows(NumberFormatException.class, () -> inputOrderItemIdDto = new InputOrderItemIdDto("12345.0"));
    }
}