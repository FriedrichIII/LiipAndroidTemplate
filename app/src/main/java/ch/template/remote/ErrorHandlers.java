package ch.template.remote;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ch.template.remote.error.ErrorOutput;
import ch.template.remote.error.GeneralNetworkHandler;
import ch.template.remote.error.handler.DefaultErrorHandler;
import ch.template.remote.error.handler.ErrorHandler;
import rx.Subscriber;

public class ErrorHandlers {

    private final Context context;
    private final List<ErrorHandler> errorHandlers;

    public ErrorHandlers(Context context) {
        this.context = context;
        this.errorHandlers = new ArrayList<ErrorHandler>(){{
            add(new GeneralNetworkHandler(context));
            add(new DefaultErrorHandler());
        }};
    }

    public List<ErrorHandler> getErrorHandlers() {
        return errorHandlers;
    }
}
