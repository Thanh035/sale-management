package com.example.demo.controllers;

import com.example.demo.domain.entities.District;
import com.example.demo.domain.entities.Province;
import com.example.demo.domain.entities.Ward;
import com.example.demo.repositories.DistrictRepository;
import com.example.demo.repositories.ProvinceRepository;
import com.example.demo.repositories.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
public class AddressController {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private WardRepository wardRepository;

    @GetMapping("/provinces")
    public List<Province> getAllProvinces() {
        return provinceRepository.findAll();
    }

    @GetMapping("/districts/{provinceCode}")
    public List<District> getDistrictsByProvinceCode(@PathVariable String provinceCode) {
        return districtRepository.findByProvinceCode(provinceCode);
    }

    @GetMapping("/wards/{districtCode}")
    public List<Ward> getWardsByDistrictCode(@PathVariable String districtCode) {
        return wardRepository.findByDistrictCode(districtCode);
    }

}
