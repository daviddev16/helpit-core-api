package br.com.helpit.artigo;


import br.com.helpit.empresa.Empresa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(
        name = "tag",
        schema = "helpit"
)
public @Entity class Tag {

    @Id
    @SequenceGenerator(
            name = "tag_idtag_seq",
            sequenceName = "tag_idtag_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "tag_idtag_seq",
            strategy = GenerationType.SEQUENCE
    )
    @Column(
            name = "idtag",
            nullable = false
    )
    @JsonProperty(value = "idTag")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(
            name = "idempresa",
            foreignKey =
                @ForeignKey(name = "fk_tag_idempresa")
    )
    private Empresa empresa;


    @Column(
            name = "nmtag",
            nullable = false,
            length = 50
    )
    private String nome;


    @JsonIgnore
    @Column(
            name = "dstag",
            columnDefinition = "TEXT DEFAULT ''"
    )
    private String descricao;

    @JsonIgnore
    @OneToMany(mappedBy = "tag")
    private List<ArtigoTag> artigosTags;

}
