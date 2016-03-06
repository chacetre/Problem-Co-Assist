package fr.esilv.probleme_co_assist.Comparator;

import java.util.Comparator;

import fr.esilv.probleme_co_assist.Models.UserData;

/**
 * Created by Charlotte on 05/03/2016.
 */
public class DateComparator implements Comparator<UserData> {
    @Override
    public int compare(UserData lhs, UserData rhs) {

        return lhs.getRegisterDate().compareTo(rhs.getRegisterDate());
    }
}
