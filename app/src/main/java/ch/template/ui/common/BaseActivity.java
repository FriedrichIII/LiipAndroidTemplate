package ch.template.ui.common;

import android.support.v7.app.AppCompatActivity;

import ch.template.ShoppingListApp;
import ch.template.wiring.ShoppingListComponent;

public class BaseActivity extends AppCompatActivity {

    protected ShoppingListComponent getTemplateComponent() {
        return ((ShoppingListApp)getApplication()).getShoppingListComponent();
    }
}
