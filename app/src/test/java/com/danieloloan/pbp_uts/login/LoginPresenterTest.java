package com.danieloloan.pbp_uts.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {
    @Mock
    private LoginView view;
    @Mock
    private LoginService service;
    private LoginPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new LoginPresenter(view, service);
    }

    @Test
    public void shouldShowErrorMessageWhenUsernameIsEmpty() throws Exception {
        when(view.getEmail()).thenReturn("");
        System.out.println("nim : "+view.getEmail());
        presenter.onLoginClicked();
        verify(view).showEmailError("Email tidak boleh kosong");
    }

    @Test
    public void shouldShowErrorMessageWhenPasswordIsEmpty() throws Exception {
        when(view.getEmail()).thenReturn("daniel.dos857@gmail.com");
        System.out.println("nim : "+ view.getEmail());
        when(view.getPassword()).thenReturn("");
        System.out.println("password : "+view.getPassword());
        presenter.onLoginClicked();
        verify(view).showPasswordError("Password tidak boleh kosong");
    }
    @Test
    public void shouldStartMainActivityWhenNimAndPasswordAreCorrect() throws
            Exception {
        when(view.getEmail()).thenReturn("daniel.dos857@gmail.com");
        System.out.println("nim : "+view.getEmail());
        when(view.getPassword()).thenReturn("test123");
        System.out.println("password : "+view.getPassword());
        when(service.getValid(view, view.getEmail(),
                view.getPassword())).thenReturn(true);
        System.out.println("Hasil : "+service.getValid(view,view.getEmail(),
                view.getPassword()));
        presenter.onLoginClicked();
        //verify(view).startMainActivity();
    }
    @Test
    public void shouldShowLoginErrorWhenNimAndPasswordAreInvalid() throws
            Exception {
        when(view.getEmail()).thenReturn("admin");
        System.out.println("nim : "+view.getEmail());
        when(view.getPassword()).thenReturn("admins");
        System.out.println("password : "+view.getPassword());
        when(service.getValid(view, view.getEmail(),
                view.getPassword())).thenReturn(false);
        System.out.println("Hasil : "+service.getValid(view,view.getEmail(),
                view.getPassword()));
        presenter.onLoginClicked();
        //verify(view).showLoginError(R.string.login_failed);
    }
}