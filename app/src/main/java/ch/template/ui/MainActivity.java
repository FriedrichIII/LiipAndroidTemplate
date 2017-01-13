package ch.template.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import javax.inject.Inject;

import ch.template.R;
import ch.template.domain.TemplateDto;
import ch.template.service.TemplateService;
import ch.template.ui.common.BaseActivity;
import ch.template.ui.create.TemplateCreateFragment;
import ch.template.ui.create.TemplateCreateFragmentBuilder;
import ch.template.ui.list.TemplateListFragment;

public class MainActivity extends BaseActivity implements TemplateCreateFragment.OnSaveDtoListener, TemplateListFragment.OnClickCreateListener {

    @Inject
    TemplateService templateService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTemplateComponent().inject(this);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // only if it's not currently being restored (for example, on start)
            setListFragment();
        }
    }

    private void setListFragment() {
        setNewFragment("my title", new TemplateListFragment(), false);
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
    public void onClickSaveDto(TemplateDto templateDto) {
        templateService.add(templateDto);
        new AlertDialog.Builder(this)
                .setMessage(String.format("%s has been created", templateDto))
                .show();
        setListFragment();

    }

    @Override
    public void onClickCreateDto() {
        setNewFragment("my title", TemplateCreateFragmentBuilder.newTemplateCreateFragment("xxx"), true);
    }
}