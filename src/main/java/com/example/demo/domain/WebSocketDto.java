package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class WebSocketDto {

    private String cctvId; // cctv 번호
    private String status;
    private String vision;

    public WebSocketDto(String cctvId, String status, String vision) {
        this.cctvId = cctvId;
        this.status = status;
        this.vision = vision;
    }
}