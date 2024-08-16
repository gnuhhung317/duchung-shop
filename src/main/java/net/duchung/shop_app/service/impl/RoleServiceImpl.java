package net.duchung.shop_app.service.impl;

import net.duchung.shop_app.dto.RoleDto;
import net.duchung.shop_app.entity.Role;
import net.duchung.shop_app.exception.DataNotFoundException;
import net.duchung.shop_app.repository.RoleRepository;
import net.duchung.shop_app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleRepository roleRepository;
    @Override
    public List<RoleDto> getRoles() {
        return roleRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public RoleDto getRoleById(Long id) {
        return toDto(roleRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Role with id "+id+" not found")));
    }
    private RoleDto toDto(Role role){
        return RoleDto.builder().id(role.getId()).name(role.getName()).build();
    }
    private Role toEntity(RoleDto roleDto){
        return roleRepository.findById(roleDto.getId()).orElseThrow(() -> new DataNotFoundException("Role not found"));
    }
}
