package ch.template.wiring;

import javax.inject.Singleton;

import ch.template.ui.MainActivity;
import ch.template.ui.list.TemplateListFragment;
import dagger.Component;

@Singleton
@Component(modules = {TemplateModule.class, FlavourModule.class})
public interface TemplateComponent {
    void inject(MainActivity mainActivity);
    void inject(TemplateListFragment templateListFragment);
}
