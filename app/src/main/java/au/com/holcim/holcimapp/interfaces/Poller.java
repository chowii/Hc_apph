package au.com.holcim.holcimapp.interfaces;

/**
 * Created by Jovan on 5/5/17.
 */

public interface Poller<T> {
    void pollLoading(boolean loading);
    void pollSuccessful(T polledObject);
    void pollFailed(Throwable throwable);
}
