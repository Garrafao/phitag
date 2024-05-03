package de.garrafao.phitag.domain.statistic.annotatorstatistic;

import java.io.Serializable;

public class AnnotatorStatisticId implements Serializable {
    
    private String annotatorname; 

    private String ownername;

    private String projectname;

    public AnnotatorStatisticId() {
    }

    public AnnotatorStatisticId(final String annotatorname, final String ownername, final String projectname) {
        this.annotatorname = annotatorname;
        this.ownername = ownername;
        this.projectname = projectname;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((annotatorname == null) ? 0 : annotatorname.hashCode());
        result = prime * result + ((ownername == null) ? 0 : ownername.hashCode());
        result = prime * result + ((projectname == null) ? 0 : projectname.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AnnotatorStatisticId other = (AnnotatorStatisticId) obj;
        return annotatorname.equals(other.annotatorname) && ownername.equals(other.ownername) && projectname.equals(other.projectname);
    }

    @Override
    public String toString() {
        return String.format("AnnotatorStatisticId[annotatorname='%s', ownername='%s', projectname='%s']", annotatorname, ownername, projectname);
    }
    

}
