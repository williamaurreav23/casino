package at.steiner.casino.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String REGISTRAR = "ROLE_REGISTRAR";

    public static final String CROUPIER = "ROLE_CROUPIER";

    public static final String STOCK_BROKER = "ROLE_STOCK_BROKER";

    private AuthoritiesConstants() {
    }
}
