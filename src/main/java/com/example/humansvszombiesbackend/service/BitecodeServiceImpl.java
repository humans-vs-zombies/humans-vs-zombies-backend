package com.example.humansvszombiesbackend.service;

import org.springframework.stereotype.Service;

@Service
public class BitecodeServiceImpl implements BiteCodeService {

    @Override
    public String generate() {
        return "4567";
    }

}
