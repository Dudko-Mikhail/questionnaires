package by.dudko.questionnaires.dto.user;

import by.dudko.questionnaires.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Builder
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final long id;
    private final String email;
    private final String password;
    private final boolean enabled;

    public static UserDetailsImpl of(User user) {
        return builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .enabled(user.isActivated())
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
