package src.gui;

public interface GuiListener {

    public void onSpeedChanged(Integer newSpeed);

    public void onNewChallenge(Integer N);

    public void onResolutionPaused();

    public void onReset(Integer solverIndex);
}
