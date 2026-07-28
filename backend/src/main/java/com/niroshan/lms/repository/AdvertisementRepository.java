package com.niroshan.lms.repository;

import com.niroshan.lms.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository
        extends JpaRepository<Advertisement,Long>{

    List<Advertisement> findByActiveTrue();

}