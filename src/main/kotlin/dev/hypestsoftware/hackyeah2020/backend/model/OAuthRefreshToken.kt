package dev.hypestsoftware.hackyeah2020.backend.model

import org.hibernate.annotations.Type
import java.sql.Blob
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "oauth_refresh_token")
class OAuthRefreshToken(
    @Id
    @Column(name = "token_id", nullable = false)
    val token_id: String,

    @Column(name = "token", nullable = false)
    @Type(type="org.hibernate.type.BinaryType")
    var token: Blob,

    @Column(name = "authentication", nullable = false)
    @Type(type="org.hibernate.type.BinaryType")
    var authentication: Blob,

    @Column(name = "user_name")
    var username: String,

    @Column(name = "expiration", nullable = false)
    var expiration: Long,
)
