package br.com.vote.no.restaurante.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.vote.no.restaurante.model.User;
import br.com.vote.no.restaurante.repository.UserRepository;
import br.com.vote.no.restaurante.service.provider.UserServiceProvider;
import br.com.vote.no.restaurante.support.TestFixtureSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

/**
 * Created by Vinicius on 22/12/15.
 */
@RunWith(JUnit4.class)
public class UserServiceTest extends TestFixtureSupport {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService = new UserServiceProvider();
    private Optional<User> expected;

    @Override
    public void setUp() {
        expected = getExpectedUser();
    }

    @Test
    public void testCreateUser() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(expected.get());
        Optional<User> saved = userService.createUser(getExpectedUserWithoutId().get());
        assertThat(expected, equalTo(saved));
    }

    @Test
    public void testCreateUserExisting() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(expected.get());
        Optional<User> saved = userService.createUser(getExpectedUserWithoutId().get());
        assertThat(expected, equalTo(saved));
    }

    @Test(expected = NullPointerException.class)
    public void testCreateUserWithInvalidFields() {
        User user = new User();
        when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(expected.get());
        Optional<User> saved = userService.createUser(user);
        assertThat(expected, equalTo(user));
    }

    private Optional<User> getExpectedUser() {
        return Optional.of(Fixture.from(User.class).gimme("valid"));
    }

    private Optional<User> getExpectedUserWithoutId() {
        return Optional.of(Fixture.from(User.class).gimme("validWithoutId"));
    }

}
