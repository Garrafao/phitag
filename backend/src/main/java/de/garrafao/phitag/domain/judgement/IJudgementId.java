package de.garrafao.phitag.domain.judgement;

import de.garrafao.phitag.domain.annotator.AnnotatorId;
import de.garrafao.phitag.domain.instance.IInstanceId;

public interface IJudgementId {

    String getUUID();

    AnnotatorId getAnnotatorid();
    
    IInstanceId getInstanceid();

}
