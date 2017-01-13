package ch.template.ui.common;

import android.support.v7.app.AppCompatActivity;

import ch.template.TemplateApp;
import ch.template.wiring.TemplateComponent;

public class BaseActivity extends AppCompatActivity {

    protected TemplateComponent getTemplateComponent() {
        return ((TemplateApp)getApplication()).getTemplateComponent();
    }
}
