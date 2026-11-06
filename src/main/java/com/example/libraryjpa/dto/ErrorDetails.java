package com.example.libraryjpa.dto;

import java.util.Map;

public record ErrorDetails(int status, String code, String message, Map<String, String> fields) {
}
