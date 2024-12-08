package com.scm.scm20.services;
import java.util.List;
import java.util.Optional;

import com.scm.scm20.entities.User;
public interface UserService {
  User saveUser(User user);
  Optional<User> getUserById(String id);//it checks user exist or not yeah we don't need if and else
  Optional<User> upateUser(User user);
  void deleteUser(String Id);
  boolean isUserExistsbyEmail(String emailId);
  boolean isUserExists(String UserId);
  List<User> getAllUsers();
  User getUserByEmail(String email);
  //I can add more methods here
}
