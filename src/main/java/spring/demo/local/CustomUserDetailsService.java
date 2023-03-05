package spring.demo.local;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.demo.local.author.User;
import spring.demo.local.author.UserRepository;

import java.util.List;
import java.util.Optional;

import static spring.demo.local.item.ItemService.ur;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = ur.findById(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> auth = AuthorityUtils.createAuthorityList(user.get().getRole().getName());
        return new org.springframework.security.core.userdetails.User(username, user.get().getPassword(), true, true, true, true, auth);
    }

}

