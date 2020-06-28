package com.lab.labbook.exception;

public enum ExceptionType {
    EMPTY_NAME("ERROR: Name is empty."),
    USER_FOUND("ERROR: User '%s' already exists."),
    USER_NOT_FOUND("ERROR: User with id=%s was not found."),
    USER_CHANGE_NAME("ERROR: Can't change user name/login on '%s'."),
    USER_EMAIL_FOUND("ERROR: User email '%s' already exists."),
    USER_DEFAULT_CHANGE("ERROR: Can't update/delete Default value."),
    ROLE_NOT_FOUND("ERROR: Authority '%s' not found. Only USER, MODERATOR and ADMIN are available."),
    SERIES_ID_NOT_FOUND("ERROR: Series with id=%s was not found."),
    SERIES_FOUND("ERROR: Series '%s' already exists."),
    LAB_NOT_FOUND("ERROR: LAB book with id=%s was not found."),
    LAB_IS_DELETED("ERROR: Can't update/delete deleted Lab book."),
    LAB_TITLE_EMPTY("ERROR: Title can't be null or empty"),
    CURRENCY_ID_NOT_FOUND("ERROR: Currency id=%s was not found."),
    MATERIAL_NOT_FOUND("ERROR: Row material with id=%s was not found."),
    MATERIAL_FOUND("ERROR: Row material '%s' already exists."),
    MATERIAL_NAME_CHANGE("ERROR: Can't change row material name: "),
    MATERIAL_IN_USE("ERROR: Material '%s' is in use. Can't be deleted."),
    INGREDIENT_NOT_FOUND("ERROR: Ingredient with id=%s was not found."),
    SUPPLIER_NOT_FOUND("ERROR: Supplier with id=$s was not found"),
    SUPPLIER_IN_USE("ERROR: Supplier '%s' is in use. Can't be deleted."),
    SYMBOL_NOT_FOUND("ERROR: GHS symbol '%s' was not found");

    private final String message;

    ExceptionType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
