package net.class101.server1.service.exception;

import org.springframework.dao.EmptyResultDataAccessException;

public class OrderServiceException extends RuntimeException {
    public OrderServiceException() {
        super("데이터가 없습니다. 다시 입력해주세요.");
    }

    public OrderServiceException(Exception e) {
        super(e.getMessage(), e.getCause());
    }
}
