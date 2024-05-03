package de.garrafao.phitag.domain.report;

import javax.persistence.*;

import org.apache.commons.lang3.Validate;

import de.garrafao.phitag.domain.status.Status;
import de.garrafao.phitag.domain.user.User;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "phitagreport")
@Getter
public class Report {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "report_user")
    private User user;

    @Setter
    @Column(name = "report_description")
    private String reportDescription;

    @Setter
    @ManyToOne
    @JoinColumn(name = "report_status", nullable = false)
    private Status status;

    Report() {
    }

    public Report(
            final User user,
            final String report,
            final Status status) {
        Validate.notNull(user);
        Validate.notNull(report);
        Validate.notNull(status);

        this.user = user;
        this.reportDescription = report;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Report [id=" + id + ", user=" + user + ", report=" + reportDescription + ", status=" + status + "]";
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) {
            return false;
        }
        if (object == this) {
            return true;
        }
        if (!(object instanceof Report)) {
            return false;
        }
        final Report report = (Report) object;
        return this.id.equals(report.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
