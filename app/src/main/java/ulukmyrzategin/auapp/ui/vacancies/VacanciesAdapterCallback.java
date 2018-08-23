package ulukmyrzategin.auapp.ui.vacancies;

import java.util.ArrayList;

import ulukmyrzategin.auapp.data.model.VacanciesModel;

/**
 * Created by $TheSusanin on 22.08.2018.
 */
public interface VacanciesAdapterCallback {

    void onVacanciesClicked(ArrayList<VacanciesModel> vacanciesModels, int position);
}
