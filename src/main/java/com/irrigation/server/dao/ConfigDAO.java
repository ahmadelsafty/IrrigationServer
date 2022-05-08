package com.irrigation.server.dao;

import com.irrigation.server.model.Config;

public interface ConfigDAO {
    int update(Config config);
    Config get();
}
