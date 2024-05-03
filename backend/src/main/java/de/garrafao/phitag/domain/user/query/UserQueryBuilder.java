package de.garrafao.phitag.domain.user.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.core.QueryComponent;

public class UserQueryBuilder {

    private final List<QueryComponent> queryComponents;

    public UserQueryBuilder() {
        this.queryComponents = new ArrayList<>();
    }

    public UserQueryBuilder withFuzzySearch(final String searchTerm) {
        if (searchTerm == null  || searchTerm.isEmpty()) {
            return this;
        }
        Arrays.stream(searchTerm.trim().split(",")).forEach(field -> this.queryComponents.add(new FuzzyQueryComponent(field.trim())));
        return this;
    }

    public UserQueryBuilder withUsername(String username) {
        if (username == null || username.isEmpty() || username.isBlank()) {
            return this;
        }
        
        this.queryComponents.add(new UsernameQueryComponent(username));
        return this;
    }

    public UserQueryBuilder withEmail(String email) {
        if (email == null || email.isEmpty() || email.isBlank()) {
            return this;
        }
        
        this.queryComponents.add(new EmailQueryComponent(email));
        return this;
    }

    public UserQueryBuilder withRole(String role) {
        if (role == null || role.isEmpty() || role.isBlank()) {
            return this;
        }
        
        this.queryComponents.add(new RoleQueryComponent(role));
        return this;
    }

    public UserQueryBuilder withVisibility(String visibility) {
        if (visibility == null || visibility.isEmpty() || visibility.isBlank()) {
            return this;
        }
        
        this.queryComponents.add(new VisibilityQueryComponent(visibility));
        return this;
    }

    public UserQueryBuilder withLanguage(String language) {
        if (language == null ||language.isEmpty() || language.isBlank()) {
            return this;
        }
        
        this.queryComponents.add(new LanguageQueryComponent(language));
        return this;
    }

    public UserQueryBuilder bot(Boolean bot) {
        if (bot == null) {
            return this;
        }
        this.queryComponents.add(new IsBotQueryComponent(bot));
        return this;
    }

    public UserQueryBuilder enabled(Boolean enabled) {
        if (enabled == null) {
            return this;
        }
        this.queryComponents.add(new IsEnabledQueryComponent(enabled));
        return this;
    }

    public Query build() {
        return new Query(this.queryComponents);
    }
    
}
