package com.ieseuropa.spring.entity.oauth

import com.ieseuropa.spring.entity.User
import java.sql.Blob
import javax.persistence.*

@Entity
@Table(name = "oauth_client_token")
class OAuthClientToken(
        @Column(name = "token_id")
        var tokenId: String? = null,
        @Lob
        var token: Blob? = null,
        @Id
        @Column(name = "authentication_id")
        var authenticationId: String? = null,
        @OneToOne
        @JoinColumn(name = "user_name", referencedColumnName = "email")
        var userName: User? = null,
        @Column(name = "client_id")
        var clientId: String? = null
)