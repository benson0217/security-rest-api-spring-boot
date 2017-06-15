package com.security.rest.api.model;

import java.util.List;

import lombok.Data;

@Data
public class Group {

    private String groupName;
    private List<GroupAuthority> groupAuthoritise; 
}
