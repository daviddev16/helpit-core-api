package br.com.helpit.empresa;

import br.com.helpit.artigo.Artigo;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(
        name = "empresa",
        schema = "helpit",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_empresa_sfempresa", columnNames = "sfempresa")}
)
public @Entity class Empresa {

    @Id
    @SequenceGenerator(
            name = "usuario_idempresa_seq",
            sequenceName = "usuario_idempresa_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "usuario_idempresa_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idempresa",
            nullable = false
    )
    private Long id;


    @Column(
            name = "sfempresa",
            nullable = false
    )
    private String sufixo;


    @Column(
            name = "nmempresa",
            nullable = false
    )
    private String nome;

    @OneToMany(mappedBy = "empresa")
    private List<UsuarioEmpresa> usuariosEmpresa;

    @OneToMany(mappedBy = "empresa")
    private List<Artigo> artigos;

}
