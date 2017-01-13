package ch.template.ui.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import ch.template.TemplateApp;
import ch.template.ui.MainActivity;
import ch.template.wiring.TemplateComponent;
import icepick.Icepick;

public class BaseFragment extends Fragment {

    private Unbinder bind;

    private MainActivity mainActivity;

    protected MainActivity getBaseActivity() {
        return mainActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mainActivity = (MainActivity)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mainActivity = null;
    }

    protected void bind(View view) {
        bind = ButterKnife.bind(this, view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Icepick.restoreInstanceState(this, savedInstanceState);
        FragmentArgs.inject(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Icepick.saveInstanceState(this, outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null) {
            bind.unbind();
        }
    }

    protected TemplateComponent getComponent() {
        return ((TemplateApp)getBaseActivity().getApplication()).getTemplateComponent();
    }
}
