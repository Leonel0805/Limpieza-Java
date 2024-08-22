package Proyecto_Limpieza.app.limpieza.domain.models.user;


import Proyecto_Limpieza.app.limpieza.domain.models.roles.RoleEnum;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs.RolDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", unique = true)
    private RoleEnum roleName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id")) //nombre de tabla y sus campos
    private Set<PermissionEntity> permissions = new HashSet<>();

    public RoleEntity(RolDTO rolDTO) {

        this.roleName = rolDTO.roleName();
    }

    public void addAllPermission(Set<PermissionEntity> permission) {
        permissions.addAll(permission);
    }
}
