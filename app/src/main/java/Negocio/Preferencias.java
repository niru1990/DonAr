package Negocio;
import android.content.SharedPreferences;

import java.util.Map;

public class Preferencias {

    private SharedPreferences preferencia;

    public Preferencias(SharedPreferences preferencia) {
        this.preferencia = preferencia;
    }

    public boolean guardarPreferencia(String key, String value){
        boolean response = false;
        SharedPreferences.Editor editor = preferencia.edit();
        editor.putString(key, value);
        editor.commit();
        response = true;
        return response;
    }

    public boolean guardarPreferencia(String key, boolean value){
        boolean response = false;
        SharedPreferences.Editor editor = preferencia.edit();
        editor.putBoolean(key, value);
        editor.commit();
        response = true;
        return response;
    }

    public boolean guardarPreferencia(String key, Float value){
        boolean response = false;
        SharedPreferences.Editor editor = preferencia.edit();
        editor.putFloat(key, value);
        editor.commit();
        response = true;
        return response;
    }

    public boolean guardarPreferencia(String key, Integer value){
        boolean response = false;
        SharedPreferences.Editor editor = preferencia.edit();
        editor.putInt(key, value);
        editor.commit();
        response = true;
        return response;
    }

    public boolean guardarPreferencia(String key, Long value){
        boolean response = false;
        SharedPreferences.Editor editor = preferencia.edit();
        editor.putLong(key, value);
        editor.commit();
        response = true;
        return response;
    }

    public String obtenerPreferencia(String key, String defaultValue) {
        return preferencia.getString(key, defaultValue);
    }

    public Integer obtenerPreferencia(String key, Integer defaultValue) {
        return preferencia.getInt(key, defaultValue);
    }

    public boolean obtenerPreferencia(String key, boolean defaultValue) {
        return preferencia.getBoolean(key, defaultValue);
    }

    public Float obtenerPreferencia(String key, Float defaultValue) {
        return preferencia.getFloat(key, defaultValue);
    }

    public Long obtenerPreferencia(String key, Long defaultValue) {
        return preferencia.getLong(key, defaultValue);
    }

    public Map obtenerPreferencia() {
        return preferencia.getAll();
    }

}
