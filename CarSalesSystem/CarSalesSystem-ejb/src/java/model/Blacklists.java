/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@Table(name="blacklists")
public class Blacklists implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String blacklist_id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    @Column(nullable = false)
    private String blacklisted_reason;
    @Column(nullable = false, updatable = false)
    private LocalDateTime created_at;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)    
    private Status status;
    
    public enum Status{
        Pending, Approved, Rejected
    }

    public Blacklists() {
    }

    public Blacklists(Users user, String blacklisted_reason) {
        this.created_at = LocalDateTime.now();
        this.user = user;
        this.blacklisted_reason = blacklisted_reason;
        this.status = Status.Pending;
    }

    public String getBlacklist_id() {
        return blacklist_id;
    }

    public void setBlacklist_id(String blacklist_id) {
        this.blacklist_id = blacklist_id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getBlacklisted_reason() {
        return blacklisted_reason;
    }

    public void setBlacklisted_reason(String blacklisted_reason) {
        this.blacklisted_reason = blacklisted_reason;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.blacklist_id);
        return hash;
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
        final Blacklists other = (Blacklists) obj;
        return Objects.equals(this.blacklist_id, other.blacklist_id);
    }

    @Override
    public String toString() {
        return "Blacklists{" + "blacklist_id=" + blacklist_id + ", user=" + user + ", blacklisted_reason=" + blacklisted_reason + ", created_at=" + created_at + ", status=" + status + '}';
    }
    
}
