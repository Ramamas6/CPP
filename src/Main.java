package src;

import java.util.ArrayList;

import src.creation.Challenge;
import src.gui.Gui;
import src.gui.GuiListener;
import src.solvings.GloutonSolve;
import src.solvings.ShortestPathSolve;
import src.solvings.Solving;

public class Main implements GuiListener {

    private final Challenge challenge;
    private final Gui gui;
    private final ArrayList<Solving> solvers;

    private Solving currentSolver;
    private int period = 100;
    private boolean running = false;
    private boolean solvingStarted = false;
    private Point nextPoint;

    public static void main(String[] args){ new Main(); }

    private Main() {
        challenge = new Challenge();
        solvers = new ArrayList<>();
        insertSolvers();
        gui = new Gui(this, solvers.stream().map(Solving::getName).toArray(String[]::new));
        running = false;
        gui.addMainPanel(challenge.getRoomPanel());
        gui.setVisible();
        solve();
    }

    private void insertSolvers() {
        solvers.add(new GloutonSolve());
        solvers.add(new ShortestPathSolve());
        currentSolver = solvers.get(0);
    }

    private void initNewSolving(int N) {
        challenge.newChallenge(N);
        gui.start(challenge.getCasesNumber());
        solvingStarted = false;
    }

    private void newSolving() {
        nextPoint = challenge.start();
        currentSolver.start(nextPoint);
        solvingStarted = true;
        gui.repaint();
    }

    private void solve() {
        initNewSolving(5);
        while (true) {
            while (running) {
                if (nextPoint != null) {
                    try { Thread.sleep(period); } catch (InterruptedException e) {}
                    nextPoint = currentSolver.next(challenge.next(nextPoint));
                    gui.actualise(challenge.getCleanedCases(), challenge.getRoamedCases());
                }
                else {
                    running = false;
                    challenge.verif();
                }
            }
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    @Override
    public void onSpeedChanged(Integer newSpeed) {
        if (newSpeed > 0) period = newSpeed;
    }

    @Override
    public void onNewChallenge(Integer N) {
        running = false;
        initNewSolving(N);
    }

    @Override
    public void onResolutionPaused() {
        running = !running;
        if (!solvingStarted) newSolving();
    }

    @Override
    public void onReset(Integer solverIndex) {
        running = false;
        currentSolver = solvers.get(solverIndex);
        newSolving();
    }
}
