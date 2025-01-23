package emode.ai.function.method;

import emode.ai.function.service.FunctionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

/**
 * @author zhl
 * @create 2025/1/23 10:48
 */
@Slf4j
@Configuration
public class FunctionTools {

    @Bean
    @Description("获取机票预定详细信息")
    public Function<FunctionService.TestRequest, FunctionService.TestDetail> getBookingDetails() {
        return request -> {
            log.info("ai 接收到参数：{}", request);
            return new FunctionService.TestDetail(request.orderId(), request.name(), "机票详细信息：南京-》妙瓦底");
        };
    }
}
