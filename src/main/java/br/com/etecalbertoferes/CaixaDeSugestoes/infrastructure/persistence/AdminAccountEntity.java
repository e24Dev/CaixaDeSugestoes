package br.com.etecalbertoferes.CaixaDeSugestoes.infrastructure.persistence;

import jakarta.persistence.*;
@Entity
@Table(name = "administrators")
public class AdminAccountEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    @Column(nullable = false, unique = true, length = 100) String username;
    @Column(name = "password_hash", nullable = false, length = 60) String passwordHash;
    @Column(nullable = false) boolean active;
    protected AdminAccountEntity() {}
}
