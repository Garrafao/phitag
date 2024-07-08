package de.garrafao.phitag.application.instance.data;

public interface IDeleteInstanceCommand {
    String getInstanceID();
    String getOwner();
    String getProject();
    String getPhase();

}
