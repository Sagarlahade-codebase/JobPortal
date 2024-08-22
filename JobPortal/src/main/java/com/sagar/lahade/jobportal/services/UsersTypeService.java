package com.sagar.lahade.jobportal.services;

import org.springframework.stereotype.Service;

import com.sagar.lahade.jobportal.entity.UsersType;
import com.sagar.lahade.jobportal.repository.UsersTypeRepository;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    public List<UsersType> getAll() {
        return usersTypeRepository.findAll();
    }
}
