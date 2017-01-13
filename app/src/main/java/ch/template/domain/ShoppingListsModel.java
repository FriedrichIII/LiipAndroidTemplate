package ch.template.domain;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import ch.template.remote.RemoteService;
import ch.template.remote.TodoJson;
import rx.Observable;

public class ShoppingListsModel {
    private final RemoteService remoteService;
    private List<ShoppingListDto> dtos;

    public ShoppingListsModel(RemoteService remoteService) {
        this.remoteService = remoteService;
        this.dtos = new ArrayList<>();
    }

    public Observable<List<ShoppingListDto>> init() {
        return remoteService.listTodos()
                .map(todoJsons ->
                        Stream.of(todoJsons)
                                .limit(5)
                                .map(this::fromTodoToTemplateDto)
                                .collect(Collectors.toList())

                    )
                .doOnNext(templateDtos -> this.dtos = templateDtos);
    }

    private ShoppingListDto fromTodoToTemplateDto(TodoJson todoJson) {
        ShoppingListDto shoppingListDto = new ShoppingListDto();
        shoppingListDto.add(todoJson.getTitle());
        return shoppingListDto;
    }

    private TodoJson fromTemplateDtoToTodo(ShoppingListDto shoppingListDto) {
        TodoJson todoJson = new TodoJson();
        todoJson.setTitle(shoppingListDto.toString());
        return todoJson;
    }

    public Observable<Void> add(ShoppingListDto shoppingListDto) {
        return this.remoteService.postTodo(fromTemplateDtoToTodo(shoppingListDto))
                .doOnNext(aVoid -> ShoppingListsModel.this.dtos.add(shoppingListDto));
    }

    public List<ShoppingListDto> getElements() {
        return dtos;
    }

    public boolean isEmpty() {
        return dtos.isEmpty();
    }
}
