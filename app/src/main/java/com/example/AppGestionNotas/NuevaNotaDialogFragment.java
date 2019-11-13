package com.example.AppGestionNotas;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.AppGestionNotas.db.entity.NotaEntity;

public class NuevaNotaDialogFragment extends DialogFragment {



    public static NuevaNotaDialogFragment newInstance() {
        return new NuevaNotaDialogFragment();
    }

    private View view;

    private EditText etTitulo, etContenido;
    private RadioGroup rgColor;
    private Switch swNotaFavorita;





    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Nueva nota");
        builder.setMessage("Introdusca los datos de la nueva nota")
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        String titulo = etTitulo.getText().toString();
                        String contenido = etContenido.getText().toString();
                        String color = "azul";

                        switch (rgColor.getCheckedRadioButtonId()) {
                            case R.id.radioButtonColorRojo:
                                color = "rojo";
                                break;
                            case R.id.radioButtonColorVerde:
                                color = "verde";
                                break;
                        }

                        boolean esFavorita = swNotaFavorita.isChecked(); //Revisar si la nota es favorita o no

                        //Comunicar al viewmodel el nuevo dato
                        NuevaNotaDialogViewModel mViewModel = ViewModelProviders.of(getActivity()).get(NuevaNotaDialogViewModel.class);
                        mViewModel.insertarNota(new NotaEntity(titulo, contenido, esFavorita, color));
                        dialog.dismiss();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.dismiss();
                    }
                });
        //Se utiliza para introducir un layout
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.nueva_nota_dialog_fragment, null);

        etContenido = view.findViewById(R.id.editTextContenido);
        etTitulo = view.findViewById(R.id.editTextTitulo);
        rgColor = view.findViewById(R.id.radioGroupColor);
        swNotaFavorita = view.findViewById(R.id.switchNotaFavorita);



        builder.setView(view); //añidimos el cuadro de dialogo a la vista

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
