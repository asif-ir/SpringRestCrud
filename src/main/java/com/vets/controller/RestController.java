package com.vets.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vets.dao.CarDao;
import com.vets.model.Car;
import com.vets.model.User;
import org.apache.log4j.Logger;
import com.vets.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vets.services.DataServices;
import sun.misc.BASE64Decoder;

import javax.ws.rs.HeaderParam;

@Controller
@RequestMapping("/user")
public class RestController implements Serializable {
    @Autowired
    DataServices dataServices;
    @Autowired
    CarDao carDao;
    static final Logger logger = Logger.getLogger(RestController.class);

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody
    Status addUser(@RequestBody User user) {
        try {
            dataServices.addEntity(user);
            return new Status(1, "User added Successfully !");

        } catch (Exception e) {
            e.printStackTrace();
            return new Status(0, "Error");
        }

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    User getUser(@PathVariable("id") long id) {
        User user = null;
        try {
            user = dataServices.getEntityById(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(user.getEmail());
        return user;
    }

    @RequestMapping(value = "username/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    User getUser(@PathVariable("username") String username) {
        User user = null;
        try {
            user = dataServices.getEntityByName(username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<User> getUsers() {
        List<User> userList = null;
        try {
            userList = dataServices.getEntityList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET, consumes = MediaType.TEXT_PLAIN_VALUE)
    public @ResponseBody
    Status deleteUser(@PathVariable("id") long id) {
        try {
            dataServices.deleteEntity(id);
            return new Status(1, "User deleted Successfully !");
        } catch (Exception e) {
            return new Status(0, e.toString());
        }
    }

    @RequestMapping(value = "auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Boolean login(@HeaderParam("Authorization") String authString) {
        String[] authParts = authString.split(" ");
        String authInfo = authParts[1];

        // Decode the data back to original string
        byte[] bytes = null;
        try {
            bytes = new BASE64Decoder().decodeBuffer(authInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String decodedAuth = new String(bytes);
        System.out.println(decodedAuth); //Username:Password

        String[] auth = decodedAuth.split(":");
        try {
            return dataServices.isAuthenticated(auth[0], auth[1]);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = "/user-cars/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Car> getMyCars(@PathVariable("username") String username) {
        List<Car> carList = null;
        try {
            carList = carDao.getMyCars(username);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return carList;
    }
}
