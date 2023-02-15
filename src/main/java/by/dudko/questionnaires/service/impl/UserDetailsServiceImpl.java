package by.dudko.questionnaires.service.impl;

import by.dudko.questionnaires.dto.user.UserDetailsImpl;
import by.dudko.questionnaires.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(UserDetailsImpl::of)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }
}
