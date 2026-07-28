package com.niroshan.lms.controller;

import com.niroshan.lms.dto.response.InstructorResponse;
import com.niroshan.lms.service.InstructorService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/instructors")
public class InstructorController {
    private final InstructorService instructorService;
    public InstructorController(
            InstructorService instructorService
    ){
        this.instructorService = instructorService;
    }
    @GetMapping
    public List<InstructorResponse> getInstructors(){
        return instructorService.getAllInstructors();
    }
}