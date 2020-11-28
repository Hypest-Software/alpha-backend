package dev.hypestsoftware.hackyeah2020.backend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "roles")
class Role(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false)
    val uuid: UUID = UUID.randomUUID(),

    @Enumerated(EnumType.STRING)
    var name: RoleName,

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
    @JsonIgnore
    var users: MutableSet<User> = mutableSetOf()
)
