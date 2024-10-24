package Proyecto_Limpieza.app.limpieza.domain.models.categoria;


import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.categoriaDTO.CategoriaDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categorias")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "categoria")
    private List<Articulo> articulos;

    public Categoria(CategoriaDTO categoriaDTO) {
        this.name = categoriaDTO.name();
    }


    //    metodo para asignar tanto a categoria el articulo como a articulo la categoria
    public void addArticulo(Articulo articulo) {
        articulos.add(articulo);
        articulo.setCategoria(this);
    }
}
