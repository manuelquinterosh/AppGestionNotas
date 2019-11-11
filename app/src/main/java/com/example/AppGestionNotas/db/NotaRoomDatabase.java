package com.example.AppGestionNotas.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.AppGestionNotas.db.dao.NotaDao;
import com.example.AppGestionNotas.db.entity.NotaEntity;

@Database(entities = {NotaEntity.class}, version = 1)
public abstract class NotaRoomDatabase extends RoomDatabase {

    public abstract NotaDao notaDao(); //nos va permitir obtener un objeto de tipo dao cuando lo necesitemos

    private static volatile NotaRoomDatabase INSTANCE;  //Guarda la instancia de la base de datos

    public static NotaRoomDatabase getDatabase(final Context context) { //Metodo que invocaremos para ontener una instancia de la base de datos
        if (INSTANCE == null) {
            synchronized (NotaRoomDatabase.class) {
                if (INSTANCE ==null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NotaRoomDatabase.class, "notas_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
