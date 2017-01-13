package ch.template.wiring;

import javax.inject.Singleton;

import ch.template.ui.MainActivity;
import ch.template.ui.list.ListFragment;
import dagger.Component;

@Singleton
@Component(modules = {ShoppingListModule.class, FlavourModule.class})
public interface ShoppingListComponent {
    void inject(MainActivity mainActivity);
    void inject(ListFragment listFragment);
}
