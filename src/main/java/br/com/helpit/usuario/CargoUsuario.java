package br.com.helpit.usuario;

import br.com.helpit.usuario.enums.CargoInterno;
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
        name = "cargosusuarios",
        schema = "helpit",
        indexes = {
                @Index(name = "idx_cargosusuarios_idusuario", columnList = "idusuario")}
)
public class CargoUsuario {

    @Id
    @SequenceGenerator(
            name = "cargosusuarios_idcgusuario_seq",
            sequenceName = "cargosusuarios_idcgusuario_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "cargosusuarios_idcgusuario_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idcgusuario",
            nullable = false
    )
    private Integer id;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE
    )
    @JoinColumn(
            name = "idusuario",
            nullable = false,
            foreignKey =
                @ForeignKey(name = "fk_cargosusuarios_idusuario",
                        value = ConstraintMode.CONSTRAINT)
    )
    private Usuario usuario;


    @Enumerated(EnumType.STRING)
    @Column(
            name = "dscargointerno",
            nullable = false
    )
    private CargoInterno cargoInterno;

}
