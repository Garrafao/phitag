package de.garrafao.phitag.domain.dictionary.dictionary;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.user.User;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "phitagdictionary")
@Getter
@ToString
public class Dictionary {

    @EmbeddedId
    private DictionaryId id;

    @MapsId("uname")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "uname", referencedColumnName = "username")
    })
    private User owner;

    @Column(name = "description", nullable = false)
    private String description;

    Dictionary() {
    }

    public Dictionary(final String name, final User owner, final String description) {
        Validate.notEmpty(name);
        Validate.matchesPattern(name, "^[a-zA-Z0-9-]+$");

        Validate.notNull(owner);

        this.id = new DictionaryId(name, owner.getUsername());
        this.owner = owner;
        this.description = description;
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof Dictionary)) {
            return false;
        }

        Dictionary other = (Dictionary) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

}
