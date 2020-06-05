package Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.donar.R;

import org.w3c.dom.Text;

import java.util.List;

import DonArDato.EventoAutoMach;

public class ListAdapter extends ArrayAdapter<EventoAutoMach>{

    private List<EventoAutoMach> mList;
    private Context context;
    private int resourse;
    private TextView idEvento;
    private TextView nombre;
    private TextView nombreMedico;
    //private TextView apellido;
    private ImageButton aceptar;
    private ImageButton rechazar;

    public ListAdapter(@NonNull Context context, int resource, @NonNull List<EventoAutoMach> objects) {
        super(context, resource, objects);
        this.mList = objects;
        this.context = context;
        this.resourse = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        try {
            View view = convertView;

            if (convertView == null)
                view = LayoutInflater.from(this.context).inflate(this.resourse, null);

            EventoAutoMach eventoAutoMach = mList.get(position);
            idEvento = (TextView) view.findViewById(R.id.idEvento);
            idEvento.setText(eventoAutoMach.getIdEvento());
            nombre = (TextView) view.findViewById(R.id.nombre);
            nombre.setText(eventoAutoMach.getNombre() + " " + eventoAutoMach.getApellido());
            nombreMedico = (TextView) view.findViewById(R.id.txtNombreMedico);
            nombreMedico.setText(eventoAutoMach.getNombreMedico());
            //apellido = (TextView) view.findViewById(R.id.apellido);
            //apellido.setText(eventoAutoMach.getApellido());
            aceptar = (ImageButton) view.findViewById(R.id.aceptar);
            rechazar = (ImageButton) view.findViewById(R.id.rechazar);

            aceptar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });

            rechazar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ListView) parent).performItemClick(view, position, 1);
                }
            });

            if(position % 2 == 1)
                view.setBackgroundColor(Color.WHITE);
            else
                view.setBackgroundColor(Color.GRAY);

            return view;
        }
        catch (Exception ex){
            Log.i("ListError", ex.getMessage());
            return LayoutInflater.from(this.context).inflate(this.resourse, null);
        }
    }


}
