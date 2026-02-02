package com.itransform.user_service.controller;

import com.itransform.user_service.dto.CarRequestDto;
import com.itransform.user_service.dto.CarResponseDto;
import com.itransform.user_service.dto.UserRequestDto;
import com.itransform.user_service.dto.UserResponseDto;
import com.itransform.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/profile/add")
    public ResponseEntity<UserResponseDto> createProfile(@RequestBody @Valid UserRequestDto dto, @RequestHeader("Authorization") String token) {
        UserResponseDto user = userService.createProfile(dto, token);
        return ResponseEntity.ok(user);
    }
    @PatchMapping("/profile/update")
    public ResponseEntity<UserResponseDto> updateProfile(@RequestBody @Valid UserRequestDto dto, @RequestHeader("Authorization") String token) {
        UserResponseDto user = userService.updateProfile(dto,token);
        return ResponseEntity.ok(user);
    }

   @GetMapping("/profile")
   public ResponseEntity<UserResponseDto> getUserProfile(@RequestHeader("Authorization") String token) {
       return ResponseEntity.ok(userService.getUserByEmail(token));
   }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/id-from-token")
    public ResponseEntity<String> getUserIdFromToken(@RequestHeader("Authorization") String token) {
        String userId = userService.getUserIdFromToken(token);
        return ResponseEntity.ok(userId);
    }





    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable String id,
                                                      @RequestBody @Valid UserRequestDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted");
    }


    @PostMapping("/{userId}/addcar")
    public ResponseEntity<CarResponseDto> addCar(@PathVariable String userId,
                                                 @RequestBody @Valid CarRequestDto dto) {
        return ResponseEntity.ok(userService.addCar(userId, dto));
    }

    @GetMapping("/{userId}/cars")
    public ResponseEntity<List<CarResponseDto>> getCarsByUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getCarsByUser(userId));
    }

    @PatchMapping ("/{userId}/cars/{carId}")
    public ResponseEntity<CarResponseDto> updateCar(@PathVariable String userId,
                                                    @PathVariable String carId,
                                                    @RequestBody @Valid CarRequestDto dto) {
        return ResponseEntity.ok(userService.updateCar(userId, carId, dto));
    }

    @DeleteMapping("/{userId}/cars/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable String userId,
                                          @PathVariable String carId) {
        userService.deleteCar(userId, carId);
        return ResponseEntity.ok("Car deleted");

    }

    @GetMapping("/{id}/email")
    public ResponseEntity<String> getUserEmail(@PathVariable String id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(user.getEmail());
    }
    @GetMapping("/car-id")
    public ResponseEntity<String> getCarIdByCarNumber(@RequestParam String email,
                                                    @RequestParam String carNumber) {
        return ResponseEntity.ok(userService.getCarIdByEmailAndCarNumber(email, carNumber));
    }

    @GetMapping("/allusers")
    public  ResponseEntity<List<UserResponseDto>> getalluser(){

        return ResponseEntity.ok(userService.getAllUsers());
    }
}