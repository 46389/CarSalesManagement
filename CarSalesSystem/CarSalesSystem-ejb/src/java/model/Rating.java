/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Chew Jin Ni
 */
@Entity
@Table(name="rating")

public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String rating_id;
    private int rating;
    private String comment;

    public Rating() {
    }

    public Rating(int rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public String getRating_id() {
        return rating_id;
    }

    public void setRating_id(String rating_id) {
        this.rating_id = rating_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.rating_id);
        hash = 53 * hash + this.rating;
        hash = 53 * hash + Objects.hashCode(this.comment);
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
        final Rating other = (Rating) obj;
        if (this.rating != other.rating) {
            return false;
        }
        if (!Objects.equals(this.rating_id, other.rating_id)) {
            return false;
        }
        return Objects.equals(this.comment, other.comment);
    }

    @Override
    public String toString() {
        return "Rating{" + "rating_id=" + rating_id + ", rating=" + rating + ", comment=" + comment + '}';
    }
}
