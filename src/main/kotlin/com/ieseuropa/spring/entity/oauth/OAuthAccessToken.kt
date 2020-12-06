package com.ieseuropa.spring.entity.oauth

import com.ieseuropa.spring.entity.User
import java.sql.Blob
import javax.persistence.*

@Entity
@Table(name = "oauth_access_token")
class OAuthAccessToken(
        @Column(name = "token_id")
        var tokenId: String? = null,
        @Lob
        var token: Blob? = null,
        @Id
        @Column(name = "authentication_id", nullable = false)
        var authenticationId: String? = null,
        @OneToOne
        @JoinColumn(name = "user_name", referencedColumnName = "email")
        var userName: User? = null,
        @Column(name = "client_id")
        var clientId: String? = null,
        @Lob
        var authentication: Blob? = null,
        @Column(name = "refresh_token")
        var refreshToken: String? = null
)