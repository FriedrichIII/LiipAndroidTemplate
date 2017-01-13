package ch.template.wiring;

import javax.inject.Singleton;

import ch.template.service.TemplateService;
import dagger.Module;
import dagger.Provides;

@Module
public class TemplateModule {

    @Singleton
    @Provides
    TemplateService provideTester() {
        return new TemplateService();
    }
}
