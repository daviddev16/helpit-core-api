package br.com.helpit.artigo;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(
        name = "tags"
)
public @Entity class Tag {

    @Id
    private Long Id;

}
