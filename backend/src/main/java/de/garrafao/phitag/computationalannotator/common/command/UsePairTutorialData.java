package de.garrafao.phitag.computationalannotator.common.command;

import lombok.Getter;

@Getter
public class UsePairTutorialData {


    private String firstUsage;
    private String secondUsage;
    private String  lemma;
    private String judgement;


    public UsePairTutorialData(String firstUsage, String secondUsage, String lemma, String judgement) {
        this.firstUsage = firstUsage;
        this.secondUsage = secondUsage;
        this.lemma = lemma;
        this.judgement = judgement;
    }

    @Override
    public String toString() {
        return "UsePairTutorialData{" +
                "firstUsage='" + firstUsage + '\'' +
                ", secondUsage='" + secondUsage + '\'' +
                ", lemma='" + lemma + '\'' +
                ", judgement='" + judgement + '\'' +
                '}';
    }

}
