package io.joework.pictureproviderapi.domain;

public enum Permission {
    PICTURE_READ("picture:read"),
    PICTURE_WRITE("picture:write"),
    PICTURE_ACCEPT("picture:accept");

    private final String permission;

    private Permission(String permission){
        this.permission = permission;
    }

    public String getPermission(){
        return permission;
    }
}
