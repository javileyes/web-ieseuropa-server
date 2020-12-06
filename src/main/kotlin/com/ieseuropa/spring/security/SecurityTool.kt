package com.ieseuropa.spring.security

import com.ieseuropa.spring.config.exception.UnauthorizedException
import com.ieseuropa.spring.entity.Authority
import com.ieseuropa.spring.security.CustomUserDetailsService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SecurityTool {

    companion object {
        fun getAllAuthorities(): Collection<GrantedAuthority>? {
            val authorities = ArrayList<GrantedAuthority>()
            for (authority in Authority.Role.values()) {
                authorities.add(SimpleGrantedAuthority(authority.toString()))
            }
            return authorities
        }
    }

    fun isUser(id: Long): Boolean {
        return id == getCustomUserDetails().id
    }

    fun isAuthenticated(): Boolean {
        return if (SecurityContextHolder.getContext().authentication == null) {
            false
        } else SecurityContextHolder.getContext().authentication.principal is CustomUserDetailsService.CustomUserDetails
    }

    fun getUserId(): Long {
        if (!isAuthenticated()) {
            throw UnauthorizedException()
        }
        return getCustomUserDetails().id!!
    }

    private fun getCustomUserDetails(): CustomUserDetailsService.CustomUserDetails {
        return SecurityContextHolder.getContext().authentication.principal as CustomUserDetailsService.CustomUserDetails
    }

}