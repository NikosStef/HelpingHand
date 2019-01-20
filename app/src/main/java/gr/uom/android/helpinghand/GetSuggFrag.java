package gr.uom.android.helpinghand;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GetSuggFrag extends Fragment {

    private float prob;

    private SuggestionsDao suggDao;
    private String suggestion;
    private View view;
    private List<Suggestions> suggestionsList;

    public GetSuggFrag() {
        super();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.viewsugg_frag, container, false);

        prob = getArguments().getFloat("prob");

        DaoSession daoSession = ((App) getActivity().getApplication()).getDaoSession();
        suggDao = daoSession.getSuggestionsDao();

        getSuggestion();
        update();


        return view;
    }

    private String getProbabilityRate() {
        String rate;
        if (prob >= 0.8) {
            rate = "0.8 - 1.0";
        } else if (prob >= 0.6) {
            rate = "0.6 - 0.8";
        } else if (prob >= 0.4) {
            rate = "0.4 - 0.6";
        } else if (prob >= 0.2) {
            rate = "0.2 - 0.4";
        } else
            rate = "0.0 - 0.2";

        return rate;
    }

    private void getSuggestion() {
        suggestion = "";

        suggestionsList = suggDao.queryBuilder()
                .where(SuggestionsDao.Properties.Rate.eq(getProbabilityRate()))
                .orderAsc(SuggestionsDao.Properties.Checkpoint)
                .limit(1)
                .list();

        for(int i = 0; i < suggestionsList.size(); i++){
            suggestion = suggestionsList.get(i).getSuggestion();
        }


    }

    private void update() {

        Suggestions resultSugg = suggestionsList.get(0);

        int latestCheckpoint =  suggDao.queryBuilder()
                .orderDesc(SuggestionsDao.Properties.Checkpoint)
                .limit(1)
                .list().get(0).getCheckpoint();

        resultSugg.setCheckpoint(latestCheckpoint + 1);
        suggDao.update(resultSugg);

        TextView SuggView = view.findViewById(R.id.sugg);
        SuggView.setText(suggestion);

    }
}
