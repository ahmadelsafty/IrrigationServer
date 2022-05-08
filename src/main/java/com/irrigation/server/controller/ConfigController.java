package com.irrigation.server.controller;

import com.irrigation.server.dao.ConfigDAO;
import com.irrigation.server.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/config")
public class ConfigController {
    @Autowired
    private ConfigDAO configDAO;

    @GetMapping("/get")
    public Config getConfigurations() {
        return configDAO.get();
    }

    @PutMapping("/update")
    public Config updateConfigurations(@RequestBody Config config) {
        int updates = configDAO.update(config);
        Config temp = configDAO.get();
        return temp;
    }
}
