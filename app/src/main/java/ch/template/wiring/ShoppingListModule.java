package ch.template.wiring;

import android.content.Context;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

import javax.inject.Singleton;

import ch.template.domain.ShoppingListsModel;
import ch.template.remote.ErrorHandlers;
import ch.template.remote.RemoteService;
import ch.template.remote.error.RxErrorHandlingCallAdapterFactory;
import dagger.Module;
import dagger.Provides;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ShoppingListModule {

    private final Context context;

    public ShoppingListModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    ShoppingListsModel provideTester(RemoteService remoteService) {
        return new ShoppingListsModel(remoteService);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder().addInterceptor(logging).build();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm a z"));

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .client(client)
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    ErrorHandlers provideSubscriberFactory() {
        return new ErrorHandlers(context);
    }

}
