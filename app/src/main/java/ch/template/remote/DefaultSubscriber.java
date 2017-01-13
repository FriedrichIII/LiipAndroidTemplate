package ch.template.remote;

import java.util.ArrayList;
import java.util.List;

import ch.template.remote.error.handler.DefaultErrorHandler;
import ch.template.remote.error.handler.ErrorHandler;
import ch.template.remote.error.ErrorOutput;
import rx.Subscriber;

public class DefaultSubscriber<T> extends Subscriber<T> {
    private final List<ErrorHandler> errorHandlers;
    private final ErrorOutput errorOutput;
    private final String errorTitle;

    public DefaultSubscriber(List<ErrorHandler> errorHandlers, ErrorOutput errorOutput, String errorTitle) {
        this.errorHandlers = errorHandlers;
        this.errorOutput = errorOutput;
        this.errorTitle = errorTitle;
    }

    private DefaultSubscriber(ErrorOutput errorOutput, String errorTitle) {
        this(new ArrayList<>(), errorOutput, errorTitle);
    }

    private DefaultSubscriber() {
        this(new ArrayList<>(), null, "Unknown error");
    }


    protected void addHandlers(List<ErrorHandler> handlers) {
        this.errorHandlers.addAll(handlers);
    }
    /**
     * To be overridden
     */
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        for (ErrorHandler errorHandler : errorHandlers) {
            if (errorHandler.handleError(errorOutput, errorTitle, e)) {
                return;
            }
        }
        throw new RuntimeException(e);
    }

    /**
     * To be overridden
     */
    @Override
    public void onNext(T t) {

    }

}
