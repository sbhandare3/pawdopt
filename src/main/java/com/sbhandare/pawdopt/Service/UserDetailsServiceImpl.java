package com.sbhandare.pawdopt.Service;

import com.sbhandare.pawdopt.Config.AES;
import com.sbhandare.pawdopt.Model.Role;
import com.sbhandare.pawdopt.Model.SecurityUser;
import com.sbhandare.pawdopt.Repository.SecurityUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private SecurityUserRepository securityUserRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        SecurityUser securityUser = securityUserRepository.findByUsername(username);
        if (securityUser == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : securityUser.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }

        return new org.springframework.security.core.userdetails.User(securityUser.getUsername(), "{noop}"+AES.decrypt(securityUser.getPassword()), grantedAuthorities);
    }
}
