package ch.template.ui.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import ch.template.R;
import ch.template.domain.ShoppingListDto;
import ch.template.domain.ShoppingListsModel;
import ch.template.remote.ErrorHandlers;
import ch.template.ui.common.BaseFragment;

public class ListFragment extends BaseFragment {

    @Inject
    ShoppingListsModel shoppingListsModel;

    @Inject
    ErrorHandlers errorHandlers;

    @BindView(R.id.dto_list)
    ListView dtoList;

    @BindView(R.id.nothing_label)
    TextView nothingLabel;

    private ArrayAdapter<ShoppingListDto> dtoAdapter;

    private OnClickCreateListener onClickCreateListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof OnClickCreateListener)) {
            throw new RuntimeException(String.format("The activity where this fragment is added should implements %s.", OnClickCreateListener.class.getCanonicalName()));
        }
        this.onClickCreateListener = (OnClickCreateListener)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getComponent().inject(this);

        dtoAdapter = new ArrayAdapter<>(getBaseActivity(), R.layout.item_list, shoppingListsModel.getElements());

    }

    @Override
    public void onResume() {
        super.onResume();
        boolean empty = this.shoppingListsModel.isEmpty();
        this.nothingLabel.setVisibility(empty ? View.VISIBLE : View.GONE);
        this.dtoList.setVisibility(empty ? View.GONE : View.VISIBLE);
        dtoAdapter.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        bind(rootView);
        dtoList.setAdapter(dtoAdapter);
        return rootView;
    }

    @OnClick(R.id.create_button)
    public void onCreateClicked() {
        this.onClickCreateListener.onClickCreateDto();
    }

    public interface OnClickCreateListener {
        void onClickCreateDto();
    }
}
