package de.garrafao.phitag.domain.instance.userankpairinstances;


import de.garrafao.phitag.domain.instance.IInstance;
import de.garrafao.phitag.domain.phase.Phase;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import lombok.Getter;
import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "phitaguserankpairinstance")
@Getter
public class UseRankPairInstance implements IInstance {

    @EmbeddedId
    private UseRankPairInstanceId id;

    @MapsId("phaseid")
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phasename", referencedColumnName = "name"),
            @JoinColumn(name = "projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "ownername", referencedColumnName = "ownername")
    })
    private Phase phase;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_first_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_first_projectname",  referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_first_ownername", referencedColumnName = "ownername")
    })
    private Usage firstusage;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_second_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_second_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_second_ownername", referencedColumnName = "ownername")
    })
    private Usage secondusage;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_third_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_third_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_third_ownername", referencedColumnName = "ownername")
    })
    private Usage thirdusage;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_fourth_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_fourth_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_fourth_ownername", referencedColumnName = "ownername")
    })
    private Usage fourthusage;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_fifth_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_fifth_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_fifth_ownername", referencedColumnName = "ownername")
    })
    private Usage fifthusage;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_sixth_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_sixth_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_sixth_ownername", referencedColumnName = "ownername")
    })
    private Usage sixthusage;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_seventh_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_seventh_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_seventh_ownername", referencedColumnName = "ownername")
    })
    private Usage seventhusage;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_eight_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_eight_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_eight_ownername", referencedColumnName = "ownername")
    })
    private Usage eightusage;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_ninth_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_ninth_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_ninth_ownername", referencedColumnName = "ownername")
    })
    private Usage ninthusage;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "phitagusage_tenth_dataid", referencedColumnName = "dataid"),
            @JoinColumn(name = "phitagusage_tenth_projectname", referencedColumnName = "projectname"),
            @JoinColumn(name = "phitagusage_tenth_ownername", referencedColumnName = "ownername")
    })
    private Usage tenthusage;




    @Column(name = "label_set", nullable = false)
    private String labelSet;

    @Column(name = "non_label", nullable = false)
    private String nonLabel;

    UseRankPairInstance() {
    }


    //constructor for 4 instances
    public UseRankPairInstance(final String instanceId, final Phase phase, final Usage firstusage, final Usage secondusage,
                               final Usage thirdusage, final Usage fourthusage,
                               final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);

        Validate.notNull(firstusage);
        Validate.notNull(secondusage);
        Validate.notNull(thirdusage);
        Validate.notNull(fourthusage);

        Validate.notNull(labelSet);
        Validate.notEmpty(labelSet);
        Validate.notNull(nonLabel);
        Validate.notEmpty(nonLabel);

        this.phase = phase;
        this.id = new UseRankPairInstanceId(instanceId, phase.getId());

        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.thirdusage = thirdusage;
        this.fourthusage = fourthusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }
    //constructor for 2 instances
    public UseRankPairInstance(final String instanceId, final Phase phase, final Usage firstusage, final Usage secondusage,

                               final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(firstusage);
        Validate.notNull(secondusage);
        Validate.notNull(labelSet);
        Validate.notEmpty(labelSet);
        Validate.notNull(nonLabel);
        Validate.notEmpty(nonLabel);
        this.phase = phase;
        this.id = new UseRankPairInstanceId(instanceId, phase.getId());
        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }
    //constructor for 3 instances
    public UseRankPairInstance(final String instanceId, final Phase phase,
                               final Usage firstusage,
                               final Usage secondusage,
                               final Usage  thirdusage,
                               final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(firstusage);
        Validate.notNull(secondusage);
        Validate.notNull(thirdusage);
        Validate.notNull(labelSet);
        Validate.notEmpty(labelSet);
        Validate.notNull(nonLabel);
        Validate.notEmpty(nonLabel);
        this.phase = phase;
        this.id = new UseRankPairInstanceId(instanceId, phase.getId());
        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.thirdusage = thirdusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }
    //constructor for 5 instances
    public UseRankPairInstance(final String instanceId, final Phase phase,
                               final Usage firstusage,
                               final Usage secondusage,
                               final Usage  thirdusage,
                               final Usage  fourthusage,
                               final Usage  fifthusage,
                               final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(firstusage);
        Validate.notNull(secondusage);
        Validate.notNull(thirdusage);
        Validate.notNull(fourthusage);
        Validate.notNull(fifthusage);
        Validate.notNull(labelSet);
        Validate.notEmpty(labelSet);
        Validate.notNull(nonLabel);
        Validate.notEmpty(nonLabel);
        this.phase = phase;
        this.id = new UseRankPairInstanceId(instanceId, phase.getId());
        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.thirdusage = thirdusage;
        this.fourthusage= fourthusage;
        this.fifthusage = fifthusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }
    //constructor for 6 instances
    public UseRankPairInstance(final String instanceId, final Phase phase,
                               final Usage firstusage,
                               final Usage secondusage,
                               final Usage  thirdusage,
                               final Usage  fourthusage,
                               final Usage  fifthusage,
                               final Usage  sixthusage,
                               final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(firstusage);
        Validate.notNull(secondusage);
        Validate.notNull(thirdusage);
        Validate.notNull(fourthusage);
        Validate.notNull(fifthusage);
        Validate.notNull(sixthusage);
        Validate.notNull(labelSet);
        Validate.notEmpty(labelSet);
        Validate.notNull(nonLabel);
        Validate.notEmpty(nonLabel);
        this.phase = phase;
        this.id = new UseRankPairInstanceId(instanceId, phase.getId());
        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.thirdusage = thirdusage;
        this.fourthusage= fourthusage;
        this.fifthusage = fifthusage;
        this.sixthusage = sixthusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    //constructor for 7 instances
    public UseRankPairInstance(final String instanceId, final Phase phase,
                               final Usage firstusage,
                               final Usage secondusage,
                               final Usage  thirdusage,
                               final Usage  fourthusage,
                               final Usage  fifthusage,
                               final Usage  sixthusage,
                               final Usage  seventhusage,
                               final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(firstusage);
        Validate.notNull(secondusage);
        Validate.notNull(thirdusage);
        Validate.notNull(fourthusage);
        Validate.notNull(fifthusage);
        Validate.notNull(sixthusage);
        Validate.notNull(seventhusage);
        Validate.notNull(labelSet);
        Validate.notEmpty(labelSet);
        Validate.notNull(nonLabel);
        Validate.notEmpty(nonLabel);
        this.phase = phase;
        this.id = new UseRankPairInstanceId(instanceId, phase.getId());
        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.thirdusage = thirdusage;
        this.fourthusage= fourthusage;
        this.fifthusage = fifthusage;
        this.sixthusage = sixthusage;
        this.seventhusage = seventhusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    //constructor for 8 instances
    public UseRankPairInstance(final String instanceId, final Phase phase,
                               final Usage firstusage,
                               final Usage secondusage,
                               final Usage  thirdusage,
                               final Usage  fourthusage,
                               final Usage  fifthusage,
                               final Usage  sixthusage,
                               final Usage  seventhusage,
                               final Usage  eightusage,
                               final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(firstusage);
        Validate.notNull(secondusage);
        Validate.notNull(thirdusage);
        Validate.notNull(fourthusage);
        Validate.notNull(fifthusage);
        Validate.notNull(sixthusage);
        Validate.notNull(seventhusage);
        Validate.notNull(eightusage);
        Validate.notNull(labelSet);
        Validate.notEmpty(labelSet);
        Validate.notNull(nonLabel);
        Validate.notEmpty(nonLabel);
        this.phase = phase;
        this.id = new UseRankPairInstanceId(instanceId, phase.getId());
        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.thirdusage = thirdusage;
        this.fourthusage= fourthusage;
        this.fifthusage = fifthusage;
        this.sixthusage = sixthusage;
        this.seventhusage = seventhusage;
        this.eightusage = eightusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }
    //constructor for 9 instances
    public UseRankPairInstance(final String instanceId, final Phase phase,
                               final Usage firstusage,
                               final Usage secondusage,
                               final Usage  thirdusage,
                               final Usage  fourthusage,
                               final Usage  fifthusage,
                               final Usage  sixthusage,
                               final Usage  seventhusage,
                               final Usage  eightusage,
                               final Usage  ninthusage,
                               final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(firstusage);
        Validate.notNull(secondusage);
        Validate.notNull(thirdusage);
        Validate.notNull(fourthusage);
        Validate.notNull(fifthusage);
        Validate.notNull(sixthusage);
        Validate.notNull(seventhusage);
        Validate.notNull(eightusage);
        Validate.notNull(ninthusage);
        Validate.notNull(labelSet);
        Validate.notEmpty(labelSet);
        Validate.notNull(nonLabel);
        Validate.notEmpty(nonLabel);
        this.phase = phase;
        this.id = new UseRankPairInstanceId(instanceId, phase.getId());
        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.thirdusage = thirdusage;
        this.fourthusage= fourthusage;
        this.fifthusage = fifthusage;
        this.sixthusage = sixthusage;
        this.seventhusage = seventhusage;
        this.eightusage = eightusage;
        this.ninthusage = ninthusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }

    //constructor for 10 instances
    public UseRankPairInstance(final String instanceId, final Phase phase,
                               final Usage firstusage,
                               final Usage secondusage,
                               final Usage  thirdusage,
                               final Usage  fourthusage,
                               final Usage  fifthusage,
                               final Usage  sixthusage,
                               final Usage  seventhusage,
                               final Usage  eightusage,
                               final Usage  ninthusage,
                               final Usage tenthusage,
                               final String labelSet, final String nonLabel) {
        Validate.notNull(instanceId);
        Validate.notNull(phase);
        Validate.notNull(firstusage);
        Validate.notNull(secondusage);
        Validate.notNull(thirdusage);
        Validate.notNull(fourthusage);
        Validate.notNull(fifthusage);
        Validate.notNull(sixthusage);
        Validate.notNull(seventhusage);
        Validate.notNull(eightusage);
        Validate.notNull(ninthusage);
        Validate.notNull(tenthusage);
        Validate.notNull(labelSet);
        Validate.notEmpty(labelSet);
        Validate.notNull(nonLabel);
        Validate.notEmpty(nonLabel);
        this.phase = phase;
        this.id = new UseRankPairInstanceId(instanceId, phase.getId());
        this.firstusage = firstusage;
        this.secondusage = secondusage;
        this.thirdusage = thirdusage;
        this.fourthusage= fourthusage;
        this.fifthusage = fifthusage;
        this.sixthusage = sixthusage;
        this.seventhusage = seventhusage;
        this.eightusage = eightusage;
        this.ninthusage = ninthusage;
        this.tenthusage = tenthusage;
        this.labelSet = labelSet;
        this.nonLabel = nonLabel;
    }
    public List<String> getLabelSet() {
        return Arrays.asList(labelSet.split(","));
    }

    @Override
    public String toString() {
        return String.format("InstanceData [id=%s]", id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UseRankPairInstance other = (UseRankPairInstance) obj;
        return this.id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
}
