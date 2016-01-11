package ua.com.juja.positiv.sqlcmd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ua.com.juja.positiv.sqlcmd.service.Service;

import java.util.List;

/**
 * Created by POSITIV on 11.01.2016.
 */
@RestController
public class RestService {

    @Autowired
    private Service service;

    @RequestMapping(value = "/menu/content", method = RequestMethod.GET)
    public List<String> menuItems() {
        return service.commandList();
    }
}
