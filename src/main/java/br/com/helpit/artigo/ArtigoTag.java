package br.com.helpit.artigo;

import jakarta.persistence.*;

@Table(
        name = "artigotag",
        schema = "helpit"
)
public @Entity class ArtigoTag {

    @Id
    @SequenceGenerator(
            name = "artigotag_idartigotag_seq",
            sequenceName = "artigotag_idartigotag_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "artigotag_idartigotag_seq",
            strategy = GenerationType.SEQUENCE
    )
    private Long id;


    @ManyToOne
    @JoinColumn(
            name = "idartigo",
            foreignKey =
                @ForeignKey(name = "fk_artigotag_idartigo")
    )
    private Artigo artigo;


    @ManyToOne
    @JoinColumn(
            name = "idtag",
            foreignKey =
            @ForeignKey(name = "fk_artigotag_idtag")
    )
    private Tag tag;

}
