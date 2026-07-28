package com.niroshan.lms.service;


import com.niroshan.lms.dto.request.LoginRequest;
import com.niroshan.lms.dto.request.RegisterRequest;
import com.niroshan.lms.dto.response.LoginResponse;
import com.niroshan.lms.dto.response.UserResponse;
import com.niroshan.lms.entity.User;
import com.niroshan.lms.exception.ResourceNotFoundException;
import com.niroshan.lms.repository.UserRepository;
import com.niroshan.lms.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final FileStorageService fileStorageService;

    public UserService(
            UserRepository userRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            FileStorageService fileStorageService
    ){
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.fileStorageService = fileStorageService;
    }
    // REGISTER
    public UserResponse registerUser(
            RegisterRequest request
    ) throws IOException {
        User user = new User();
        user.setName(
                request.getName()
        );
        user.setEmail(
                request.getEmail()
        );
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );
        user.setRole(
                request.getRole()
        );
        if(request.getProfileImage() != null &&
                !request.getProfileImage().isEmpty()){
            if(!request.getProfileImage()
                    .getContentType()
                    .startsWith("image/")){
                throw new RuntimeException(
                        "Only image files allowed"
                );
            }
            String imageUrl =
                    fileStorageService.saveImage(
                            request.getProfileImage()
                    );
            user.setProfileImageUrl(
                    imageUrl
            );
        }
        User savedUser =
                userRepository.save(user);
        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole().name(),
                savedUser.getProfileImageUrl()
        );
    }

    // LOGIN
    public LoginResponse loginUser(
            LoginRequest request
    ){
        // Spring Security authentication
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user =
                userRepository.findByEmail(
                                request.getEmail()
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );
        String token =
                jwtUtil.generateToken(
                        user.getEmail(),
                        user.getRole().name()
                );
        return new LoginResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                token,
                user.getProfileImageUrl()
        );
    }
}