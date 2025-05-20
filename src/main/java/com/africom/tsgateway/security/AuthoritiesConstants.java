package com.africom.tsgateway.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";
    public static final String ADMIN_COMMERCIAL = "ROLE_ADMIN_COMMERCIAL";

    public static final String COMMERCIAL = "ROLE_COMMERCIAL";

    public static final String VENDEUR = "ROLE_VENDEUR";
    public static final String MAGASINIER = "ROLE_MAGASINIER";
    public static final String CLIENT = "ROLE_CLIENT";
    public static final String RPV_CLIENT = "ROLE_RPV_CLIENT";
    public static final String CHEF_PARK = "ROLE_CHEF_PARK";


    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
