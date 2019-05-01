package gui.windows;

import game.GameEngine;
import game.arena.IArena;
import game.arena.WinterArena;
import game.competition.Competition;
import game.entities.sportsman.Sportsman;
import game.entities.sportsman.WinterSportsman;
import game.enums.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.*;
import java.util.Objects;


public class NewWindow extends JFrame implements ActionListener {
    private static builder competionBuilder = builder.getInstance();
    private static ArrayList<Sportsman> sportsmens=new ArrayList<>();

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
    private String chosenTypeComp = null;
    private String chosenDiscipline = null;
    private String chosenLeague = null;
    private String chosenGender = null;

    private IArena arena = null;
    private int competitorsNumber = 0;

    private Competition competition = null;

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
        title.setLocation(10,8);
        title.setSize(100,10);
        Font ft=title.getFont();
        Map<TextAttribute, Object> opt = new HashMap<>(ft.getAttributes());
        opt.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
        title.setFont(ft.deriveFont(opt));
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
        Map opt3 = ft2.getAttributes();
        opt3.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        title2.setFont(ft2.deriveFont(opt3));
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
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon("src/icons/" + chosenWeather + ".jpg").getImage().getScaledInstance(arenaWidth + 80, arenaLength, Image.SCALE_DEFAULT));
        JLabel picLabel1 = new JLabel(imageIcon1);
        picLabel1.setLocation(0, 0);
        picLabel1.setSize(arenaWidth,arenaLength);
        arenaPanel.add(picLabel1);

        for (int i = 0; i < competitorsNumber; i++) {
            JLabel picLabel2 = new JLabel(competitorsImages[i]);
            int placeY=(int) sportsmens.get(i).getLocation().getY();
            if(placeY>arenaLength)
                placeY=arenaLength;
            picLabel2.setLocation((int) sportsmens.get(i).getLocation().getX() + (75*i), placeY);

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
                    if (arenaLength < 700 || arenaLength > 900 )
                        throw new Exception();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input values! Please try again.");
                    return;
                }

                chosenSurface = (String) cmbSurfeces.getSelectedItem();
                chosenWeather = (String) cmbWeather.getSelectedItem();

                try {
                        arena = competionBuilder.buildArena(arenaLength,
                                SnowSurface.valueOf(chosenSurface.toUpperCase()),
                                WeatherCondition.valueOf(chosenWeather.toUpperCase()));

                } catch (Exception ex) {
                    System.out.println(ex);
                }


                competitorsNumber = 0;
                sportsmens=new ArrayList<>();
                competitionStarted = competitionFinished = false;
                if(competition !=null)
                    competition= null;

                updateFrame();
                break;

            case "Create competition":
                if (arena == null) {
                    JOptionPane.showMessageDialog(this, "Please build arena, and then create competition!");
                    return;
                }
                if (competitionFinished) {
                    JOptionPane.showMessageDialog(this, "Competition finished! Please build a new arena.");
                    return;
                }
                if (competitionStarted) {
                    JOptionPane.showMessageDialog(this, "Competition started! No Competitors can be added.");
                    return;
                }

                chosenTypeComp = (String) cmbCompetition.getSelectedItem();
                String pathType="";
                if(Objects.equals(chosenTypeComp, "Ski"))
                    pathType="game.competition.SkiCompetition";
                else if(Objects.equals(chosenTypeComp, "Snowboard"))
                    pathType="game.competition.SnowboardCompetition";
                maxCompetitor = Integer.parseInt(tfMaxCompetitors.getText());
                if (maxCompetitor<1 || maxCompetitor > 20){
                    JOptionPane.showMessageDialog(this, "Invalid Max amount of competitors! (choose 1-20)");
                    return;
                }
                int newWidth = (maxCompetitor + 1) * 80;
                if (newWidth > 1000)
                    this.arenaWidth = newWidth;
                else
                    this.arenaWidth = 1000;

                chosenDiscipline = (cmbDiscipline.getSelectedItem()+ "").toUpperCase();
                chosenLeague = (cmbLeague.getSelectedItem()+ "").toUpperCase();
                chosenGender = (cmbGender.getSelectedItem()+ "").toUpperCase();

                try {
                     competition = competionBuilder.buildCompetition((WinterArena) arena, maxCompetitor,
                             Discipline.valueOf(chosenDiscipline.toUpperCase()),
                            League.valueOf(chosenLeague.toUpperCase()),
                             Gender.valueOf(chosenGender.toUpperCase()), pathType);

                } catch (Exception ex) {
                    System.out.println(ex);
                }
                competitorsImages=new ImageIcon[maxCompetitor];
                updateFrame();
                break;

            case "Add competitor":
                if (arena == null || competition == null) {
                    JOptionPane.showMessageDialog(this, "Please build arena, create competition and add competitors!");
                    return;
                }
                if (competitionFinished) {
                    JOptionPane.showMessageDialog(this, "Competition finished! Please build a new arena and then add competitors.");
                    return;
                }
                if (competitionStarted) {
                    JOptionPane.showMessageDialog(this, "Competition already started!");
                    return;
                }

                competitorsNumber++;
                if (competitorsNumber > maxCompetitor) {
                    competitorsNumber--;
                    JOptionPane.showMessageDialog(this, "No more Competitors can be added!");
                    return;
                }

                String name=tfCompetitorName.getText();
                if(name.isEmpty()) {
                    competitorsNumber--;
                    JOptionPane.showMessageDialog(this, "Empty name not allowed!");
                    return;
                }

                double age;
                try{
                    age= Double.parseDouble(tfCompetitorAge.getText());}
                catch (NumberFormatException ex1) {
                    competitorsNumber--;
                    JOptionPane.showMessageDialog(this, "Invalid age of competitor!");
                    return;
                }
                if(!League.valueOf(chosenLeague.toUpperCase()).isInLeague(age)) {
                    competitorsNumber--;
                    JOptionPane.showMessageDialog(this, "Invalid age of competitor!");
                    return;
                }

                double maxSpeed;
                try{
                    maxSpeed= Double.parseDouble(tfMaxSpeed.getText());}
                catch (NumberFormatException ex2) {
                    competitorsNumber--;
                    JOptionPane.showMessageDialog(this, "Invalid max speed of competitor!");
                    return;
                }

                double acceleration;
                try{
                    acceleration= Double.parseDouble(tfAcceleration.getText());}
                catch (NumberFormatException ex3) {
                    competitorsNumber--;
                    JOptionPane.showMessageDialog(this, "Invalid acceleration of competitor!");
                    return;
                }

                //////////////////////Getting from Competition Details////////////////////
                String competitorPath = null;
                if (chosenTypeComp.equals("Ski"))
                    competitorPath = "game.entities.sportsman.Skier";
                else if (chosenTypeComp.equals("Snowboarder"))
                    competitorPath = "game.entities.sportsman.Snowboarder";

                Gender competitor_gender= Gender.valueOf(chosenGender);
                Discipline competitor_discipline= Discipline.valueOf(chosenDiscipline);
                /////////////////////////////////////////////////////////////////////////

                try {
                    WinterSportsman competitorNew = competionBuilder.buildCompetitor(name, age,competitor_gender,acceleration, maxSpeed, competitor_discipline, competitorPath);
                    competitorNew.initRace();
                    competition.addCompetitor(competitorNew);
                    sportsmens.add(competitorNew);
                } catch (Exception ex4) {
                    System.out.println(ex4);
                }
                String path_gender=convert(chosenGender);
                competitorsImages[competitorsNumber-1] = new ImageIcon(new ImageIcon("src/icons/" + chosenTypeComp + path_gender + ".png").getImage()
                        .getScaledInstance(70, 70, Image.SCALE_DEFAULT));
                updateFrame();
                break;

            case "Start competition":
                if (arena == null || competition == null || competitorsNumber==0) {
                    JOptionPane.showMessageDialog(this, "Please build arena, create competition and add competitors!");
                    return;
                }
                if (competitionStarted) {
                        JOptionPane.showMessageDialog(this, "Competition finished! Please build a new arena and add competitors.");
                        return;
                }
                if (competitionFinished) {
                        JOptionPane.showMessageDialog(this, "Competition already started!");
                        return;
                }
                try {
                    competitionStarted = true;
                    (new Thread(() -> {
                        while (competition.hasActiveCompetitors()) {
                            try {
                                Thread.sleep(30);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            updateFrame();
                        }
                        updateFrame();
                        competitionFinished = true;
                    })).start();

                    GameEngine gameEngine =GameEngine.getInstance();
                    gameEngine.startRace(competition);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                break;
            case "Show info":
                if (arena == null || competition == null || competitorsNumber==0) {
                    JOptionPane.showMessageDialog(this, "Please build arena, create competition and add competitors!");
                    return;
                }
                    String[] columnNames = {"Name", "Speed", "Max speed", "Location", "Finished"};
                    String[][] data = new String[competitorsNumber][5];
                    int i = 0;

                    for (Sportsman c : sportsmens) {
                        data[i][0] = c.getName();
                        data[i][1] = "" + c.getSpeed();
                        data[i][2] = "" + c.getMaxSpeed();
                        data[i][3] = "" + c.getLocation().getX();
                        data[i][4] = String.valueOf(arena.isFinished(c));
                        i++;
                    }

                    JTable table = new JTable(data, columnNames);
                    table.setPreferredScrollableViewportSize(table.getPreferredSize());
                    JScrollPane scrollWindow = new JScrollPane(table);

                    JPanel jpanel_table = new JPanel();
                    jpanel_table.add(scrollWindow);

                    if (infoTable != null)
                        infoTable.dispose();
                    infoTable = new JFrame("Competitors information");
                    infoTable.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    infoTable.setContentPane(jpanel_table);
                    infoTable.pack();
                    infoTable.setVisible(true);
                    break;
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
