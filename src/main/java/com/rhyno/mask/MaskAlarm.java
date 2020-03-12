package com.rhyno.mask;

import com.rhyno.mask.model.RemainStat;
import com.rhyno.mask.model.Store;
import com.rhyno.mask.model.StoreSaleResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class MaskAlarm {
    private static final Predicate<RemainStat> BIGGER_THAN_100 = RemainStat.PLENTY::equals;
    private static final Predicate<RemainStat> BETWEEN_30_AND_100 = RemainStat.SOME::equals;
    private static final Predicate<RemainStat> BETWEEN_2_AND_30 = RemainStat.FEW::equals;

    @Value("${mask.stores}")
    private String[] STORES;

    private final Predicate<String> FILTER_STORES = name -> Arrays.asList(STORES).contains(name);

    public void notify(Mono<StoreSaleResult> response) {
        response.subscribe(storeSaleResult -> {
            List<Store> stores = storeSaleResult.getStores().stream()
                    .filter(store -> FILTER_STORES.test(store.getName()))
//                    .filter(store -> BETWEEN_2_AND_30.test(store.getStatus()))
                    .filter(store -> BIGGER_THAN_100.or(BETWEEN_30_AND_100).test(store.getStatus()))
                    .collect(Collectors.toList());

            this.alarm(stores);
        });
    }

    private void alarm(List<Store> stores) {
        if (stores.isEmpty()) {
            return;
        }

        Toolkit.getDefaultToolkit().beep();
        stores.forEach(System.out::println);
    }
}
