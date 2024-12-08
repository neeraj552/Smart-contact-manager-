package com.scm.scm20.Implementation;

import com.scm.scm20.entities.User;
import com.scm.scm20.repositories.UserRepo;
import com.scm.scm20.services.UserService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HttpMessageConverters messageConverters;

    @Override
    public User saveUser(User user) {
        //generate User Id
        String userId= UUID.randomUUID().toString();
        user.setUserId(userId);
        //To Encode password
        //user.setProfilePic(userId);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //set the user role

        user.setRoleLists(List.of("AppConstant.ROLE_USER"));
        logger.info(user.getProvider().toString());
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> upateUser(User user) {
       User user2= userRepo.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user.setProviderUserId(user.getUserId());
        // save user data
        User save=userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String Id) {
        User user2= userRepo.findById(Id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
         userRepo.delete(user2);
    }

    @Override
    public boolean isUserExistsbyEmail(String emailId) {
      User user=  userRepo.findById(emailId).orElse(null);
        return user != null ? true : false;
    }

    @Override
    public boolean isUserExists(String UserId) {
        User user2= userRepo.findById(UserId).orElse(null);
        return user2 != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(null);
    }
}
