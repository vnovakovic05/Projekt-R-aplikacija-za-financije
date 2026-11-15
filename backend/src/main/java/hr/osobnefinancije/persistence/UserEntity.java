package hr.osobnefinancije.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false, length = 255, unique = true)
    String email;

    @Column(name = "password_hash", nullable = false, columnDefinition = "text")
    String passwordHash;

    @Column(name = "created_at", nullable = false)
    OffsetDateTime createdAt;

    protected UserEntity() {
    }
}
