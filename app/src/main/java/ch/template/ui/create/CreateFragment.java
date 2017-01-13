package ch.template.ui.create;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hannesdorfmann.fragmentargs.annotation.Arg;
import com.hannesdorfmann.fragmentargs.annotation.FragmentWithArgs;

import butterknife.BindView;
import butterknife.OnClick;
import ch.template.R;
import ch.template.domain.ShoppingListDto;
import ch.template.ui.common.BaseFragment;
import icepick.State;
import timber.log.Timber;


@FragmentWithArgs
public class CreateFragment extends BaseFragment {

    @State
    ShoppingListDto objectProducedByFragment;

    @Arg
    String id;

    @BindView(R.id.fragment_argument)
    TextView fragmentArgument;

    @BindView(R.id.list)
    ListView list;

    @BindView(R.id.item_to_add)
    EditText itemToAdd;

    @BindView(R.id.save_button)
    Button saveButton;

    private ArrayAdapter<String> listAdapter;

    private OnSaveDtoListener onSaveDtoListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!(context instanceof OnSaveDtoListener)) {
            throw new RuntimeException(String.format("The activity where this fragment is added should implements %s.", OnSaveDtoListener.class.getCanonicalName()));
        }
        this.onSaveDtoListener = (OnSaveDtoListener)context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // only if it's not currently being restored (for example, on start)
            objectProducedByFragment = new ShoppingListDto();
        }

        listAdapter = new ArrayAdapter<>(getBaseActivity(), R.layout.item_list);
        listAdapter.addAll(objectProducedByFragment.getItems());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create, container, false);
        bind(rootView);
        fragmentArgument.setText(String.format("Shopping list '%s'", id));

        list.setAdapter(listAdapter);
        return rootView;
    }

    @OnClick(R.id.add_button)
    void onClickAddButton() {
        String toAdd = itemToAdd.getText().toString();
        if (toAdd == null || toAdd.equals("")) {
            return;
        }
        objectProducedByFragment.add(toAdd);
        itemToAdd.setText("");
        listAdapter.add(toAdd);
        Timber.d("The item '%s' has been added !", toAdd);
        Timber.d("ShoppingListDto is now %s !", objectProducedByFragment.toString());
    }

    @OnClick(R.id.save_button)
    void onClickSaveButton() {
        saveButton.setEnabled(false);
        onSaveDtoListener.onClickSaveDto(this.objectProducedByFragment);
    }

    public interface OnSaveDtoListener {
        void onClickSaveDto(ShoppingListDto shoppingListDto);
    }
}
