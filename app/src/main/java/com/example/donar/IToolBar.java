package com.example.donar;

import android.widget.Toolbar;

public interface IToolBar {

    /**
     * Permite modificar los datos que muestra la ToolBar.
     * Cualquier layout que utilise la ToolBaar debera implementarla para actualizar la información
     */
    void dataChangeToolbar(String titulo, String usuario, String idUsuario, boolean verBotones);
    void mostrarBotones(boolean mostrar);

}
