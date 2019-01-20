package gr.uom.android.helpinghand;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class DataViewFrag extends Fragment {

    private View view;

    private SuggestionsDao suggDao;

    private Spinner spinner;
    private Button button;
    private ListView listView;

    private List<Suggestions> suggList;
    private List<String> suggStrList = new ArrayList<>();
    private ArrayAdapter<String> suggAdapter;


    public DataViewFrag() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        suggDao = daoSession.getSuggestionsDao();

    }

    public void updateList() {
        if (suggList != null && !suggList.isEmpty()) {
            if(!suggStrList.isEmpty()){
                suggStrList.clear();
            }
            for(int i = 0; i < suggList.size(); i++){
                suggStrList.add(suggList.get(i).getSuggestion());
            }

        }
        else if(suggList.size() == 0){
            suggStrList.clear();
        }

        suggAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, suggStrList);
        listView.setAdapter(suggAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dataview_frag, container, false);
        return view;
    }

    public void setUpViews() {
        if (isAdded()) {

            button = view.findViewById(R.id.GoButton);
            spinner = view.findViewById(R.id.MySpinner);

            ArrayAdapter<CharSequence> fromResource = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.rates_array,
                    android.R.layout.simple_spinner_item);
            fromResource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(fromResource);

            listView = getActivity().findViewById(R.id.listview);
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        setUpViews();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String setRate = spinner.getSelectedItem().toString();
                suggList = suggDao.queryBuilder()
                        .where(SuggestionsDao.Properties.Rate.eq(setRate))
                        .orderDesc(SuggestionsDao.Properties.Checkpoint)
                        .list();

                updateList();
            }
        });
    }
}
