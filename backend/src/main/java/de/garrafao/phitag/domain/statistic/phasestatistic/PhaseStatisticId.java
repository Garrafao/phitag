package de.garrafao.phitag.domain.statistic.phasestatistic;

import java.io.Serializable;

public class PhaseStatisticId implements Serializable {

    private String ownername;

    private String projectname;

    private String phasename;

    public PhaseStatisticId() {
    }

    public PhaseStatisticId(final String ownername, final String projectname, final String phasename) {
        this.ownername = ownername;
        this.projectname = projectname;
        this.phasename = phasename;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ownername == null) ? 0 : ownername.hashCode());
        result = prime * result + ((projectname == null) ? 0 : projectname.hashCode());
        result = prime * result + ((phasename == null) ? 0 : phasename.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        PhaseStatisticId other = (PhaseStatisticId) obj;
        return ownername.equals(other.ownername) && projectname.equals(other.projectname) && phasename.equals(other.phasename);
    }

    @Override
    public String toString() {
        return String.format("PhaseStatisticId[ownername='%s', projectname='%s', phasename='%s']", ownername, projectname, phasename);
    }
    
}
