package pl.mobilization.conference2015.sponsor.view;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msaramak on 29.07.15.
 */
public class SponsorsListViewModel {

    public List<SponsorViewModel> sponsors;

    public SponsorsListViewModel() {
        this.sponsors = new ArrayList<>();
    }

    public void addSponsor(SponsorViewModel sponsor) {
        sponsors.add(sponsor);
    }

    public int size() {
        return sponsors.size();
    }

    public String getItemName(int position) {
        return sponsors.get(position).displayName;
    }

    public SponsorViewModel getItem(int position) {
        return sponsors.get(position);
    }
}
