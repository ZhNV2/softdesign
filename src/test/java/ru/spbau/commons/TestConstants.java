package ru.spbau.commons;

import org.joda.time.DateTime;
import ru.spbau.Msg;

public class TestConstants {
    public final static String SERVER_HOST = "localhost";
    public final static int SERVER_PORT = 5555;
    public final static String DEFAULT_LOGIN = "login";
    public final static String DEFAULT_TEXT = "text";
    public final static String DEFAULT_DATE = DateTime.now().toString();
    public final static Msg DEFAULT_MESSAGE = Msg.newBuilder()
                                                    .setText(DEFAULT_TEXT)
                                                    .setDate(DEFAULT_DATE)
                                                    .setLogin(DEFAULT_LOGIN)
                                                    .build();
}
