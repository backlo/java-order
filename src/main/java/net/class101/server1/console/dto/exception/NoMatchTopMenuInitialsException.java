package net.class101.server1.console.dto.exception;

public class NoMatchTopMenuInitialsException extends RuntimeException {
    public NoMatchTopMenuInitialsException() {
        super("잘못 입력하였습니다. 주문(o), 종료(q)만 적어주세요.");
    }
}
