package com.optivem.eshop.systemtest.core.drivers;

/**
 * Utility for closing drivers safely.
 */
public class DriverCloser {

    public static void close(AutoCloseable... drivers) {
        for (var driver : drivers) {
            if (driver != null) {
                try {
                    driver.close();
                } catch (Exception e) {
                    // Log or handle if needed
                    System.err.println("Error closing driver: " + e.getMessage());
                }
            }
        }
    }
}

