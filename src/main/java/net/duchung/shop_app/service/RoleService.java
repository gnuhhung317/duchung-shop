package net.duchung.shop_app.service;

import net.duchung.shop_app.dto.RoleDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<RoleDto>getRoles();

    RoleDto getRoleById(Long id);
}
