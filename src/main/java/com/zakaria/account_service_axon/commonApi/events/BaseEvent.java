package com.zakaria.account_service_axon.commonApi.events;

import lombok.Getter;

public class BaseEvent<T> {
    @Getter private T id;
    public BaseEvent(T id) {
        this.id = id;
    }
}
