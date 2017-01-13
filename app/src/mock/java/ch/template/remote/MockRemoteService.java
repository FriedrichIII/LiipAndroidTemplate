package ch.template.remote;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.mock.BehaviorDelegate;
import rx.Observable;

public class MockRemoteService implements RemoteService {
    private final BehaviorDelegate<RemoteService> delegate;

    public MockRemoteService(BehaviorDelegate<RemoteService> delegate) {
        this.delegate = delegate;
    }

    @Override
    public Observable<List<TodoJson>> listTodos() {
        ArrayList<TodoJson> objects = new ArrayList<>();
        return delegate.returningResponse(objects).listTodos();
    }

    @Override
    public Observable<Void> postTodo(@Body TodoJson todo) {
        return delegate.returningResponse(null).postTodo(todo);
    }
}
