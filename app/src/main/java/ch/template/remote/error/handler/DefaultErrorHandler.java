package ch.template.remote.error.handler;

import android.util.Log;

import ch.template.remote.error.ErrorOutput;

/**
 * Created by fabrice on 06.05.16.
 */
public class DefaultErrorHandler implements ErrorHandler {

    public static final String LOG = DefaultErrorHandler.class.getSimpleName();

    @Override
    public boolean handleError(ErrorOutput errorOutput, String errorTitle, Throwable e) {
        String errorMessage = e != null ? e.getMessage() : "Unknown error";
        outputErrors(errorOutput, errorTitle, errorMessage, e);
        return true;
    }

    protected void outputErrors(ErrorOutput errorOutput, String errorTitle, String errorBody, Throwable e) {
        String errorMessage = errorBody != null ? errorBody : (e != null ? e.getMessage() : "Unknown error");
        if (errorOutput!=null) {
            try {
                errorOutput.showError(errorTitle, errorMessage);
            } catch (Exception ee) {
                System.out.println(ee);
            }
        }
        String logMessage = String.format("%s : %s", errorTitle, errorMessage);
        if (e!=null) {
            Log.e(LOG, logMessage, e);
            //Crashlytics.logException(e);
        } else {
            Log.e(LOG, logMessage);
        }
    }
}
