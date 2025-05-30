package com.example.hirecraft.enums;

public enum PermissionName {
    //Admin Mgmt
    VIEW_ALL_USERS,
    MANAGE_USERS,
    APPROVE_DOCUMENTS,

    //User Mgmt
    VIEW_USER_PROFILE,
    EDIT_USER_PROFILE,
    DELETE_USER_ACCOUNT,
    SEND_MESSAGE,
    RECEIVE_MESSAGE,

    //Provider Permissions
    ACCEPT_BOOKING_REQUEST,
    DECLINE_BOOKING_REQUEST,
    BOOK_SERVICE_PROVIDER,
    UPLOAD_CV,
    RECEIVE_PAYMENT,

    //Client Permissions
    CREATE_BOOKING_REQUEST,
    ADD_REVIEW,
    ADD_RATING,
    MAKE_PAYMENT,

    //System Settings
    MANAGE_SETTINGS
}
