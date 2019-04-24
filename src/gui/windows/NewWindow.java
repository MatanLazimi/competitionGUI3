package gui.windows;

import game.arena.IArena;
import game.competition.Competition;
import game.entities.sportsman.Sportsman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewWindow extends JFrame implements ActionListener {
    private static CompetionBuilder builder = CompetitionBuilder.getInstance();
    private static ArrayList<Sportsman> sportsmens;
    private static final long serialVersionUID = 1L;

    private JComboBox<String> cmbSurfeces;
    private JComboBox<String> cmbWeather;
    private JComboBox<String> cmbCompetition;
    private JComboBox<String> cmbDiscipline;
    private JComboBox<String> cmbLeague;
    private JComboBox<String> cmbGender;

    private JTextField tfArenaLength;
    private JTextField tfMaxCompetitors;
    private JTextField tfCompetitorName;
    private JTextField tfCompetitorAge;
    private JTextField tfMaxSpeed;
    private JTextField tfAcceleration;

    private int arenaLength = 700;
    private int maxCompetitor = 10;
    private int arenaWidth = 1000;

    private String chosenWeather = null;
    private IArena arena = null;
    private int competitorsNumber = 0;
    private ImageIcon competitorsImages[] = null;
    private JFrame infoTable = null;
    private boolean competitionStarted = false;
    private boolean competitionFinished = false;

    public NewWindow(){
        super("Competition");
        this.setContentPane(getPanel());
        this.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        this.setLocation(x, y);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private JPanel getPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(getArenaPanel(), BorderLayout.WEST);
        mainPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.CENTER);
        mainPanel.add(getControlsPanel(), BorderLayout.EAST);
        return mainPanel;
    }

    public JPanel getControlsPanel() {
        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(null);
        controlsPanel.setPreferredSize(new Dimension(140, arenaHeight));

        cmbSurfeces = new JComboBox<>();
        int i = 0;
        for (String string : RacingClassesFinder.getInstance().getArenasNamesList()) {
            cmbSurfeces.addItem(string);
            if (i == 0)
                cmbSurfeces.setSelectedItem(string);
            i++;
        }

        if (chosenWeather != null)
            cmbSurfeces.setSelectedItem(chosenWeather);

        // controlsPanel.setAlignmentX(0.0f);
        JLabel l1 = new JLabel("Choose arena:");
        controlsPanel.add(l1);
        l1.setLocation(10, 20);
        l1.setSize(100, 10);
        controlsPanel.add(cmbSurfeces);
        cmbSurfeces.setLocation(10, 40);
        cmbSurfeces.setSize(100, 20);

        JLabel l2 = new JLabel("Arena length:");
        l2.setLocation(10, 75);
        l2.setSize(100, 10);
        controlsPanel.add(l2);

        tfArenaLength = new JTextField("" + arenaLength);
        tfArenaLength.setLocation(10, 95);
        tfArenaLength.setSize(100, 25);
        controlsPanel.add(tfArenaLength);

        JLabel l3 = new JLabel("Max racers number:");
        l3.setLocation(10, 135);
        l3.setSize(150, 10);
        controlsPanel.add(l3);

        tfMaxRacers = new JTextField("" + maxRacers);
        tfMaxRacers.setLocation(10, 155);
        tfMaxRacers.setSize(100, 25);
        controlsPanel.add(tfMaxRacers);

        JButton buildArenaBut = new JButton("Build arena");
        buildArenaBut.setLocation(10, 195);
        buildArenaBut.setSize(100, 30);
        buildArenaBut.addActionListener(this);
        controlsPanel.add(buildArenaBut);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setLocation(0, 240);
        sep.setSize(150, 10);
        controlsPanel.add(sep);

        cmbRacers = new JComboBox<>();
        for (String string : RacingClassesFinder.getInstance().getRacersNamesList()) {
            cmbRacers.addItem(string);
        }
        JLabel l4 = new JLabel("Choose racer:");
        controlsPanel.add(l4);
        l4.setLocation(10, 260);
        l4.setSize(100, 10);

        controlsPanel.add(cmbRacers);
        cmbRacers.setLocation(10, 280);
        cmbRacers.setSize(100, 20);

        JLabel l5 = new JLabel("Choose color:");
        controlsPanel.add(l5);
        l5.setLocation(10, 315);
        l5.setSize(100, 10);

        colors = new JComboBox<>();
        colors.addItem("Black");
        colors.addItem("Green");
        colors.addItem("Blue");
        colors.addItem("Red");
        colors.addItem("Yellow");
        controlsPanel.add(colors);
        colors.setLocation(10, 335);
        colors.setSize(100, 20);

        JLabel l6 = new JLabel("Racer name:");
        l6.setLocation(10, 370);
        l6.setSize(150, 10);
        controlsPanel.add(l6);

        tfRacerName = new JTextField("R");
        tfRacerName.setLocation(10, 390);
        tfRacerName.setSize(100, 25);
        controlsPanel.add(tfRacerName);

        JLabel l7 = new JLabel("Max speed:");
        l7.setLocation(10, 425);
        l7.setSize(150, 14);
        controlsPanel.add(l7);

        tfMaxSpeed = new JTextField("120");
        tfMaxSpeed.setLocation(10, 445);
        tfMaxSpeed.setSize(100, 25);
        controlsPanel.add(tfMaxSpeed);

        JLabel l8 = new JLabel("Acceleration:");
        l8.setLocation(10, 485);
        l8.setSize(150, 10);
        controlsPanel.add(l8);

        tfAcceleration = new JTextField("15");
        tfAcceleration.setLocation(10, 505);
        tfAcceleration.setSize(100, 25);
        controlsPanel.add(tfAcceleration);

        JButton addRacerBut = new JButton("Add racer");
        addRacerBut.setLocation(10, 545);
        addRacerBut.setSize(100, 30);
        addRacerBut.addActionListener(this);
        controlsPanel.add(addRacerBut);

        JSeparator sep2 = new JSeparator(SwingConstants.HORIZONTAL);
        sep2.setLocation(0, 590);
        sep2.setSize(150, 10);
        controlsPanel.add(sep2);

        JButton startRaceBut = new JButton("Srart race");
        startRaceBut.setLocation(10, 605);
        startRaceBut.setSize(100, 30);
        startRaceBut.addActionListener(this);
        controlsPanel.add(startRaceBut);

        JButton printInfoBut = new JButton("Show info");
        printInfoBut.setLocation(10, 650);
        printInfoBut.setSize(100, 30);
        printInfoBut.addActionListener(this);
        controlsPanel.add(printInfoBut);

        return controlsPanel;
    }


    private JPanel getArenaPanel() {
        JPanel arenaPanel = new JPanel();
        arenaPanel.setLayout(null);
        arenaPanel.setPreferredSize(new Dimension(arenaWidth + 80, arenaLength));
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon("icons/" + chosenWeather + ".jpg").getImage()
                .getScaledInstance(arenaWidth + 80, arenaLength, Image.SCALE_DEFAULT));
        JLabel picLabel1 = new JLabel(imageIcon1);
        picLabel1.setLocation(0, 0);
        picLabel1.setSize(arenaWidth + 80,arenaLength);
        arenaPanel.add(picLabel1);

        for (int i = 0; i < competitorsNumber; i++) {
            JLabel picLabel2 = new JLabel(competitorsImages[i]);
            picLabel2.setLocation((int) sportsmens.get(i).getLocation().getX() + 5,
                    (int) sportsmens.get(i).getLocation().getY());
            picLabel2.setSize(70, 70);
            picLabel1.add(picLabel2);
        }

        return arenaPanel;
    }
}
