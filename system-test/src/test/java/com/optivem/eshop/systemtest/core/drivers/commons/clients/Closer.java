package com.optivem.eshop.systemtest.core.drivers.commons.clients;

public class Closer {
    public static void close(AutoCloseable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
