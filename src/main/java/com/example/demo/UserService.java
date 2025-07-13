package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this as a service component
public class UserService {

    private final UserRepository userRepository;

    // Using constructor injection is a best practice
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> searchUsers(String term) {
        return userRepository.searchByTerm(term);
    }

    /**
     * Get all users.
     * @return a list of all users.
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get a single user by their ID.
     * @param id The ID of the user.
     * @return an Optional containing the user if found.
     */
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Create a new user.
     * @param user The user object to be created.
     * @return the saved user.
     */
    public UserEntity createUser(UserEntity user) {
        // You could add validation or other logic here
        return userRepository.save(user);
    }

    public UserEntity updateUserById(Long id, UserEntity user) {
        // You could add validation or other logic here
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setIdCard(user.getIdCard());
            existingUser.setDateOfBirth(user.getDateOfBirth());
            existingUser.setPosition(user.getPosition());
            existingUser.setRole(user.getRole());
            existingUser.setStartDate(user.getStartDate());
            existingUser.setEndDate(user.getEndDate());
            existingUser.setEmail(user.getEmail());
            existingUser.setPhoneNumber(user.getPhoneNumber());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public boolean validateDuplicateCreate(ValidateDuplicateType validateDuplicateType, String value) {
        boolean isDuplicated;
        switch (validateDuplicateType) {
            case ID_CARD:
                isDuplicated = userRepository.existsByIdCard(value);
                break;
            case PHONE_NUMBER:
                isDuplicated = userRepository.existsByPhoneNumber(value);
                break;
            case EMAIL:
                isDuplicated = userRepository.existsByEmail(value);
                break;
            default:
                isDuplicated = false;
                break;
        }
        return isDuplicated;
    }

    public boolean validateDuplicateUpdate(Long id, ValidateDuplicateType validateDuplicateType, String value) {
        boolean isDuplicated;
        switch (validateDuplicateType) {
            case ID_CARD:
                isDuplicated = userRepository.existsByIdCardAndIdNot(value, id);
                break;
            case PHONE_NUMBER:
                isDuplicated = userRepository.existsByPhoneNumberAndIdNot(value, id);
                break;
            case EMAIL:
                isDuplicated = userRepository.existsByEmailAndIdNot(value, id);
                break;
            default:
                isDuplicated = false;
                break;
        }
        return isDuplicated;
    }

}