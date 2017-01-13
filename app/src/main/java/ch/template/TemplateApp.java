package ch.template;

import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import ch.template.wiring.DaggerTemplateComponent;
import ch.template.wiring.TemplateComponent;
import ch.template.wiring.TemplateModule;
import timber.log.Timber;

public class TemplateApp extends MultiDexApplication {

    private TemplateComponent templateComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(true);
        if (BuildConfig.DEBUG) {
            Timber.plant(new LoggerTree());
        }

        this.templateComponent = DaggerTemplateComponent.builder()
            .templateModule(new TemplateModule())
            .build();
    }

    public TemplateComponent getTemplateComponent() {
        return templateComponent;
    }

    private static class LoggerTree extends Timber.DebugTree {

        public LoggerTree() {

            // see configuration doc here : https://github.com/orhanobut/logger
            Logger
                    .init()
                    .methodOffset(5);
        }
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            Logger.log(priority, tag, message, t);
        }
    }
}
