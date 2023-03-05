package spring.demo.local.role;

import java.util.HashSet;
import java.util.Set;

public class RoleRepository {

    private final Set<ch.supsi.webapp.web.role.Role> roles = new HashSet<>();

    public void save(ch.supsi.webapp.web.role.Role role_admin) {
        roles.add(role_admin);
    }
}
