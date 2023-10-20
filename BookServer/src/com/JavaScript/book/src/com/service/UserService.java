package com.service;

import com.domain.*;
import java.util.*;
public interface UserService {
public User getByName(String name);
public User getByEmail(String email);
public boolean checkUser(User user);
public boolean addUser(User user);
}
