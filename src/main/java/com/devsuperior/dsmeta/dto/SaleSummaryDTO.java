package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.entities.Sale;

import java.math.BigDecimal;

public class SaleSummaryDTO{
    private String name;
    private Double total;

    public SaleSummaryDTO(String name, Double total) {
        this.name = name;
        this.total = total;
    }

    public SaleSummaryDTO(Sale entity){
        name = entity.getSeller().getName();
        total = entity.getAmount();
    }

    public String getName() {
        return name;
    }

    public Double getTotal() {
        return total;
    }
}
