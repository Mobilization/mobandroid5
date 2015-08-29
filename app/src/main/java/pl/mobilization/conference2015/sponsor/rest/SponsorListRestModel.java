package pl.mobilization.conference2015.sponsor.rest;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by msaramak on 29.07.15.
 */
public class SponsorListRestModel {

    public List<SponsorRestModel> diamond = new ArrayList<>();
    public List<SponsorRestModel> platinum = new ArrayList<>();
    public List<SponsorRestModel> gold = new ArrayList<>();
    public List<SponsorRestModel> silver = new ArrayList<>();
}
