package com.niroshan.lms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdvertisementResponse {
    private Long id;
    private String title;
    private String imageUrl;
}