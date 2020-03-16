package com.rhyno.mask;

import com.rhyno.mask.model.RemainStat;
import com.rhyno.mask.model.Store;
import com.rhyno.mask.model.StoreSaleResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
@Component
public class MaskAlarm {
    private static final Predicate<RemainStat> BIGGER_THAN_100 = RemainStat.PLENTY::equals;
    private static final Predicate<RemainStat> BETWEEN_30_AND_100 = RemainStat.SOME::equals;
    private static final Predicate<RemainStat> BETWEEN_2_AND_30 = RemainStat.FEW::equals;
    private static final Predicate<RemainStat> BETWEEN_EMPTY = RemainStat.EMPTY::equals;

    @Value("${mask.stores}")
    private String[] STORES;

    private final Predicate<String> FILTER_STORES = name -> Arrays.asList(STORES).contains(name);

    public void notify(Mono<StoreSaleResult> response) {
        response.subscribe(storeSaleResult -> Flux.fromIterable(storeSaleResult.getStores())
                .filter(store -> FILTER_STORES.test(store.getName()))
//                    .filter(store -> BETWEEN_EMPTY.test(store.getStatus()))
                .filter(store -> BIGGER_THAN_100.or(BETWEEN_30_AND_100).test(store.getStatus()))
                .doOnNext(s -> log.info(s.toString()))
                .subscribe(this::alarm));
    }

    private void alarm(Store store) {
        Optional.ofNullable(store).ifPresent(s -> Toolkit.getDefaultToolkit().beep());
    }
}
