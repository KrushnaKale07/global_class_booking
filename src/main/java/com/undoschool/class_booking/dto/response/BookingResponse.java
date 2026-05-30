package com.undoschool.class_booking.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingResponse {

    private Long bookingId;

    private Long offeringId;

    private String offeringName;
}