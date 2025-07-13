package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Map;

@RestController // Combination of @Controller and @ResponseBody, returns JSON
@RequestMapping("/api/v1/users") // Base path for all endpoints in this controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserEntity>> searchUsers(@RequestParam("q") String query) {
        List<UserEntity> result = userService.searchUsers(query);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/v1/users : Get all users
     */
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * GET /api/v1/users/{id} : Get a single user by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok) // If user exists, return 200 OK
                .orElse(ResponseEntity.notFound().build()); // Otherwise, return 404 Not Found
    }

    /**
     * POST /api/v1/users : Create a new user
     */
    @PostMapping
    public ResponseEntity<UserEntity> createUser(@RequestBody UserEntity user) {
        UserEntity createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED); // Return 201 Created
    }

    /**
     * PATCH /api/v1/users : Update  user
     */
    @PatchMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Long id, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUserById(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/validate-duplicate")
    public ResponseEntity<?> validateDuplicateCreate(@RequestParam ValidateDuplicateType validateDuplicateType, @RequestParam String value) {
        boolean isDuplicated = userService.validateDuplicateCreate(validateDuplicateType,value);
        if (isDuplicated) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("valid", false, "message", validateDuplicateType + " already in use."));
        } else {
            return ResponseEntity
                    .ok(Map.of("valid", true, "message", validateDuplicateType +  " is available."));
        }
    }

    @GetMapping("/validate-duplicate/{id}")
    public ResponseEntity<?> validateDuplicateUpdate(@PathVariable Long id, @RequestParam ValidateDuplicateType validateDuplicateType, @RequestParam String value) {
        boolean isDuplicated = userService.validateDuplicateUpdate(id, validateDuplicateType, value);
        if (isDuplicated) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("valid", false, "message",validateDuplicateType + " already in use."));
        } else {
            return ResponseEntity
                    .ok(Map.of("valid", true, "message", validateDuplicateType +  " is available."));
        }
    }
}

