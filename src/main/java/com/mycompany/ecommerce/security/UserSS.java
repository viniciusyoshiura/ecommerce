package com.mycompany.ecommerce.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mycompany.ecommerce.domain.enums.EProfile;

// ---------- User class that implements UserDetails from Spring Security
public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String email;
	private String password;
	// ---------- Access levels
	private Collection<? extends GrantedAuthority> authorities;

	public UserSS() {

	}

	public UserSS(Integer id, String email, String password, Set<EProfile> profiles) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		// ---------- Generating Authorities from Profiles
		this.authorities = profiles.stream().map(x -> new SimpleGrantedAuthority(x.getDescription()))
				.collect(Collectors.toList());
	}

	public Integer getId() {

		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return authorities;
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

		return true;
	}

	public boolean hasRole(EProfile profile) {
		
		// ---------- new SimpleGrantedAuthority: converts profile description to GrantedAuthority 
		return getAuthorities().contains(new SimpleGrantedAuthority(profile.getDescription()));
		
	}
	
}
