package com.oh29oh29.hellojpa.dto;

public class MemberDTO {

    private final String username;

    private final int age;

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }
}
