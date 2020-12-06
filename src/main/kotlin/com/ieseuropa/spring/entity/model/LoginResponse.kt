package com.ieseuropa.spring.entity.model

import com.ieseuropa.spring.entity.Authority
import com.ieseuropa.spring.entity.User
import org.springframework.security.oauth2.common.OAuth2AccessToken

data class LoginResponse(
        var oAuth2AccessToken: OAuth2AccessToken,
        var user: User,
        var authorities: Set<Authority>
)