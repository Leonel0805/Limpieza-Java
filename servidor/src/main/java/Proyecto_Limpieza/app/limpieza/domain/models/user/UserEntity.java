package Proyecto_Limpieza.app.limpieza.domain.models.user;

import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.RoleList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter //Lombock
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    @Column(unique = true)
    private String email;
    private String password;


    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "account_no_expired")
    private Boolean accountNoExpired;

    @Column(name = "account_no_locked")
    private Boolean accountNoLocked;

    @Column(name = "credential_no_expired")
    private Boolean credentialNoExpired;


//    relacion muchos a muchos con Roles
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id")) //nombre de tabla y sus campos
    private Set<RoleEntity> roles = new HashSet<>();


    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isEnabled = Boolean.TRUE;
        this.accountNoExpired = Boolean.TRUE;
        this.accountNoLocked = Boolean.TRUE;
        this.credentialNoExpired = Boolean.TRUE;
    }

    public void asignarRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
