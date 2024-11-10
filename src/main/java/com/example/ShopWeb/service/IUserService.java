package com.example.ShopWeb.service;

import com.example.ShopWeb.DTO.UserDTO;
import com.example.ShopWeb.Exeption.DataNotFoundException;
import com.example.ShopWeb.Model.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;
    String login(String phoneNumber, String password) throws DataNotFoundException, Exception;

}
