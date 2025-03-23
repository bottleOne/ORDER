package com._9._ss23.order;

import com._9._ss23.order.vo.OrderResponse;
import com._9._ss23.order.vo.ProductOrderRequest;
import com._9._ss23.order.process.ProductOrderProcess;
import com._9._ss23.product.domain.Product;
import com._9._ss23.product.service.ProductService;
import com._9._ss23.sale.service.ProductSaleServiceImpl;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class testOrderProcess {

    @Autowired
    ProductOrderProcess orderProcess;

    @Autowired
    ProductSaleServiceImpl saleService;

    @Autowired
    ProductService productService;

    @Autowired
    TransactionTemplate transactionTemplate;

    @Test
    public void testProcess(){
        List<ProductOrderRequest> requests = new ArrayList<>();

        requests.add(new ProductOrderRequest(213341L, 2));
        requests.add(new ProductOrderRequest(377169L, 2));

        List<OrderResponse> orderResponses = orderProcess.orderProcess(requests);
        saleService.sale(orderResponses);

        orderResponses.stream().forEach(o ->{
            ProductOrderRequest productOrderRequest = requests.stream().filter(r -> r.getItemNumber().equals(o.getItemId())).findFirst().get();
            assertNotEquals(o.getItemId(), productOrderRequest.getItemNumber());
        } );
    }

    @Test
    public void 동시성_이슈_test() throws InterruptedException {
        class WaitingWorker implements Runnable{
            private CountDownLatch readyThreadCounter;
            private CountDownLatch callingThreadBlocker;
            private CountDownLatch completedThreadCounter;
            private ProductOrderRequest request;

            public WaitingWorker(
                    ProductOrderRequest request,
                    CountDownLatch readyThreadCounter,
                    CountDownLatch callingThreadBlocker,
                    CountDownLatch completedThreadCounter) {
                this.request = request;
                this.readyThreadCounter = readyThreadCounter;
                this.callingThreadBlocker = callingThreadBlocker;
                this.completedThreadCounter = completedThreadCounter;
            }

            @Override
            public void run() {
                readyThreadCounter.countDown();

                try {
                    callingThreadBlocker.await();
                    transactionTemplate.execute((status->{
                        List<OrderResponse> responses = orderProcess.orderProcess(List.of(request));
                        saleService.sale(responses);
                        return null;
                    }));

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }finally {
                    completedThreadCounter.countDown();
                }
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(200);
        CountDownLatch countDownLatch = new CountDownLatch(10); // 준비 카운트
        CountDownLatch countDownLatch1 = new CountDownLatch(1); // 요청 카운트
        CountDownLatch countDownLatch2 = new CountDownLatch(10); //완료카운트
        int productQ = productService.getProduct(213341L).getProductQuantity();
        ProductOrderRequest request = new ProductOrderRequest(213341L, 1);


        Runnable task = new WaitingWorker(
                request,countDownLatch, countDownLatch1, countDownLatch2);

        for(int i=0; i<10; i++){
            executorService.submit(task);
        }
        countDownLatch.await();
        countDownLatch1.countDown();
        countDownLatch2.await();

        executorService.shutdown();

        Product product = productService.getProduct(213341L);

        assertEquals(product.getProductQuantity(), productQ-10);
    }

    @Test
    public void testConcurrentReduceProductCount() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        Product product = productService.getProduct(213341L);
        int initialStock = product.getProductQuantity();

        for (int i = 0; i < threadCount; i++) {

            executorService.execute(() -> {
                try {
                    productService.reduceProductCount(213341L, 1);
                }catch (ObjectOptimisticLockingFailureException e){

                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(); // 모든 스레드가 종료될 때까지 대기
        executorService.shutdown();

        Product updatedProduct = productService.getProduct(product.getId());
        assertEquals(initialStock - threadCount, updatedProduct.getProductQuantity()); // 🔥 최종 재고 검증
    }

}
