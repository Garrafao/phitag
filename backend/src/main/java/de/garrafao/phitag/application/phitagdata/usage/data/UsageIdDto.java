package de.garrafao.phitag.application.phitagdata.usage.data;

import de.garrafao.phitag.domain.phitagdata.usage.UsageId;
import lombok.Getter;

@Getter
public class UsageIdDto {
    
        private final String dataid;
        private final String project;
        private final String owner;
    
        private UsageIdDto(final String dataid, final String project, final String owner) {
            this.dataid = dataid;
            this.project = project;
            this.owner = owner;
        }
    
        public static UsageIdDto from(final UsageId usageId) {
            return new UsageIdDto(usageId.getDataid(), usageId.getProjectid().getName(), usageId.getProjectid().getOwnername());
        }
    
}
