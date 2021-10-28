package com.manutencao_predial.model;

public enum ImmobileSizeEnum {
    SMALL_SIZE(1), MID_SIZE(2), LARGE_SIZE(3);

    private int employees;

    ImmobileSizeEnum(int employees) {
        this.employees = employees;
    }

    public int getEmployees() {
        return employees;
    }
    public void setEmployees(int employees) {
        this.employees = employees;
    }
}
