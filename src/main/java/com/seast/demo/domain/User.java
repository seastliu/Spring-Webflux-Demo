package com.seast.demo.domain;

public class User {

    private Long id;

    private String user;

    public User(){}

    public User(Long id, String user) {
        this.id = id;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", user=" + user + "]";
    }
}
