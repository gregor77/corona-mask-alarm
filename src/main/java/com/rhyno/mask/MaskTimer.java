package com.rhyno.mask;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Slf4j
@Component
public class MaskTimer {
    private static final int ONE_MINUTE_MILLI_SECONDS = 60000;

    @Value("${mask.interval.time.unit.minute}")
    private int INTERVAL_TIME_UNIT_MINUTE;

    @Value("${mask.address}")
    private String ADDRESS;

    private final MaskApi maskApi;
    private final MaskAlarm maskAlarm;

    public MaskTimer(MaskApi maskApi, MaskAlarm maskAlarm) {
        this.maskApi = maskApi;
        this.maskAlarm = maskAlarm;
    }

    @PostConstruct
    public void start() {
        checkMask("tick=start");

        Flux.interval(Duration.ofMillis(INTERVAL_TIME_UNIT_MINUTE * ONE_MINUTE_MILLI_SECONDS))
                .map(input -> "tick=" + input)
                .retry(1)
                .elapsed()
                .subscribe((tuple) -> checkMask(tuple.getT2()), System.err::println);
    }

    private void checkMask(String tick) {
        log.info(tick);

        maskAlarm.notify(maskApi.getStores(ADDRESS));
    }
}
