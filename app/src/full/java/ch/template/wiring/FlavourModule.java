package ch.template.wiring;

import javax.inject.Singleton;

import ch.template.service.FlavourService;
import ch.template.service.RealFlavourService;
import dagger.Module;
import dagger.Provides;

@Module
public class FlavourModule {

    @Singleton
    @Provides
    FlavourService provideFlavourService() {
        return new RealFlavourService();
    }
}
