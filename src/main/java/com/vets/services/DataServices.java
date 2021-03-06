package com.vets.services;

import java.util.List;

import com.vets.model.User;

public interface DataServices {
    boolean addEntity(User user) throws Exception;
    User getEntityById(long id) throws Exception;
    User getEntityByName(String username) throws  Exception;
    List<User> getEntityList() throws Exception;
    boolean deleteEntity(long id) throws Exception;
    Long getUserId(String username) throws Exception;
    boolean isAuthenticated(String username, String password) throws Exception;
}
