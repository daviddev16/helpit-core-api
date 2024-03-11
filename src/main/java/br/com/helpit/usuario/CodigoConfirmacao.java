package br.com.helpit.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
        name = "codigo_confirmacao",
        schema = "helpit"
)
public class CodigoConfirmacao {

    @Id
    @SequenceGenerator(
            name = "codigo_confirmacao_idcodconfirmacao_seq",
            sequenceName = "codigo_confirmacao_idcodconfirmacao_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "codigo_confirmacao_idcodconfirmacao_seq"
    )
    private Integer id;

    @Column(
            name = "codigoconfirmacao",
            nullable = false
    )
    private String codigoConfirmacao;

    @OneToOne
    @JoinColumn(
            name = "idusuario",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_codigo_confirmacao_idusuario")
    )
    private Usuario usuario;

}
