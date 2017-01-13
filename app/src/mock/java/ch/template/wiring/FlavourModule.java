package ch.template.wiring;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import ch.template.remote.MockRemoteService;
import ch.template.remote.RemoteService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

@Module
public class FlavourModule {

    @Singleton
    @Provides
    RemoteService provideRemoteService(MockRetrofit mockRetrofit) {
        final BehaviorDelegate<RemoteService> delegate = mockRetrofit.create(RemoteService.class);
        RemoteService mockRemoteService = new MockRemoteService(delegate);
        return mockRemoteService;
    }

    @Provides
    @Singleton
    MockRetrofit provideMockRetrofit(Retrofit retrofit) {
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setFailurePercent(50);
        return new MockRetrofit.Builder(retrofit).networkBehavior(behavior).backgroundExecutor(Executors.newSingleThreadExecutor()).build();
    }
}
