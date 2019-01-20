package gr.uom.android.helpinghand;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddSuggFrag extends Fragment {


    private View view;
    private Button button;
    private Spinner spinner;
    private EditText editText;
    private String suggText;
    private String rateText;
    private SuggestionsDao suggDao;

    public AddSuggFrag() {
        super();
    }

    @Override
    public void onStart() {
        super.onStart();


        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        suggDao = daoSession.getSuggestionsDao();

        setUpViews();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.addsugg_frag, container, false);

        return view;
    }

    public void setUpViews() {
        if (isAdded()) {
            editText = view.findViewById(R.id.editText);
            button = view.findViewById(R.id.AddButton);
            spinner = view.findViewById(R.id.spinner);

            editText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.getText().clear();
                }
            });


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(editText.getText().toString().equals("")){
                        Toast.makeText(getContext(), "Please add suggestion before clicking the button!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        suggText = editText.getText().toString();
                        rateText = spinner.getSelectedItem().toString();
                        makeSuggestion();

                    }
                }
            });


            ArrayAdapter<CharSequence> fromResource = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.rates_array,
                    android.R.layout.simple_spinner_item);
            fromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(fromResource);

        }

    }

    private void makeSuggestion(){

        Suggestions suggestion = new Suggestions();
        suggestion.setSuggestion(suggText);
        suggestion.setRate(rateText);
        suggestion.setCheckpoint(0);

        suggDao.insert(suggestion);
        Toast.makeText(getContext(), "Activity Added Successfully", Toast.LENGTH_LONG).show();

    }


}
