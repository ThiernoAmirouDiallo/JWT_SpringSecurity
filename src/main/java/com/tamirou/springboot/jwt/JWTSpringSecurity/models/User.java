package com.tamirou.springboot.jwt.JWTSpringSecurity.models;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.NaturalId;


/**
 * The persistent class for the USERS_TABLES database table.
 *
 */
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id_user", unique=true, nullable=false)
    private Long idUser;

    @NotBlank
    @Size(min=3, max = 60)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    //@JsonIgnore
    @Size(min=3, max = 100)
    @NotBlank
    private String password;

    private String newPassword;

    @Column(nullable=false, length=300)
    @NotBlank
    private String adresse;

    @Column(nullable=false)
    private boolean enabled;

    @Column(nullable=false, length=50)
    @NotBlank
    private String firstname;

    @Column(nullable=false, length=50)
    @NotBlank
    private String lastname;

    @Temporal(TemporalType.DATE)
    @Column(nullable=true)
    private Date creationDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable=true)
    private Date lastPasswordResetDate;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String firstname, String lastname, String username, String email, String password, String adresse, String matricule) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.adresse=adresse;

        this.enabled=true;
        this.creationDate=new Date();
    }
}