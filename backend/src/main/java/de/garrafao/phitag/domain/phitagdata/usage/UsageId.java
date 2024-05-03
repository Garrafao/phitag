package de.garrafao.phitag.domain.phitagdata.usage;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import de.garrafao.phitag.domain.phitagdata.IPhitagDataTypeId;
import de.garrafao.phitag.domain.project.ProjectId;
import lombok.Data;

@Data
@Embeddable
public class UsageId implements Serializable, IPhitagDataTypeId {

    @Column(name = "dataid")
    private String dataid;

    private ProjectId projectid;
    
    public UsageId() {
    }

    public UsageId(final String dataid, final ProjectId projectid) {
        this.dataid = dataid;
        this.projectid = projectid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataid == null) ? 0 : dataid.hashCode());
        result = prime * result + ((projectid == null) ? 0 : projectid.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UsageId other = (UsageId) obj;
        return dataid.equals(other.dataid) && projectid.equals(other.projectid);
    }

}
