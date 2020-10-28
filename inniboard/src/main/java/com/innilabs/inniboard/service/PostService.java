package com.innilabs.inniboard.service;

import com.innilabs.inniboard.repository.PostDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    
    @Autowired
    PostDao postDao;

    
}