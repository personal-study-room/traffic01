package com.onion.common.event;

import java.io.IOException;

public interface OnionListener<T> {
    void onEvent(T event) throws IOException;
}
