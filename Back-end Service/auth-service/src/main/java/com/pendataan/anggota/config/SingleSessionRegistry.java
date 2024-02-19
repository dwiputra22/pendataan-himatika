package com.pendataan.anggota.config;


import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;

import java.util.List;

import static java.util.Collections.emptyList;

public class SingleSessionRegistry extends SessionRegistryImpl {

    @Override
    public List<Object> getAllPrincipals() {
        // Override this method to return an empty list
        // We don't want to expose all principals to prevent multiple sessions per user
        return emptyList();
    }

    @Override
    public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
        // Override this method to return an empty list
        // We don't want to expose all sessions to prevent multiple sessions per user
        return emptyList();
    }

    @Override
    public SessionInformation getSessionInformation(String sessionId) {
        // Override this method to return null
        // We don't want to expose session information to prevent multiple sessions per user
        return null;
    }

    @Override
    public void registerNewSession(String sessionId, Object principal) {
        // Check if the principal is already associated with a session
        for (Object loggedPrincipal : getAllPrincipals()) {
            if (principal instanceof User && loggedPrincipal instanceof User) {
                User user = (User) principal;
                User loggedUser = (User) loggedPrincipal;
                if (user.getUsername().equals(loggedUser.getUsername())) {
                    // If the principal already has an active session, expire it
                    List<SessionInformation> sessions = getAllSessions(loggedPrincipal, false);
                    if (!sessions.isEmpty()) {
                        for (SessionInformation session : sessions) {
                            session.expireNow();
                        }
                    }
                    break;
                }
            }
        }
        // Register the new session
        super.registerNewSession(sessionId, principal);
    }
}

