package de.garrafao.phitag.domain.core;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class PageRequestBuilder {

    private int page;
    private int size;

    private String order;

    public PageRequestBuilder withPage(int page) {
        this.page = page;
        return this;
    }

    public PageRequestBuilder withSize(int size) {
        this.size = size;
        return this;
    }

    public PageRequestBuilder withOrder(String order) {
        this.order = order;
        return this;
    }

    public PageRequest build() {
        if (order == null || order.isEmpty()) {
            return PageRequest.of(page, size);
        }
        return PageRequest.of(page, size, Sort.by(order));
    }
    
}
