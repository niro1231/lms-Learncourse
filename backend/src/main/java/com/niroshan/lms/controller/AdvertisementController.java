package com.niroshan.lms.controller;


import com.niroshan.lms.dto.response.AdvertisementResponse;
import com.niroshan.lms.service.AdvertisementService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {
    private final AdvertisementService service;
    public AdvertisementController(
            AdvertisementService service
    ){
        this.service = service;
    }
    @PostMapping(
            value="/upload",
            consumes="multipart/form-data"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public AdvertisementResponse upload(
            @RequestParam String title,
            @RequestParam MultipartFile image
    ) throws IOException{
        return service.upload(
                title,
                image
        );
    }
    @GetMapping
    public List<AdvertisementResponse> getAll(){
        return service.getAdvertisements();
    }
}