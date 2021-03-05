package com.rest.controller.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserLinks {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String url;
    private String title;

    public UserLinks() {
    };

    public UserLinks(String userName, String url, String title) {
        this.userName = userName;
        this.url = url;
        this.title = title;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLinks)) return false;
        UserLinks userLinks = (UserLinks) o;
        return Objects.equals(getId(), userLinks.getId()) &&
                Objects.equals(getUserName(), userLinks.getUserName()) &&
                Objects.equals(getUrl(), userLinks.getUrl()) &&
                Objects.equals(getTitle(), userLinks.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserName(), getUrl(), getTitle());
    }
}

