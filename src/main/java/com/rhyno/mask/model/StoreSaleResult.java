package com.rhyno.mask.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreSaleResult {
    private String address;
    private int count;
    private List<Store> stores;
}
