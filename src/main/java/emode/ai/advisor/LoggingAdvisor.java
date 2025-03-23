package emode.ai.advisor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.AdvisedRequest;
import org.springframework.ai.chat.client.advisor.api.AdvisedResponse;
import org.springframework.ai.chat.client.advisor.api.StreamAroundAdvisorChain;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;


/**
 * @author zhl
 * @create 2025/1/21 15:17
 * @description 日志记录
 */
public class LoggingAdvisor extends SimpleLoggerAdvisor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAdvisor.class);

    /**
     * 流式日志记录
     */
    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
//        logger.info("BEFORE: {}", advisedRequest);
        Flux<AdvisedResponse> responseFlux = chain.nextAroundStream(advisedRequest);
        return new MessageAggregator().aggregateAdvisedResponse(responseFlux,
                advisedResponse -> {
                });
//        logger.info("AFTER: {}", advisedResponse)
    }
}
