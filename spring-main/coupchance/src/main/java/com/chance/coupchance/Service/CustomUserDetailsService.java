package com.chance.coupchance.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.chance.coupchance.Entites.User;
import com.chance.coupchance.Repos.User_Repos;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final User_Repos userRepository;

    public CustomUserDetailsService(User_Repos userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Utilisation de Optional pour éviter NullPointerException
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + username));
        
        // Retourner l'utilisateur qui implémente déjà UserDetails
        return user;
    }
}
