package com.accenture.model;

import java.time.LocalDateTime;

/**
 * Record representing an error response.
 *
 * @param time The time when the error occurred
 * @param type The type of the error
 * @param messsage The error message
 */
public record ResponseError(LocalDateTime time, String type, String messsage) {
}
