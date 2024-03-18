package br.com.helpit.artigo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtigoTag artigoTag = (ArtigoTag) o;
        return Objects.equals(artigo, artigoTag.artigo) && Objects.equals(tag, artigoTag.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artigo, tag);
    }
}
