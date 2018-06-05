package com.ymu.framework.web;

/**
 * aouth2.0 token对象。
 */
public final class AccessToken {


    /**
     * scope : am_application_scope default
     * token_type : bearer
     * expires_in : 2075
     * access_token : 354d5f382773baa18aa277f3a0d764a0
     */

    private String scope;
    private String token_type;
    private int expires_in;
    private String access_token;

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
