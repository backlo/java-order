package net.class101.server1.domain.exception;

public class SoldOutException extends RuntimeException {
    public SoldOutException() {
        super("재고가 없습니다. 다시 선택 해주세요!");
    }
}
