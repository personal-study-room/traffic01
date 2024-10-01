package com.onion.common.event;

public interface OnionListener<T> {
    void onEvent(T event);
}
