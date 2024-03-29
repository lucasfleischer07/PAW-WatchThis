package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class PawUserDetailsService implements UserDetailsService {

    private final UserService us;

    private Pattern BCRYP_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    @Autowired
    public PawUserDetailsService(final UserService us){
        this.us=us;
    }

    @Override
    public PawUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = us.findByEmail(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException("Not user with email " + username);
        }

        final Set<GrantedAuthority> authorities = new HashSet<>();
       authorities.add(new SimpleGrantedAuthority(username));
       if(user.get().getRole().equals("admin")){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
       }
       authorities.add(new SimpleGrantedAuthority("ROLE_USER"));


        return new PawUserDetails(username,user.get().getPassword(),authorities);
    }
}
