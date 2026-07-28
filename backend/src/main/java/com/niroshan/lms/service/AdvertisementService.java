package com.niroshan.lms.service;

import com.niroshan.lms.dto.response.AdvertisementResponse;
import com.niroshan.lms.entity.Advertisement;
import com.niroshan.lms.repository.AdvertisementRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class AdvertisementService {


    private final AdvertisementRepository repository;
    private final FileStorageService fileStorageService;


    public AdvertisementService(
            AdvertisementRepository repository,
            FileStorageService fileStorageService
    ){
        this.repository = repository;
        this.fileStorageService = fileStorageService;
    }



    public AdvertisementResponse upload(
            String title,
            MultipartFile image
    ) throws IOException{


        String imageUrl =
                fileStorageService.saveImage(image);


        Advertisement ad =
                new Advertisement();


        ad.setTitle(title);
        ad.setImageUrl(imageUrl);
        ad.setActive(true);


        Advertisement saved =
                repository.save(ad);



        return new AdvertisementResponse(
                saved.getId(),
                saved.getTitle(),
                saved.getImageUrl()
        );

    }



    public List<AdvertisementResponse> getAdvertisements(){

        return repository.findByActiveTrue()
                .stream()
                .map(ad ->
                        new AdvertisementResponse(
                                ad.getId(),
                                ad.getTitle(),
                                ad.getImageUrl()
                        )
                )
                .toList();

    }


}