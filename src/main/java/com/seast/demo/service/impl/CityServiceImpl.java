package com.seast.demo.service.impl;

import com.seast.demo.domain.City;
import com.seast.demo.service.CityService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityServiceImpl implements CityService {

    // 模拟数据库，存储 City 信息
    private static Map<Long, City> CITY_DB = new HashMap<>();

    public List<City> findAllCity() {
        return new ArrayList<>(CITY_DB.values());
    }

    public City findCityById(Long id) {
        return CITY_DB.get(id);
    }

    @Override
    public Long saveCity(City city) {
        city.setId(CITY_DB.size() + 1L);
        CITY_DB.put(city.getId(), city);
        return city.getId();
    }

    @Override
    public Long updateCity(City city) {
        CITY_DB.put(city.getId(), city);
        return city.getId();
    }

    @Override
    public Long deleteCity(Long id) {
        CITY_DB.remove(id);
        return id;
    }

}
