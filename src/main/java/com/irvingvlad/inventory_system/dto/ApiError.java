package com.irvingvlad.inventory_system.dto;

import java.time.Instant;
import java.util.Map;

public record ApiError(int status, String mensaje, Map<String, String> errores, Instant timestamp) {
    public ApiError(int status, String mensaje) {
        this(status, mensaje, null, Instant.now());
    }
}
