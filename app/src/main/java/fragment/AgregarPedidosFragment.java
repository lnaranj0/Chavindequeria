package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luu.chavindequeria.R;
import com.mikepenz.materialdrawer.Drawer;

public class AgregarPedidosFragment extends Fragment {
    private static final String KEY_TITLE = "title";

    private Drawer result;

    public AgregarPedidosFragment(){

    }

    public static AgregarPedidosFragment newInstance(String title){
        AgregarPedidosFragment f = new AgregarPedidosFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        f.setArguments(args);

        return f;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        View view = inflater.inflate(R.layout.fragment_agregar_pedidos, container, false);

        Toolbar toolbar = (Toolbar)view.findViewById(R.id.appbar);

        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getSupportActionBar().setTitle("Agregar Pedidos");


        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        //outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
