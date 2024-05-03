package de.garrafao.phitag.domain.judgement;

import de.garrafao.phitag.domain.annotator.Annotator;
import de.garrafao.phitag.domain.instance.IInstance;

public interface IJudgement {

    IJudgementId getId();

    IInstance getInstance();

    Annotator getAnnotator();

    String getLabel();

    String getComment();

}
