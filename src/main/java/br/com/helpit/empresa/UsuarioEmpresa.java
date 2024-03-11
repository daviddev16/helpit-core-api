package br.com.helpit.empresa;

import br.com.helpit.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor


@Entity
@Table(
        name = "empusuario",
        schema = "helpit",
        indexes = {
                @Index(name = "idx_empusuario_idusuario", columnList = "idusuario"),
                @Index(name = "idx_empusuario_idempresa", columnList = "idempresa")}
)
public class UsuarioEmpresa {

    @Id
    @SequenceGenerator(
            name = "empusuario_idempusuario_seq",
            sequenceName = "empusuario_idempusuario_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "empusuario_idempusuario_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idempusuario",
            nullable = false
    )
    private Long id;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "idempresa",
            foreignKey =
                @ForeignKey(name = "fk_empusuario_idempresa")
    )
    private Empresa empresa;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "idusuario",
            foreignKey =
            @ForeignKey(name = "fk_empusuario_idusuario")
    )
    private Usuario usuario;


    @Column(
            name = "dscargoempresa",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private CargoEmpresa cargoEmpresa;

}
