package ch.template.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import java.util.List;

import javax.inject.Inject;

import ch.template.R;
import ch.template.domain.ShoppingListDto;
import ch.template.domain.ShoppingListsModel;
import ch.template.remote.DefaultSubscriber;
import ch.template.remote.ErrorHandlers;
import ch.template.remote.error.ErrorOutput;
import ch.template.rx.RxOperations;
import ch.template.ui.common.BaseActivity;
import ch.template.ui.create.CreateFragment;
import ch.template.ui.create.CreateFragmentBuilder;
import ch.template.ui.list.ListFragment;

public class MainActivity extends BaseActivity implements ErrorOutput, CreateFragment.OnSaveDtoListener, ListFragment.OnClickCreateListener {

    @Inject
    ShoppingListsModel shoppingListsModel;

    @Inject
    ErrorHandlers errorHandlers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTemplateComponent().inject(this);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // only if it's not currently being restored (for example, on start)
            this.shoppingListsModel.init()
                    .compose(RxOperations.applySchedulers())
                    .subscribe(new DefaultSubscriber<List<ShoppingListDto>>(errorHandlers.getErrorHandlers(), this, "Cannot load shopping lists"){
                        @Override
                        public void onNext(List<ShoppingListDto> o) {
                            setListFragment();
                        }
                    });

        }
    }

    private void setListFragment() {
        setNewFragment("my title", new ListFragment(), false);
    }

    public void setNewFragment(String title, Fragment fragment, boolean addToBackStack) {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        String fragmentTag = fragment.getClass().getSimpleName();
        if (addToBackStack) {
            ft.addToBackStack(fragmentTag);
        }

        ft.replace(R.id.content_frame, fragment, fragmentTag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        /*
        ft.setCustomAnimations(
                R.anim.fadein,
                R.anim.fadeout
                );
                */
        ft.commit();

        // set the toolbar title
        // toolbarTitle.setText(title);
    }

    @Override
    public void onClickSaveDto(ShoppingListDto shoppingListDto) {
        shoppingListsModel.add(shoppingListDto)
                .compose(RxOperations.applySchedulers())
            .subscribe(new DefaultSubscriber<Void>(errorHandlers.getErrorHandlers(), this, "Error saving shopping list"){
                @Override
                public void onNext(Void aVoid) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage(String.format("%s has been created", shoppingListDto))
                            .show();
                    setListFragment();
                }
            });


    }

    @Override
    public void onClickCreateDto() {
        setNewFragment("my title", CreateFragmentBuilder.newCreateFragment("xxx"), true);
    }

    @Override
    public void showError(String title, String message) {
        runOnUiThread(() -> new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .show()
        );

    }
}