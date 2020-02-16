package net.class101.server1.domain.exception;

public class CannotBuyDuplicateClassException extends RuntimeException {
    public CannotBuyDuplicateClassException() {
        super("클래스 종류는 중복 결제가 불가능 합니다.");
    }
}
