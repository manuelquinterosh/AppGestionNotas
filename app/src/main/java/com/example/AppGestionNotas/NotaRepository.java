package com.example.AppGestionNotas;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.AppGestionNotas.db.NotaRoomDatabase;
import com.example.AppGestionNotas.db.dao.NotaDao;
import com.example.AppGestionNotas.db.entity.NotaEntity;

import java.util.List;

public class NotaRepository {

    private NotaDao notaDao;  //Al que accederemos para realizar las operaciones de la base de datos
    private LiveData<List<NotaEntity>> allNotas;
    private LiveData<List<NotaEntity>> allNotasFavoritas;

    public NotaRepository(Application application) {
        NotaRoomDatabase db = NotaRoomDatabase.getDatabase(application); //obtenemos una instancia de la base de datos
        notaDao = db.notaDao();   //Nos permite acceder a las operaciones de la entidad nota
        allNotas = notaDao.getAll();
        allNotasFavoritas = notaDao.getAllFavoritas();
    }

    public LiveData<List<NotaEntity>> getAll() {  //Para obtener todas las notas
        return allNotas;
    }

    public LiveData<List<NotaEntity>> getAllFavs() {
        return allNotasFavoritas;
    }

    public void insert (NotaEntity notaEntity){
       new insertAsyncTask(notaDao).execute(notaEntity);
    }

    private static class insertAsyncTask extends AsyncTask<NotaEntity, Void, Void> {
        private NotaDao notaDaoAsyncTask;

        insertAsyncTask(NotaDao dao) {
            notaDaoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDaoAsyncTask.insert(notaEntities[0]);
            return null;
        }
    }
}
