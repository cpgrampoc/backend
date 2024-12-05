package org.uni.cpgram.model;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateMapper {
    public Date asDate(Long timestamp) {
        return timestamp != null ? new Date(timestamp) : null;
    }
}
