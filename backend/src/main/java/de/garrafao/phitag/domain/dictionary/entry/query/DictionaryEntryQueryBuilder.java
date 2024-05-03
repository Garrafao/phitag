package de.garrafao.phitag.domain.dictionary.entry.query;

import java.util.ArrayList;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class DictionaryEntryQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public DictionaryEntryQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public DictionaryEntryQueryBuilder withHeadword(final String headword) {
        if (headword == null || headword.isEmpty() || headword.isBlank()) {
            return this;
        }
        this.queryComponents.add(new HeadwordQueryComponent(headword));
        return this;
    }

    public DictionaryEntryQueryBuilder withPartOfSpeach(final String partOfSpeach) {
        if (partOfSpeach == null || partOfSpeach.isEmpty() || partOfSpeach.isBlank()) {
            return this;
        }
        this.queryComponents.add(new PartOfSpeachQueryComponent(partOfSpeach));
        return this;
    }

    public DictionaryEntryQueryBuilder withOwner(final String owner) {
        if (owner == null || owner.isEmpty() || owner.isBlank()) {
            return this;
        }
        this.queryComponents.add(new DictionaryOwnerQueryComponent(owner));
        return this;
    }

    public DictionaryEntryQueryBuilder withDictionaryname(final String dictionaryname) {
        if (dictionaryname == null || dictionaryname.isEmpty() || dictionaryname.isBlank()) {
            return this;
        }
        this.queryComponents.add(new DictionarynameQueryComponent(dictionaryname));
        return this;
    }

    public DictionaryEntryQueryBuilder withDictionaryEntryId(final String dictionaryEntryId) {
        if (dictionaryEntryId == null || dictionaryEntryId.isEmpty() || dictionaryEntryId.isBlank()) {
            return this;
        }
        this.queryComponents.add(new DictionaryEntryIdQueryComponent(dictionaryEntryId));
        return this;
    }

    public Query build() {
        return new Query(this.queryComponents);
    }

}
