package net.class101.server1.console.dto;

import net.class101.server1.console.dto.exception.NoMatchTopMenuInitialsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputTopMenuDtoTest {

    private InputTopMenuDto inputTopMenuDto;

    @Test
    @DisplayName("top menu 이니셜 형식이 맞는지 테스트")
    void checkValidIdTest() {
        inputTopMenuDto = new InputTopMenuDto("o");
        assertThat(inputTopMenuDto.getTopMenu()).isEqualTo("o");

        inputTopMenuDto = new InputTopMenuDto("q");
        assertThat(inputTopMenuDto.getTopMenu()).isEqualTo("q");
    }

    @Test
    @DisplayName("top menu 이니셜 외 다른 문자나 숫자 입력하면 예외 처리 하는 테스트")
    void checkExitTest() {
        assertThrows(NoMatchTopMenuInitialsException.class, () -> inputTopMenuDto = new InputTopMenuDto("a"));
        assertThrows(NoMatchTopMenuInitialsException.class, () -> inputTopMenuDto = new InputTopMenuDto("1"));
        assertThrows(NoMatchTopMenuInitialsException.class, () -> inputTopMenuDto = new InputTopMenuDto("1.0"));
    }
}