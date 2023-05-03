package com.yigiter.librarymanagement.service;



import com.yigiter.librarymanagement.domain.Role;
import com.yigiter.librarymanagement.domain.enums.RoleType;
import com.yigiter.librarymanagement.exception.ErrorMessage;
import com.yigiter.librarymanagement.exception.ResourceNotFoundException;
import com.yigiter.librarymanagement.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByType(RoleType roleType) {
    Role role= roleRepository.findByType(roleType).orElseThrow(
                ()->new ResourceNotFoundException(String.format(ErrorMessage.ROLE_NOT_FOUND_EXCEPTION,roleType))
        );
        return  role;
    }
}
