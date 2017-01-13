package ch.template.remote.error.handler;

import android.content.Context;

import ch.template.remote.error.ErrorOutput;

/**
 * Created by fabrice on 06.05.16.
 */
public interface ErrorHandler {
    boolean handleError(ErrorOutput errorOutput, String errorTitle, Throwable e);
}
