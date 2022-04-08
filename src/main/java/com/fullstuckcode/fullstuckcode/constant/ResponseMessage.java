package com.fullstuckcode.fullstuckcode.constant;

public class ResponseMessage {

    public static final String RESOURCE_NOT_FOUND = "%s not found";
    public static final String RESOURCE_GET_SUCCESS = "successfully fetch data %s";
    public static final String RESOURCE_CREATED = "%s successfully created";
    public static final String RESOURCE_UPDATE = "%s successfully updated";
    public static final String RESOURCE_DELETED = "%s successfully deleted";

    public static String getResourceNotFound(String resource) {
        return String.format(RESOURCE_NOT_FOUND, resource);
    }

    public static String getResourceGetSuccess(String resource) {
        return String.format(RESOURCE_GET_SUCCESS, resource);
    }

    public static String getResourceCreated(String resource) {
        return String.format(RESOURCE_CREATED, resource);
    }

    public static String getResourceUpdated(String resource) {
        return String.format(RESOURCE_UPDATE, resource);
    }

    public static String getResourceDeleted(String resource) {
        return String.format(RESOURCE_DELETED, resource);
    }
}
