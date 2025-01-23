package emode.ai.function.service;

import java.util.function.Function;

/**
 * @author zhl
 * @create 2025/1/23 13:30
 */
public class FunctionService implements Function<FunctionService.TestRequest, FunctionService.TestDetail> {

    @Override
    public TestDetail apply(TestRequest testRequest) {
        return new TestDetail(testRequest.orderId(), testRequest.name(), "机票详细信息：南京-》妙瓦底");
    }

    /**
     * 测试请求
     *
     * @param orderId
     * @param name
     */
    public record TestRequest(Long orderId, String name) {
    }

    /**
     * 测试返回
     *
     * @param orderId
     * @param orderName
     * @param desc
     */
    public record TestDetail(Long orderId, String orderName, String desc) {
    }

}
