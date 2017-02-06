package be.zenodotus.data;

import java.util.List;

public class KalenderItems
{

    private int dag;
    private boolean feestdag;
    private List verlof;
    private String weekdag;

    public KalenderItems()
    {
    }

    public int getDag()
    {
        return dag;
    }

    public List getVerlof()
    {
        return verlof;
    }

    public String getWeekdag()
    {
        return weekdag;
    }

    public boolean isFeestdag()
    {
        return feestdag;
    }

    public void setDag(int i)
    {
        dag = i;
    }

    public void setFeestdag(boolean flag)
    {
        feestdag = flag;
    }

    public void setVerlof(List list)
    {
        verlof = list;
    }

    public void setWeekdag(String s)
    {
        weekdag = s;
    }
}
