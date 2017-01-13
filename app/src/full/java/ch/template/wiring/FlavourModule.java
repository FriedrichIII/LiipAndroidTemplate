package ch.template.wiring;

import javax.inject.Singleton;

import ch.template.remote.RemoteService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class FlavourModule {

    @Singleton
    @Provides
    RemoteService provideRemoteService(Retrofit retrofit) {
        return retrofit.create(RemoteService.class);
    }
}
