package com.accenture.model;

import java.time.LocalDateTime;

public record ResponseError(LocalDateTime time, String type, String messsage) {
}
