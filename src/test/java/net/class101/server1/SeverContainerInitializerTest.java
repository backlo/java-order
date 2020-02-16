package net.class101.server1;

import net.class101.server1.domain.Item;
import net.class101.server1.service.OrderService;
import net.class101.server1.service.exception.OrderServiceException;
import net.class101.server1.support.context.ContextLoaderListener;
import net.class101.server1.util.OrderRequestThread;
import net.class101.server1.util.ThreadExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SeverContainerInitializerTest {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Test
    @DisplayName("멀티쓰레드 요청으로 정상 작동 및 SoldOutException 발생 테스트")
    void synchronizedExceptionTest() {
        ContextLoaderListener contextLoaderListener = new ContextLoaderListener();
        contextLoaderListener.contextInitialized();

        OrderService orderService = new OrderService();

        ThreadExceptionHandler handler = new ThreadExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);

        for (int i = 0; i < 7; i++) {
            Map<Item, Integer> orderList = new HashMap<>();
            executorService.execute(new OrderRequestThread(orderList, orderService, 60538, 1, executorService));
        }

        assertNull(handler.getErrorMessage());

        for (int i = 0; i < 32; i++) {
            Map<Item, Integer> orderList = new HashMap<>();
            executorService.execute(new OrderRequestThread(orderList, orderService, 58395, 1, executorService));
        }
        while (!executorService.isTerminated()) {}

        assertThat(handler.getErrorMessage()).isEqualTo("재고가 없습니다. 다시 선택 해주세요!");
    }
}