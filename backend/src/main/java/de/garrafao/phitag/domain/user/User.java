package de.garrafao.phitag.domain.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import de.garrafao.phitag.domain.annotationtype.AnnotationType;
import de.garrafao.phitag.domain.joblisting.Joblisting;
import de.garrafao.phitag.domain.language.Language;
import de.garrafao.phitag.domain.role.Role;
import de.garrafao.phitag.domain.usecase.Usecase;
import de.garrafao.phitag.domain.visibility.Visibility;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "phitaguser")
@Data
public class User implements UserDetails {

    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "displayname", unique = true, nullable = false)
    private String displayname;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "phitaguser_phitagrole", joinColumns = { @JoinColumn(name = "phitaguser_username") }, inverseJoinColumns = { @JoinColumn(name = "phitagrole_name") })
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "phitagusecase_name")
    private Usecase usecase;

    @ManyToOne
    @JoinColumn(name = "phitagvisibility_name")
    private Visibility visibility;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "phitaguser_phitaglanguage", joinColumns = { @JoinColumn(name = "phitaguser_username") }, inverseJoinColumns = { @JoinColumn(name = "phitaglanguage_name") })
    private Set<Language> languages = new HashSet<>();

    @Setter(AccessLevel.NONE)
    @ManyToMany()
    @JoinTable(name = "phitaguser_phitagannotationtype", joinColumns = { @JoinColumn(name = "phitaguser_username") }, inverseJoinColumns = { @JoinColumn(name = "phitagannotationtype_name") })
    private Set<AnnotationType> annotationTypes = new HashSet<>();

    @Column(name = "description")
    private String description;

    @Column(name = "prolific_id")
    private String prolific_id;


    @Column(name = "isbot")
    private boolean isbot;

    @Column(nullable = false)
    private boolean enabled;

    @Column(name = "account_non_expired", nullable = false)
    private boolean accountNonExpired;

    @Column(name = "account_non_locked", nullable = false)
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired", nullable = false)
    private boolean credentialsNonExpired;

    @ManyToMany(mappedBy = "waitinglist")
    private List<Joblisting> joblistings;


    User() {
    }

    public User(final String username, final String email, final String password, final Set<Role> roles, final Usecase usecase, final Visibility visibility, final Set<Language> languages, final String prolific_id) {
        
        Validate.notBlank(username);
        // Validate that username only contains letters, numbers and dashes
        Validate.matchesPattern(username, "^[a-zA-Z0-9-]+$");

        Validate.notBlank(email);
        Validate.notBlank(password);
        Validate.notNull(roles);
        Validate.notNull(usecase);
        Validate.notNull(visibility);
        Validate.noNullElements(roles);
        Validate.notNull(languages);

        this.username = username;
        this.displayname = username;

        this.email = email;
        this.password = password;
        this.roles = roles;
        this.usecase = usecase;
        this.visibility = visibility;

        this.languages = languages;
        this.description = "";


        this.isbot = false;

        this.enabled = true;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.prolific_id = prolific_id;

    }

    public void addRole(final Role role) {
        Validate.notNull(role);
        this.roles.add(role);
    }

    @Override
    public String toString() {
        return String.format("User[username='%s', email='%s', enabled='%b']", this.username, this.email, this.enabled);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return this.getUsername().equals(other.getUsername());
    }

    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(
                this.roles.stream().map(Role::getName).collect(Collectors.joining(",")));
    }

    public String getProlific_id() {
        return prolific_id;
    }
}
