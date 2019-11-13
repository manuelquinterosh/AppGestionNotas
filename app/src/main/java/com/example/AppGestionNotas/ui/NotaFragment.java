package com.example.AppGestionNotas.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.AppGestionNotas.NuevaNotaDialogFragment;
import com.example.AppGestionNotas.NuevaNotaDialogViewModel;
import com.example.AppGestionNotas.R;
import com.example.AppGestionNotas.db.entity.NotaEntity;

import java.util.ArrayList;
import java.util.List;



public class NotaFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    private List<NotaEntity> notaEntityList;
    private MyNotaRecyclerViewAdapter notaRecyclerViewAdapter;
    private NuevaNotaDialogViewModel notaViewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NotaFragment newInstance(int columnCount) {
        NotaFragment fragment = new NotaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        //Indicamos que el fragment tiene un menu de opciones propio
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nota_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if(view.getId() == R.id.listPortrait) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else { //En caso de ser landscape

                //Nos permite averiguar el tamano de la pantalla en la que estamos ejecutando la aplicacion
                DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                float dpWidth = displayMetrics.widthPixels / displayMetrics.density; //Obtenemos el numero de pixeles por pulgada en el ancho de la pantalla
                int numeroColumnas = (int) (dpWidth /180); //El numero de columnas que podriamos dibujar en la pantalla actual en la que se esta ejecutando la aplicacion
                // el 180 significa que cada columna de la lista va ocupar 180 pixeles por pulgada
                recyclerView.setLayoutManager(new StaggeredGridLayoutManager(numeroColumnas, StaggeredGridLayoutManager.VERTICAL)); //Dibujamos la lista
            }

            notaEntityList = new ArrayList<>();


            notaRecyclerViewAdapter = new MyNotaRecyclerViewAdapter(notaEntityList, getActivity());
            recyclerView.setAdapter(notaRecyclerViewAdapter);

            lanzarViewModel(); //Metodo que se va encargar de decirnos si hay nuevos datos y en el refrescamos la lista de datos

        }
        return view;
    }

    private void lanzarViewModel() {
        notaViewModel = ViewModelProviders.of(getActivity()).get(NuevaNotaDialogViewModel.class);
        notaViewModel.getAllNotas().observe(getActivity(), new Observer<List<NotaEntity>>() {
            @Override
            public void onChanged(List<NotaEntity> notaEntities) {
                //en el momento que hay un cambio en este metodo onChanged vamos recivir la nueva lista de notas
                 notaRecyclerViewAdapter.setNuevasNotas(notaEntities);
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu_nota_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_nota:
                mostrarDialogoNuevaNota();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    private void mostrarDialogoNuevaNota() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        NuevaNotaDialogFragment dialogNuevaNota = new NuevaNotaDialogFragment();
        dialogNuevaNota.show(fm, "NuevaNotaDialogFragment");
    }
}
