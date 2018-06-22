package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luu.chavindequeria.R;
import com.mikepenz.materialdrawer.Drawer;

public class VerPedidosFragment extends Fragment {
    private static final String KEY_TITLE = "title";

    private Drawer result;

    public VerPedidosFragment(){

    }

    public static VerPedidosFragment newInstance(String title){
        VerPedidosFragment fp = new VerPedidosFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        fp.setArguments(args);

        return fp;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // don't look at this layout it's just a listView to show how to handle the keyboard
        View view = inflater.inflate(R.layout.fragment_ver_pedidos, container, false);



        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the drawer to the bundle
        //outState = result.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}
