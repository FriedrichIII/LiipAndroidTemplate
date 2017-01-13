package ch.template;

import android.support.multidex.MultiDexApplication;

import com.orhanobut.logger.Logger;

import butterknife.ButterKnife;
import ch.template.wiring.DaggerShoppingListComponent;
import ch.template.wiring.ShoppingListComponent;
import ch.template.wiring.ShoppingListModule;
import timber.log.Timber;

public class ShoppingListApp extends MultiDexApplication {

    private ShoppingListComponent shoppingListComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        ButterKnife.setDebug(true);
        if (BuildConfig.DEBUG) {
            Timber.plant(new LoggerTree());
        }

        this.shoppingListComponent = DaggerShoppingListComponent.builder()
            .shoppingListModule(new ShoppingListModule(getBaseContext()))
            .build();
    }

    public ShoppingListComponent getShoppingListComponent() {
        return shoppingListComponent;
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
