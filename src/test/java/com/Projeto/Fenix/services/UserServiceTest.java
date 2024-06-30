package com.Projeto.Fenix.services;

import com.Projeto.Fenix.domain.user.User;
import com.Projeto.Fenix.domain.user.UserRole;
import com.Projeto.Fenix.exceptions.MissingFieldsException;
import com.Projeto.Fenix.exceptions.UserNotFoundException;
import com.Projeto.Fenix.exceptions.UserUnauthorizedException;
import com.Projeto.Fenix.exceptions.UsernameOrEmailAlreadyInUseException;
import com.Projeto.Fenix.infra.security.TokenService;
import com.Projeto.Fenix.repositories.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UuidService uuidService;

    @Mock
    TokenService tokenService;

    @InjectMocks
    UserService userService;

    User testUser;

    HttpServletRequest request = new HttpServletRequest() {
        @Override
        public Object getAttribute(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String getCharacterEncoding() {
            return null;
        }

        @Override
        public void setCharacterEncoding(String s) throws UnsupportedEncodingException {

        }

        @Override
        public int getContentLength() {
            return 0;
        }

        @Override
        public long getContentLengthLong() {
            return 0;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public String getParameter(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return null;
        }

        @Override
        public String[] getParameterValues(String s) {
            return new String[0];
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return null;
        }

        @Override
        public String getProtocol() {
            return null;
        }

        @Override
        public String getScheme() {
            return null;
        }

        @Override
        public String getServerName() {
            return null;
        }

        @Override
        public int getServerPort() {
            return 0;
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return null;
        }

        @Override
        public String getRemoteAddr() {
            return null;
        }

        @Override
        public String getRemoteHost() {
            return null;
        }

        @Override
        public void setAttribute(String s, Object o) {

        }

        @Override
        public void removeAttribute(String s) {

        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public Enumeration<Locale> getLocales() {
            return null;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public RequestDispatcher getRequestDispatcher(String s) {
            return null;
        }

        @Override
        public int getRemotePort() {
            return 0;
        }

        @Override
        public String getLocalName() {
            return null;
        }

        @Override
        public String getLocalAddr() {
            return null;
        }

        @Override
        public int getLocalPort() {
            return 0;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public AsyncContext startAsync() throws IllegalStateException {
            return null;
        }

        @Override
        public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
            return null;
        }

        @Override
        public boolean isAsyncStarted() {
            return false;
        }

        @Override
        public boolean isAsyncSupported() {
            return false;
        }

        @Override
        public AsyncContext getAsyncContext() {
            return null;
        }

        @Override
        public DispatcherType getDispatcherType() {
            return null;
        }

        @Override
        public String getRequestId() {
            return null;
        }

        @Override
        public String getProtocolRequestId() {
            return null;
        }

        @Override
        public ServletConnection getServletConnection() {
            return null;
        }

        @Override
        public String getAuthType() {
            return null;
        }

        @Override
        public Cookie[] getCookies() {
            return new Cookie[0];
        }

        @Override
        public long getDateHeader(String s) {
            return 0;
        }

        @Override
        public String getHeader(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getHeaders(String s) {
            return null;
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            return null;
        }

        @Override
        public int getIntHeader(String s) {
            return 0;
        }

        @Override
        public String getMethod() {
            return null;
        }

        @Override
        public String getPathInfo() {
            return null;
        }

        @Override
        public String getPathTranslated() {
            return null;
        }

        @Override
        public String getContextPath() {
            return null;
        }

        @Override
        public String getQueryString() {
            return null;
        }

        @Override
        public String getRemoteUser() {
            return null;
        }

        @Override
        public boolean isUserInRole(String s) {
            return false;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public String getRequestedSessionId() {
            return null;
        }

        @Override
        public String getRequestURI() {
            return null;
        }

        @Override
        public StringBuffer getRequestURL() {
            return null;
        }

        @Override
        public String getServletPath() {
            return null;
        }

        @Override
        public HttpSession getSession(boolean b) {
            return null;
        }

        @Override
        public HttpSession getSession() {
            return null;
        }

        @Override
        public String changeSessionId() {
            return null;
        }

        @Override
        public boolean isRequestedSessionIdValid() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromCookie() {
            return false;
        }

        @Override
        public boolean isRequestedSessionIdFromURL() {
            return false;
        }

        @Override
        public boolean authenticate(HttpServletResponse httpServletResponse) throws IOException, ServletException {
            return false;
        }

        @Override
        public void login(String s, String s1) throws ServletException {

        }

        @Override
        public void logout() throws ServletException {

        }

        @Override
        public Collection<Part> getParts() throws IOException, ServletException {
            return null;
        }

        @Override
        public Part getPart(String s) throws IOException, ServletException {
            return null;
        }

        @Override
        public <T extends HttpUpgradeHandler> T upgrade(Class<T> aClass) throws IOException, ServletException {
            return null;
        }
    };

    @BeforeEach
    public void setup(){
        testUser.setUserUsername("theUsername");
        testUser.setUserPassword("thePassword");
        testUser.setUserEmail("email@email.com");
    }

    @DisplayName("Cria usuário com sucesso")
    @Test
    void createNewUserSuccess() throws Exception {
        given(userRepository.findUserByUserEmail(any())).willThrow(Exception.class);
        given(userRepository.findUserByUserUsername(any())).willThrow(Exception.class);
        given(uuidService.generateUUID()).willReturn(UUID.randomUUID());

        User theUser = userService.createNewUser(testUser.getUserUsername(), testUser.getUserPassword(),
                testUser.getUserEmail());

        verify(userRepository, times(1)).save(any());
        assertEquals(theUser.getUserUsername(), testUser.getUserUsername());
    }

    @DisplayName("Tenta criar usuário por com email duplicado")
    @Test
    void createNewUserSuccessWithDuplicatedEmail() {
        given(userRepository.findUserByUserEmail(any())).willReturn(testUser);

        assertThrows(UsernameOrEmailAlreadyInUseException.class, () -> {
         userService.createNewUser(testUser.getUserUsername(), testUser.getUserPassword(),
                testUser.getUserEmail());
        });

        verify(userRepository, times(0)).save(any());
    }

    @DisplayName("Tenta criar usuário com username duplicado")
    @Test
    void createNewUserSuccessWithDuplicatedUsername() {
        given(userRepository.findUserByUserEmail(any())).willReturn(testUser);
        given(userRepository.findUserByUserUsername(any())).willReturn(testUser);

        assertThrows(UsernameOrEmailAlreadyInUseException.class, () -> {
            userService.createNewUser(testUser.getUserUsername(), testUser.getUserPassword(),
                    testUser.getUserEmail());
        });

        verify(userRepository, times(0)).save(any());
    }

    @DisplayName("Tenta criar usuário com campos nulos")
    @Test
    void createNewUserWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            userService.createNewUser(testUser.getUserUsername(), null,
                    testUser.getUserEmail());
        });

        verify(userRepository, times(0)).save(any());
    }

    @DisplayName("Usuário tem autorização para realizar ação")
    @Test
    void validateUserAuthorizationSuccess() throws Exception {
        testUser.setUserRole(UserRole.ADMIN);

        userService.validateUserAuthorization(testUser);
    }

    @DisplayName("Usuário não tem autorização para realizar ação")
    @Test
    void validateUserAuthorizationFailed() throws Exception {
        testUser.setUserRole(UserRole.USER);

        assertThrows(UserUnauthorizedException.class, () -> {
            userService.validateUserAuthorization(testUser);
        });
    }

    @DisplayName("Procura usuário por email com sucesso")
    @Test
    void findUserByUserEmailSuccess() {
        given(userRepository.findUserByUserEmail(testUser.getUserEmail())).willReturn(testUser);

        User theUser = userService.findUserByUserEmail(testUser.getUserEmail());

        verify(userRepository, times(1)).findUserByUserEmail(any());
        assertEquals(theUser.getUserEmail(), testUser.getUserEmail());
    }

    @DisplayName("Procura usuário por email sem resultados")
    @Test
    void findUserByUserEmailWithNoResults() {
        given(userRepository.findUserByUserEmail(testUser.getUserEmail())).willThrow(Exception.class);

        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserByUserEmail(testUser.getUserEmail());
        });

        verify(userRepository, times(1)).findUserByUserUsername(any());
    }

    @DisplayName("Procura usuário por email com campos nulos")
    @Test
    void findUserByUserEmailWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            userService.findUserByUserEmail(null);
        });

        verify(userRepository, times(0)).findUserByUserUsername(any());
    }

    @DisplayName("Procura usuário por usuário com sucesso")
    @Test
    void findUserByUserUsernameSuccess() throws Exception {
        given(userRepository.findUserByUserUsername(testUser.getUserUsername())).willReturn(testUser);

        User theUser = userService.findUserByUserUsername(testUser.getUserUsername());

        verify(userRepository, times(1)).findUserByUserUsername(any());
        assertEquals(theUser.getUserEmail(), testUser.getUserEmail());
    }


    @DisplayName("Procura usuário por usuário sem resultados")
    @Test
    void findUserByUserUsernameWithNoResults() {
        given(userRepository.findUserByUserUsername(testUser.getUserUsername())).willThrow(Exception.class);

        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserByUserUsername(testUser.getUserUsername());
        });

        verify(userRepository, times(1)).findUserByUserUsername(any());
    }

    @DisplayName("Procura usuário por username com campos nulos")
    @Test
    void findUserByUserUsernameWithNullFields() {
        assertThrows(MissingFieldsException.class, () -> {
            userService.findUserByUserUsername(null);
        });

        verify(userRepository, times(0)).findUserByUserUsername(any());
    }

    @DisplayName("Procura usuário por Id com sucesso")
    @Test
    void findUserByUserIdSuccess() throws Exception {
        testUser.setUserId(UUID.randomUUID());

        given(userRepository.findUserByUserId(testUser.getUserId())).willReturn(testUser);

        User theUser = userService.findUserByUserId(testUser.getUserId());

        assertEquals(theUser.getUserEmail(), testUser.getUserEmail());
        verify(userRepository, times(1)).findUserByUserId(any());
    }

    @DisplayName("Procura usuário por Id sem resultados")
    @Test
    void findUserByUserIdWithNoResults() {
        testUser.setUserId(UUID.randomUUID());

        given(userRepository.findUserByUserId(testUser.getUserId())).willThrow(Exception.class);

        assertThrows(UserNotFoundException.class, () -> {
            userService.findUserByUserId(testUser.getUserId());
        });

        verify(userRepository, times(1)).findUserByUserId(any());
    }

    @DisplayName("Localiza usuário por token")
    @Test
    void findUserByToken() {


        given(tokenService.validateToken(any())).willReturn(testUser.getUserUsername());
        given(userRepository.findUserByUserUsername(any())).willReturn(testUser);

        userService.findUserByToken(request);

        verify(tokenService, times(1)).validateToken(any());
        verify(userRepository, times(1)).findUserByUserUsername(any());
    }

    @DisplayName("Retorna informação pública do usuário")
    @Test
    void findUserByIdPublicInfo() {
        testUser.setUserId(UUID.randomUUID());
        given(userRepository.findUserByUserId(any())).willReturn(testUser);

        userService.findUserByIdPublicInfo(testUser.getUserId());

        verify(userRepository, times(1)).findUserByUserId(any());
    }
}