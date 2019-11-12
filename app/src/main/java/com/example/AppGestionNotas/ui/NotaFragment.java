package com.example.AppGestionNotas.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
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

            lanzarViewModel();

        }
        return view;
    }

    private void lanzarViewModel() {
        notaViewModel = ViewModelProviders.of(getActivity()).get(NuevaNotaDialogViewModel.class);
        notaViewModel.getAllNotas().observe(getActivity(), new Observer<List<NotaEntity>>() {
            @Override
            public void onChanged(List<NotaEntity> notaEntities) {
                 notaRecyclerViewAdapter.setNuevasNotas(notaEntities);
            }
        });
    }

    //El primer metodo que se lanza cuando insertamos un fragmento
  /**  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NotasInteractionListener) {
            mListener = (NotasInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NotasInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }**/


}
