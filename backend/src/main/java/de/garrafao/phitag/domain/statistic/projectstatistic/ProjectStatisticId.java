package de.garrafao.phitag.domain.statistic.projectstatistic;

import java.io.Serializable;

public class ProjectStatisticId implements Serializable {

    private String username;

    private String projectname;

    public ProjectStatisticId() {
    }

    public ProjectStatisticId(final String username, final String projectname) {
        this.username = username;
        this.projectname = projectname;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((projectname == null) ? 0 : projectname.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ProjectStatisticId other = (ProjectStatisticId) obj;
        return projectname.equals(other.projectname) && username.equals(other.username);
    }
    
    @Override
    public String toString() {
        return String.format("ProjectStatisticId[username='%s', projectname='%s']", username, projectname);
    }

}
