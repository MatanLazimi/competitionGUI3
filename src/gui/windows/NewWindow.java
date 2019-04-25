package gui.windows;

import game.GameEngine;
import game.arena.IArena;
import game.competition.Competition;
import game.competition.WinterCompetition;
import game.entities.sportsman.Sportsman;
import game.enums.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;

import static game.enums.SnowSurface.*;

public class NewWindow extends JFrame implements ActionListener {
    //private static CompetionBuilder builder = CompetitionBuilder.getInstance();
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
    private String chosenSurface = null;
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
        controlsPanel.setPreferredSize(new Dimension(200, arenaLength));

        JLabel title = new JLabel("BUILD ARENA");
        title.setForeground(Color.BLUE);
        Font ft=title.getFont();
        Map opt = ft.getAttributes();
        opt.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        title.setFont(ft.deriveFont(opt));
        title.setLocation(10,10);
        title.setSize(100,10);
        controlsPanel.add(title);


        JLabel len = new JLabel("Arena length");
        len.setLocation(10, 25);
        len.setSize(100, 10);
        controlsPanel.add(len);

        tfArenaLength = new JTextField("" + arenaLength);
        tfArenaLength.setLocation(10, 38);
        tfArenaLength.setSize(100, 20);
        controlsPanel.add(tfArenaLength);

        //ComboBox Surface
        cmbSurfeces = new JComboBox<>();
        for (SnowSurface string : SnowSurface.values()) {
            cmbSurfeces.addItem(convert(string.toString()));
        }

        JLabel snow_sur = new JLabel("Snow surface");
        controlsPanel.add(snow_sur);
        snow_sur.setLocation(10, 65);
        snow_sur.setSize(100, 10);

        controlsPanel.add(cmbSurfeces);
        cmbSurfeces.setLocation(10,80);
        cmbSurfeces.setSize(100,20);

        cmbWeather = new JComboBox<>();

        for (WeatherCondition string : WeatherCondition.values()) {
            cmbWeather.addItem(convert(string.toString()));
        }

        JLabel weather_con = new JLabel("Weather condition");
        controlsPanel.add(weather_con);
        weather_con.setLocation(10, 105);
        weather_con.setSize(150, 10);

        controlsPanel.add(cmbWeather);
        cmbWeather.setLocation(10,120);
        cmbWeather.setSize(100,20);

        JButton buildArenaBut = new JButton("Build arena");
        buildArenaBut.setLocation(10, 145);
        buildArenaBut.setSize(100, 30);
        buildArenaBut.addActionListener(this);
        controlsPanel.add(buildArenaBut);

        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setLocation(0, 180);
        sep.setSize(150, 10);
        controlsPanel.add(sep);


        JLabel title1 = new JLabel("CREATE COMPETITION");
        title1.setForeground(Color.BLUE);
        Font ft1=title1.getFont();
        Map opt1 = ft1.getAttributes();
        opt1.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        title1.setFont(ft1.deriveFont(opt1));
        title1.setLocation(10,185);
        title1.setSize(190,10);
        controlsPanel.add(title1);

        cmbCompetition = new JComboBox<>();
        cmbCompetition.addItem("Ski");
        cmbCompetition.addItem("Snowboard");


        JLabel choose_competition = new JLabel("Choose competition");
        controlsPanel.add(choose_competition);
        choose_competition.setLocation(10, 200);
        choose_competition.setSize(150, 10);

        controlsPanel.add(cmbCompetition);
        cmbCompetition.setLocation(10, 215);
        cmbCompetition.setSize(100, 20);

        JLabel max_comp = new JLabel("Max competitors number");
        controlsPanel.add(max_comp);
        max_comp.setLocation(10, 240);
        max_comp.setSize(210, 10);

        tfMaxCompetitors = new JTextField("" + maxCompetitor);
        tfMaxCompetitors.setLocation(10, 255);
        tfMaxCompetitors.setSize(100, 20);
        controlsPanel.add(tfMaxCompetitors);


        cmbDiscipline = new JComboBox<>();
        for (Discipline string : Discipline.values()) {
            cmbDiscipline.addItem(convert(string.toString()));
        }

        JLabel text_discipline = new JLabel("Discipline");
        controlsPanel.add(text_discipline);
        text_discipline.setLocation(10, 285);
        text_discipline.setSize(100, 10);

        controlsPanel.add(cmbDiscipline);
        cmbDiscipline.setLocation(10, 300);
        cmbDiscipline.setSize(100, 20);

        cmbLeague = new JComboBox<>();
        for (League string : League.values()) {
            cmbLeague.addItem(convert(string.toString()));
        }

        JLabel text_league = new JLabel("League");
        text_league.setLocation(10, 325);
        text_league.setSize(150, 10);
        controlsPanel.add(text_league);

        controlsPanel.add(cmbLeague);
        cmbLeague.setLocation(10, 340);
        cmbLeague.setSize(100, 20);

        cmbGender = new JComboBox<>();
            for (Gender string : Gender.values()) {
            cmbGender.addItem(convert(string.toString()));
        }

        JLabel text_gender = new JLabel("Gender");
        text_gender.setLocation(10, 365);
        text_gender.setSize(150, 10);
        controlsPanel.add(text_gender);

        controlsPanel.add(cmbGender);
        cmbGender.setLocation(10, 380);
        cmbGender.setSize(100, 20);

        JButton createCompBut = new JButton("Create competition");
        createCompBut.setLocation(10, 405);
        createCompBut.setSize(150, 20);
        createCompBut.addActionListener(this);
        controlsPanel.add(createCompBut);

        JSeparator sep1 = new JSeparator(SwingConstants.HORIZONTAL);
        sep1.setLocation(0, 435);
        sep1.setSize(150, 10);
        controlsPanel.add(sep1);


        JLabel title2 = new JLabel("ADD COMPETITOR");
        title2.setForeground(Color.BLUE);
        Font ft2=title2.getFont();
        Map opt2 = ft2.getAttributes();
        opt2.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        title2.setFont(ft2.deriveFont(opt2));
        title2.setLocation(10,440);
        title2.setSize(190,10);
        controlsPanel.add(title2);

        JLabel text_name = new JLabel("Name");
        controlsPanel.add(text_name);
        text_name.setLocation(10, 455);
        text_name.setSize(210, 10);

        tfCompetitorName = new JTextField("");
        tfCompetitorName.setLocation(10, 470);
        tfCompetitorName.setSize(100, 20);
        controlsPanel.add(tfCompetitorName);


        JLabel text_age = new JLabel("Age");
        controlsPanel.add(text_age);
        text_age.setLocation(10, 495);
        text_age.setSize(210, 10);
        controlsPanel.add(text_age);

        tfCompetitorAge = new JTextField("");
        tfCompetitorAge.setLocation(10, 510);
        tfCompetitorAge.setSize(100, 20);
        controlsPanel.add(tfCompetitorAge);

        JLabel text_maxSpeed = new JLabel("Max speed");
        controlsPanel.add(text_maxSpeed);
        text_maxSpeed.setLocation(10, 535);
        text_maxSpeed.setSize(210, 10);
        controlsPanel.add(text_maxSpeed);

        tfMaxSpeed = new JTextField("");
        tfMaxSpeed.setLocation(10, 550);
        tfMaxSpeed.setSize(100, 20);
        controlsPanel.add(tfMaxSpeed);

        JLabel text_acceleration = new JLabel("Acceleration");
        controlsPanel.add(text_acceleration);
        text_acceleration.setLocation(10, 575);
        text_acceleration.setSize(210, 10);
        controlsPanel.add(text_acceleration);

        tfAcceleration = new JTextField("");
        tfAcceleration.setLocation(10, 590);
        tfAcceleration.setSize(100, 20);
        controlsPanel.add(tfAcceleration);

        JButton addCompetitorBut = new JButton("Add competitor");
        addCompetitorBut.setLocation(10, 615);
        addCompetitorBut.setSize(150, 20);
        addCompetitorBut.addActionListener(this);
        controlsPanel.add(addCompetitorBut);

        JSeparator sep2 = new JSeparator(SwingConstants.HORIZONTAL);
        sep2.setLocation(0, 640);
        sep2.setSize(150, 10);
        controlsPanel.add(sep2);

        JButton startCompetitionBut = new JButton("Start competition");
        startCompetitionBut.setLocation(10, 645);
        startCompetitionBut.setSize(150, 20);
        startCompetitionBut.addActionListener(this);
        controlsPanel.add(startCompetitionBut);

        JButton showInfoBut = new JButton("Show info");
        showInfoBut.setLocation(10,670);
        showInfoBut.setSize(150, 20);
        showInfoBut.addActionListener(this);
        controlsPanel.add(showInfoBut);

        return controlsPanel;
    }


    public JPanel getArenaPanel() {
        JPanel arenaPanel = new JPanel();
        arenaPanel.setLayout(null);
        arenaPanel.setPreferredSize(new Dimension(arenaWidth, arenaLength));
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon("src\\icons\\" + chosenWeather + ".jpg").getImage().getScaledInstance(arenaWidth + 80, arenaLength, Image.SCALE_DEFAULT));
        JLabel picLabel1 = new JLabel(imageIcon1);
        picLabel1.setLocation(0, 0);
        picLabel1.setSize(arenaWidth,arenaLength);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {

            case "Build arena":
                if (competitionStarted && !competitionFinished) {
                    JOptionPane.showMessageDialog(this, "Competition is started! You can't change arena.");
                    return;
                }
                try {
                    arenaLength = Integer.parseInt(tfArenaLength.getText());
                    maxCompetitor = Integer.parseInt(tfMaxCompetitors.getText());
                    if (arenaLength < 700 || arenaLength > 900 || maxCompetitor <= 1 || maxCompetitor > 10)
                        throw new Exception();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.");
                    return;
                }

                chosenSurface = (String) cmbSurfeces.getSelectedItem();
                chosenWeather = (String) cmbWeather.getSelectedItem();


                /*try {
                    if (chosenWeather.equals("Sunny")) {
                        arena = builder.buildArena("game.arenas.air.AerialArena", arenaLength, maxRacers);
                    } else if (chosenArena.equals("Cloudy")) {
                        arena = builder.buildArena("game.arenas.land.LandArena", arenaLength, maxRacers);
                    } else if (chosenArena.equals("Stormy")) {
                        arena = builder.buildArena("game.arenas.naval.NavalArena", arenaLength, maxRacers);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }*/
                updateFrame();
                break;
                /*
            case "Add racer":
                int newWidth = (competitorsNumber + 1) * 65;
                if (newWidth > 700)
                    this.arenaHeight = newHeight;
                else
                    this.arenaHeight = 700;
                racers = new ArrayList<>();
                racersImages = new ImageIcon[maxRacers];

                if (raceFinished) {
                    JOptionPane.showMessageDialog(this, "Race finished! Please build a new arena.");
                    return;
                }
                if (raceStarted) {
                    JOptionPane.showMessageDialog(this, "Race started! No racers can be added.");
                    return;
                }
                if (arena == null) {
                    JOptionPane.showMessageDialog(this, "Please build arena first!");
                    return;
                }
                if (racersNumber == maxRacers) {
                    JOptionPane.showMessageDialog(this, "No more racers can be added!");
                    return;
                }
                String name;
                double maxSpeed;
                double acceleration;
                try {
                    name = tfRacerName.getText();
                    maxSpeed = Double.parseDouble(tfMaxSpeed.getText());
                    acceleration = Double.parseDouble(tfAcceleration.getText());
                    if (name.isEmpty() || maxSpeed <= 0 || acceleration <= 0)
                        throw new Exception();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.");
                    return;
                }

                String racerType = (String) cmbRacers.getSelectedItem();

                String color = (String) colors.getSelectedItem();
                EnumContainer.Color col = null;
                if (color.equals("Red"))
                    col = EnumContainer.Color.RED;
                else if (color.equals("Black"))
                    col = EnumContainer.Color.BLACK;
                else if (color.equals("Green"))
                    col = EnumContainer.Color.GREEN;
                else if (color.equals("Blue"))
                    col = EnumContainer.Color.BLUE;
                else if (color.equals("Yellow"))
                    col = EnumContainer.Color.YELLOW;

                String racerClass = null;
                if (racerType.equals("Helicopter"))
                    racerClass = "game.racers.air.Helicopter";
                else if (racerType.equals("Airplane"))
                    racerClass = "game.racers.air.Airplane";
                else if (racerType.equals("Car"))
                    racerClass = "game.racers.land.Car";
                else if (racerType.equals("Horse"))
                    racerClass = "game.racers.land.Horse";
                else if (racerType.equals("Bicycle"))
                    racerClass = "game.racers.land.Bicycle";
                else if (racerType.equals("SpeedBoat"))
                    racerClass = "game.racers.naval.SpeedBoat";
                else if (racerType.equals("RowBoat"))
                    racerClass = "game.racers.naval.RowBoat";

                try {
                    Racer racer = builder.buildRacer(racerClass, name, maxSpeed, acceleration, col);
                    arena.addRacer(racer);
                    arena.initRace();
                    racers.add(racer);
                } catch (RacerTypeException ex) {
                    JOptionPane.showMessageDialog(this, "Recer does not fit to arena! Choose another racer.");
                    return;
                } catch (Exception ex) {
                    System.out.println(ex);
                }

                racersImages[racersNumber] = new ImageIcon(new ImageIcon("icons/" + racerType + color + ".png").getImage()
                        .getScaledInstance(70, 70, Image.SCALE_DEFAULT));
                racersNumber++;

                updateFrame();
                break;

            case "Srart race":
                if (arena == null || racersNumber == 0) {
                    JOptionPane.showMessageDialog(this, "Please build arena first and add racers!");
                    return;
                }
                if (raceFinished) {
                    JOptionPane.showMessageDialog(this, "Race finished! Please build a new arena and add racers.");
                    return;
                }
                if (raceStarted) {
                    JOptionPane.showMessageDialog(this, "Race already started!");
                    return;
                }
                try {
                    raceStarted = true;
                    (new Thread() {
                        public void run() {
                            while (arena.hasActiveRacers()) {
                                try {
                                    Thread.sleep(30);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                updateFrame();
                            }
                            updateFrame();
                            raceFinished = true;
                        }
                    }).start();
                    arena.startRace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;

            case "Show info":
                if (arena == null || racersNumber == 0) {
                    JOptionPane.showMessageDialog(this, "Please build arena first and add racers!");
                    return;
                }
                String[] columnNames = {"Racer name", "Current speed", "Max speed", "Current X location", "Finished"};
                String[][] data = new String[racersNumber][5];
                int i = 0;
                for (Racer r : arena.getCompleatedRacers()) {
                    data[i][0] = r.getName();
                    data[i][1] = "" + r.getCurrentSpeed();
                    data[i][2] = "" + r.getMaxSpeed();
                    data[i][3] = "" + r.getLocation().getX();
                    data[i][4] = "COMP";
                    i++;
                }

                for (Racer r : arena.getActiveRacers()) {
                    data[i][0] = r.getName();
                    data[i][1] = "" + r.getCurrentSpeed();
                    data[i][2] = "" + r.getMaxSpeed();
                    data[i][3] = "" + r.getLocation().getX();
                    data[i][4] = "ACT";
                    i++;
                }
                for (Racer r : arena.getBrokenRacers()) {
                    data[i][0] = r.getName();
                    data[i][1] = "" + r.getCurrentSpeed();
                    data[i][2] = "" + r.getMaxSpeed();
                    data[i][3] = "" + r.getLocation().getX();
                    data[i][4] = "BROK";
                    i++;
                }
                for (Racer r : arena.getDisabledRacers()) {
                    data[i][0] = r.getName();
                    data[i][1] = "" + r.getCurrentSpeed();
                    data[i][2] = "" + r.getMaxSpeed();
                    data[i][3] = "" + r.getLocation().getX();
                    data[i][4] = "DIS";
                    i++;
                }

                JTable table = new JTable(data, columnNames);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                JScrollPane scrollPane = new JScrollPane(table);

                JPanel tabPan = new JPanel();
                // tabPan.setLayout(new GridLayout(1,0));
                tabPan.add(scrollPane);

                if (infoTable != null)
                    infoTable.dispose();
                infoTable = new JFrame("Racers information");
                infoTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                infoTable.setContentPane(tabPan);
                infoTable.pack();
                infoTable.setVisible(true);

                break;*/
        }


    }
    private void updateFrame() {
        this.setContentPane(getPanel());
        this.pack();
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        this.setLocation(x, y);
        this.setVisible(true);
    }
    /**
     * Help Function Capitalize the first latter in string
     * @param str input string
     * @return relevant string with upper case in the first latter
     */
    static String convert(String str)
    {

        // Create a char array of given String
        char ch[] = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {

            // If first character of a word is found
            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {

                // If it is in lower-case
                if (ch[i] >= 'a' && ch[i] <= 'z') {

                    // Convert into Upper-case
                    ch[i] = (char)(ch[i] - 'a' + 'A');
                }
            }

            // If apart from first character
            // Any one is in Upper-case
            else if (ch[i] >= 'A' && ch[i] <= 'Z')

                // Convert into Lower-Case
                ch[i] = (char)(ch[i] + 'a' - 'A');
        }

        // Convert the char array to equivalent String
        String st = new String(ch);
        return st;
    }
}
