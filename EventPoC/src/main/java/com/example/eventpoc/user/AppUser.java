package com.example.eventpoc.user;

import com.example.eventpoc.common.IsEqual;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class AppUser implements IsEqual<AppUser> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private String name;

    public AppUser() {
    }

    public AppUser(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUser user = (AppUser) o;

        if (id != user.id) return false;
        return name != null ? name.equals(user.name) : user.name == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public boolean isEqual(AppUser user) {
        if(this.getId() != user.getId()){
            return false;
        }
        if(this.getName() == user.getName()){
            return false;
        }
        return true;
    }
}
