package com.xmerge.chainHandler.chain;

import com.xmerge.base.context.ApplicationContextHolder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 责任链上下文
 * @author Xmerge
 */
public class ChainHandlerContext<T> implements CommandLineRunner {

    private final Map<String, List<IChainHandler<T>>> chainHandlerContainer = new HashMap<>();

    public void handle(String mark, T requestParam) {
        List<IChainHandler<T>> chainHandlers = chainHandlerContainer.get(mark);
        if (CollectionUtils.isEmpty(chainHandlers)) {
            throw new RuntimeException(String.format("[%s] 责任链标识未指定.", mark));
        }
        chainHandlers.forEach(each -> each.handler(requestParam));
    }

    /**
     * 通过责任链组件标识获取责任链组件
     * 实现了CommandLineRunner接口，项目启动时执行
     */
    @Override
    public void run(String... args) throws Exception {
        // 查找IChainHandler类型的Bean
        Map<String, IChainHandler> chainFilterMap = ApplicationContextHolder.getBeansOfType(IChainHandler.class);
        chainFilterMap.forEach((name, bean) -> {
            List<IChainHandler<T>> chainHandlers = chainHandlerContainer.get(bean.mark());
            if (CollectionUtils.isEmpty(chainHandlers)) {
                chainHandlers = new ArrayList<>(); // 初始化
            }
            chainHandlers.add(bean);
            // 排序
            List<IChainHandler<T>> sortedChainHandler = chainHandlers.stream()
                    .sorted(Comparator.comparing(Ordered::getOrder))
                    .toList();
            chainHandlerContainer.put(bean.mark(), sortedChainHandler);
        });
    }
}
