package com.manutencao_predial.details;

public class UserDetails {
    private String name;
    private String email;
    private String fone;
    private String job;
    
    public UserDetails(String name, String email, String fone, String job) {
        this.name = name;
        this.email = email;
        this.fone = fone;
        this.job = job;
    }
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getJob() {
        return job;
    }
    
    public void setJob(String job) {
        this.job = job;
    }
    
    public String getFone() {
        return fone;
    }
    public void setFone(String fone) {
        this.fone = fone;
    }
    
}
