package com.example.hirecraft.enums;

public enum UserRole {
    ROLE_CLIENT,
    ROLE_SERVICE_PROVIDER,
    ROLE_ADMIN;

    // Optional: If you need to remove the "ROLE_" prefix
    public String getSimpleName() {
        return this.name().replace("ROLE_", "");
    }
}


//package com.example.hirecraft.enums;
//
//public enum UserRole {
//    ROLE_CLIENT("CLIENT"),
//    ROLE_SERVICE_PROVIDER("SERVICE_PROVIDER"),
//    ROLE_ADMIN("ADMIN");
//
//    private final String value;
//
//    UserRole(String value) {
//        this.value = value;
//    }
//
//    public String getValue() {
//        return value;
//    }
//}



