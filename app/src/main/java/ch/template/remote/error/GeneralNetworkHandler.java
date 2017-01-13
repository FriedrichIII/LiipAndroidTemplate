package ch.template.remote.error;

import android.content.Context;

import java.io.IOException;

import ch.template.R;
import ch.template.remote.error.handler.DefaultErrorHandler;

/**
 * Look this : http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 */
public class GeneralNetworkHandler extends DefaultErrorHandler {

    private final Context context;

    public GeneralNetworkHandler(Context context) {
        this.context = context;
    }

    @Override
    public boolean handleError(ErrorOutput errorOutput, String errorTitle, Throwable e) {
        if (!(e instanceof RetrofitException)) {
            return false;
        }

        String errorMessage;
        RetrofitException retrofitException = (RetrofitException)e;
        Throwable exceptionToReturn = e;
        switch (retrofitException.getKind()) {
            case HTTP:
                try {
                    APIError apiError = retrofitException.getErrorBodyAs(APIError.class);
                    errorMessage = String.format("Server error : %s", apiError.getError());
                    exceptionToReturn = new RuntimeException(apiError.toString(), e);
                } catch (IOException e1) {
                    throw new RuntimeException(e1);
                }
                break;
            case NETWORK:
                errorMessage = context.getString(R.string.network_error);
                exceptionToReturn = new RuntimeException("Network error", e);
                break;
            case UNEXPECTED:
                errorMessage = context.getString(R.string.unexpected_error);
                exceptionToReturn = new RuntimeException("Unexpected error", e);
                break;
            default:
                errorMessage = context.getString(R.string.unhandled_error);
                exceptionToReturn = new RuntimeException("Unhandled error", e);
        }

        outputErrors(
                errorOutput,
                errorTitle,
                errorMessage,
                exceptionToReturn
        );
        return true;
    }
}
