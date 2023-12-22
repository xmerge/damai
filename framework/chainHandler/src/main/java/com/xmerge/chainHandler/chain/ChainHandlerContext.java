package com.xmerge.chainHandler.chain;

import com.xmerge.base.context.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 责任链上下文
 * @author Xmerge
 */
@Slf4j
public class ChainHandlerContext<T> implements CommandLineRunner {

    private final Map<String, List<IChainHandler<T>>> chainHandlerContainer = new HashMap<>();

    public void handle(String mark, T requestParam) {
        List<IChainHandler<T>> chainHandlers = chainHandlerContainer.get(mark);
        if (CollectionUtils.isEmpty(chainHandlers)) {
            throw new RuntimeException(String.format("[%s] 责任链标识未指定.", mark));
        }
        chainHandlers.forEach(each -> each.handle(requestParam));
    }

    /**
     * 通过责任链组件标识获取责任链组件
     * 实现了CommandLineRunner接口，项目启动时执行
     */
    @Override
    public void run(String... args) throws Exception {
        // 查找IChainHandler类型的Bean
        Map<String, IChainHandler> chainFilterMap = ApplicationContextHolder.getBeansOfType(IChainHandler.class);
        log.info("查找到责任链组件: {}", chainFilterMap.keySet());
        chainFilterMap.forEach((name, bean) -> {
            List<IChainHandler<T>> chainHandlers = chainHandlerContainer.get(bean.mark());
            List<IChainHandler<T>> newChainHandlers = new ArrayList<>();
            if (!CollectionUtils.isEmpty(chainHandlers)) {
                newChainHandlers.addAll(chainHandlers);
            }
            newChainHandlers.add(bean);
            // 排序
            List<IChainHandler<T>> sortedChainHandler = newChainHandlers.stream()
                    .sorted(Comparator.comparing(Ordered::getOrder))
                    .toList();
            chainHandlerContainer.put(bean.mark(), sortedChainHandler);
            log.info("责任链组件: {} - {} 已添加.", bean.mark(), name);
        });
//        log.info(chainHandlerContainer.toString());
    }
}
