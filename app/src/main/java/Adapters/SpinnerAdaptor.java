package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.donar.R;

import org.w3c.dom.Text;

import java.net.ContentHandler;
import java.util.ArrayList;

import DonArDato.SpinnerItem;

public class SpinnerAdaptor extends ArrayAdapter<SpinnerItem> {

    public SpinnerAdaptor(Context context, ArrayList<SpinnerItem> spinnerItems){
        super(context, 0, spinnerItems);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent){
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_row_generic,
                    parent,
                    false);
        }
        TextView id = (TextView) convertView.findViewById(R.id.idData);
        TextView description = (TextView) convertView.findViewById(R.id.descriptionData);
        SpinnerItem item = getItem(position);
        id.setText(item.getIdData());
        description.setText(item.getDescriptionData());
        return convertView;
    }
}
