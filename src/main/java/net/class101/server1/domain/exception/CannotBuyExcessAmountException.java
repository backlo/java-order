package net.class101.server1.domain.exception;

public class CannotBuyExcessAmountException extends RuntimeException {
    public CannotBuyExcessAmountException() {
        super("현재 재고가 남은 물량을 넘게 살 수 없습니다. 다시 입력해주세요!");
    }
}
