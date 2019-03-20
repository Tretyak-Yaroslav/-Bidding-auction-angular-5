package uk.co.afe.model.role;

/**
 * @author Sergey Teryoshin
 * 19.03.2018 12:25
 */
public final class Roles {

    public static final Role CLIENT = new Role(1L, "_IS_CLIENT");

    public static final Role BACKOFFICE = new Role(2L, "_IS_BACK_OFFICE");

    private Roles() {
    }
}
