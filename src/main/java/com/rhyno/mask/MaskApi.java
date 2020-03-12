package com.rhyno.mask;

import com.rhyno.mask.model.StoreSaleResult;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Component
public class MaskApi {
    private static final String MASK_BASE_URL = "https://8oi9s0nnth.apigw.ntruss.com";

    private WebClient client;

    @PostConstruct
    public void init() {
        this.client = WebClient.builder()
                .baseUrl(MASK_BASE_URL)
                .build();
    }

    public Mono<StoreSaleResult > getStores(String cityGuName) {
        return this.client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/corona19-masks/v1/storesByAddr/json")
                        .queryParam("address", cityGuName)
                        .build())
                .retrieve()
                .bodyToMono(StoreSaleResult.class);
    }
}
