package br.com.helpit.artigo;


import br.com.helpit.empresa.Empresa;
import br.com.helpit.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(
        name = "artigo",
        schema = "helpit"
)
public @Entity class Artigo {

    @Id
    @SequenceGenerator(
            name = "artigo_idartigo_seq",
            sequenceName = "artigo_idartigo_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "artigo_idartigo_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idartigo",
            nullable = false
    )
    private Long id;


    @Column(
            name = "titulo",
            nullable = false
    )
    private String titulo;


    @Column(
            name = "corpo",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String corpo;


    @Column(
            name = "dtcriacao",
            nullable = false
    )
    private LocalDateTime dataCriacao;


    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "idusuario",
            foreignKey =
                @ForeignKey(name = "fk_artigo_idusuario", value = ConstraintMode.CONSTRAINT),
            nullable = false
    )
    private Usuario usuario;


    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "idempresa",
            foreignKey =
            @ForeignKey(name = "fk_artigo_idempresa", value = ConstraintMode.CONSTRAINT),
            nullable = false
    )
    private Empresa empresa;


    @OneToMany(mappedBy = "artigo")
    private Set<ArtigoTag> artigoTags;

}
