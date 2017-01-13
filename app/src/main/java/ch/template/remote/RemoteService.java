package ch.template.remote;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface RemoteService {

    @GET("todos")
    Observable<List<TodoJson>> listTodos();

    @POST("todos")
    Observable<Void> postTodo(@Body TodoJson todo);
}
