package com.ra.project5.model.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NoticeResponse {
    private String message;

    public NoticeResponse(String message) {
        this.message = message;
    }
}
