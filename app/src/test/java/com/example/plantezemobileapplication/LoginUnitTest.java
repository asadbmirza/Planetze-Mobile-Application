package com.example.plantezemobileapplication;

import static org.mockito.Mockito.*;

import com.example.plantezemobileapplication.model.LoginModel;
import com.example.plantezemobileapplication.presenter.LoginPresenter;
import com.example.plantezemobileapplication.view.login.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class LoginUnitTest {
    @Mock
    private LoginView mockView;
    @Mock
    private LoginModel mockLoginModel;
    @Mock
    private Task<AuthResult> mockTask;
    @Mock
    private Task<Void> mockUserTask;
    @Mock
    private FirebaseAuth mockAuth;
    @Mock
    private FirebaseUser mockUser;
    private LoginPresenter loginPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockTask);
        when(mockTask.addOnCompleteListener(any())).thenAnswer(invocation -> {
            OnCompleteListener<AuthResult> listener = invocation.getArgument(0);
            listener.onComplete(mockTask);
            return mockTask;
        });

        when(mockUserTask.addOnCompleteListener(any())).thenAnswer(invocation -> {
            OnCompleteListener<Void> listener = invocation.getArgument(0);
            listener.onComplete(mockUserTask);
            return mockTask;
        });

        when(mockLoginModel.getUser()).thenReturn(mockUser);
        when(mockUser.reload()).thenReturn(mockUserTask);

        mockLoginModel = new LoginModel(mockAuth);
        loginPresenter = new LoginPresenter(mockView, mockLoginModel);
    }

    @Test
    public void testLoginUserSuccessEmailVerified() {
        when(mockTask.isSuccessful()).thenReturn(true);
        when(mockUserTask.isSuccessful()).thenReturn(true);
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockLoginModel.isVerified()).thenReturn(true);

        loginPresenter.loginUser("test@example.com", "password");

        verify(mockView).showLoading();
        verify(mockView).hideLoading();
        verify(mockView).showProcessSuccess("Logged in.");
    }

    @Test
    public void testLoginUserSuccessEmailNotVerified() {
        when(mockTask.isSuccessful()).thenReturn(true);
        when(mockUserTask.isSuccessful()).thenReturn(true);
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockLoginModel.isVerified()).thenReturn(false);

        loginPresenter.loginUser("test@example.com", "password");

        verify(mockView).showLoading();
        verify(mockView).hideLoading();
        verify(mockView).showProcessFailure("Please verify your email before logging in.");
    }

    @Test
    public void testLoginUserFailure() {
        when(mockTask.isSuccessful()).thenReturn(false);
        loginPresenter.loginUser("test@example.com", "wrongPassword");

        verify(mockView).showLoading();
        verify(mockView).hideLoading();
        verify(mockView).showProcessFailure("Authentication failed.");
    }

    @Test
    public void testLoginUserWithNullView() {
        LoginPresenter presenterWithNullView = new LoginPresenter(null, mockLoginModel);

        presenterWithNullView.loginUser("test@example.com", "password");

        verifyNoInteractions(mockView);
    }

    @Test
    public void testLoginUserMissingOnlyEmail() {
        loginPresenter.loginUser("", "password");

        verify(mockView).showProcessFailure("Please enter all fields.");
    }

    @Test
    public void testLoginUserMissingOnlyPassword() {
        loginPresenter.loginUser("test@example.com", "");

        verify(mockView).showProcessFailure("Please enter all fields.");
    }

    @Test
    public void testLoginUserFailedUserVerifyCheck() {
        when(mockTask.isSuccessful()).thenReturn(true);
        when(mockUserTask.isSuccessful()).thenReturn(false);
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);

        loginPresenter.loginUser("test@example.com", "password");

        verify(mockView).showLoading();
        verify(mockView).hideLoading();
        verify(mockView).showProcessFailure("Error verifying user. Please try again.");
    }

}

