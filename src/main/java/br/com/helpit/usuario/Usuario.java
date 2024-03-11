package br.com.helpit.usuario;

import br.com.helpit.empresa.UsuarioEmpresa;
import br.com.helpit.usuario.enums.StatusAtividade;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Entity
@Table(
        name = "usuario",
        schema = "helpit",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_usuario_login", columnNames = "login"),
                @UniqueConstraint(name = "uq_usuario_email", columnNames = "email")}
)
public class Usuario {

    @Id
    @SequenceGenerator(
            name = "usuario_idusuario_seq",
            sequenceName = "usuario_idusuario_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "usuario_idusuario_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idusuario",
            nullable = false
    )
    private Long id;


    @Column(
            name = "login",
            nullable = false,
            updatable = false
    )
    private String login;

    @Column(
            name = "email",
            nullable = false,
            updatable = false
    )
    private String email;


    @Column(
            name = "senha",
            nullable = false
    )
    private String senha;


    @Column(
            name = "dtcriacao",
            nullable = false
    )
    private LocalDateTime dataCriacao;


    @OneToMany(
            mappedBy = "usuario",
            orphanRemoval = true
    )
    private List<CargoUsuario> cargosInternos;


    @Column(
            name = "statividade",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private StatusAtividade statusAtividade;


    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<UsuarioEmpresa> usuarioEmpresas;


    @Transient
    public boolean habilitadoParaUso() {
        return getStatusAtividade() != null && getStatusAtividade()
                .ehHabilitadoParaUtilizacao();
    }
}
