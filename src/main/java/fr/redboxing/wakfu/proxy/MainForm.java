package fr.redboxing.wakfu.proxy;

import fr.redboxing.wakfu.proxy.network.packets.Packet;

import javax.swing.*;
import javax.swing.table.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * @author RedBoxing
 */
public class MainForm extends JFrame {
    private JScrollPane scrollPane1;
    private JTextArea log;
    private JButton startbtn;
    private JScrollPane scrollPane2;
    private JTable table1;
    private JButton startGamebtn;

    private DefaultTableModel tableModel;

    private boolean proxyStarted = false;

    public MainForm() {
        setTitle("Wakfu Proxy");
        setSize(600, 315);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        startbtn = new JButton();
        startbtn.setText("Start Proxy");
        startbtn.setSize(125, 50);
        startbtn.setLocation(10, 10);
        startbtn.addActionListener(e -> {
            if(proxyStarted) {
                startbtn.setEnabled(false);
                stopProxy();
                proxyStarted = false;
                startbtn.setText("Start Proxy");
                startbtn.setEnabled(true);
            }else {
                startbtn.setEnabled(false);
                startProxy();
                proxyStarted = true;
                startbtn.setText("Stop Proxy");
                startbtn.setEnabled(true);
            }
        });
        panel.add(startbtn);

        startGamebtn = new JButton();
        startGamebtn.setText("Launch Wakfu");
        startGamebtn.setSize(125, 50);
        startGamebtn.setLocation(10, 67);
        startGamebtn.addActionListener(e -> launchWakfu());
        panel.add(startGamebtn);

        log = new JTextArea();
        log.setEditable(false);

        scrollPane1 = new JScrollPane();
        scrollPane1.setSize(445, 108);
        scrollPane1.setLocation(140, 10);
        scrollPane1.setViewportView(log);
        panel.add(scrollPane1);

        tableModel = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                        "Source", "Opcode", "Name", "Data"
                });

        table1 = new JTable();
        table1.setModel(tableModel);

        scrollPane2 = new JScrollPane();
        scrollPane2.setSize(575, 150);
        scrollPane2.setLocation(10, 130);
        scrollPane2.setViewportView(table1);
        panel.add(scrollPane2);

        setContentPane(panel);
        setVisible(true);
    }

    public void startProxy() {
        new Thread(() -> WakfuProxy.getInstance().start()).start();
        new Thread(() -> WakfuProxy.getInstance().startGameProxy()).start();
    }

    public void stopProxy() {
        new Thread(() -> WakfuProxy.getInstance().stop()).start();
        new Thread(() -> WakfuProxy.getInstance().stopGameProxy()).start();
    }

    public void launchWakfu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                WakfuProxy.getInstance().log("Launching Wakfu.");

                try {
                    String home = System.getProperty("user.home");
                    String wakfuDir = home + "/AppData/Local/Ankama/zaap-steam/wakfu";
                    String cpu = "x64";

                    String xms = "512m";
                    String xmx = "2048m";

                    String[] options = new String[]{
                            "-XX:+UnlockExperimentalVMOptions",
                            "-XX:+UseG1GC",
                            "-XX:G1NewSizePercent=20",
                            "-XX:G1ReservePercent=20",
                            "-Djava.net.preferIPv4Stack=true",
                            "-Dsun.awt.noerasebackground=true",
                            "-Dsun.java2d.noddraw=true",
                            "-Djogl.disable.openglarbcontext",
                    };

                    ArrayList<String> args = new ArrayList<>();
                    args.add(String.format("%s/jre/win32/%s/bin/java.exe", wakfuDir, cpu));

                    args.add("-Xms" + xms);
                    args.add("-Xmx" + xmx);

                    args.addAll(Arrays.asList(options));

                    args.add("-Djava.library.path=natives/win32/" + cpu);

                    args.add("-cp");
                    args.add(wakfuDir + "/lib/*");

                    args.add("com.ankamagames.wakfu.client.WakfuClient");

                    ProcessBuilder builder = new ProcessBuilder();
                    builder.directory(new File(wakfuDir));
                    builder.command(args);
                    Map<String, String> env = builder.environment();
                    env.put("WAKFU_LANGUAGE", "fr");
                    env.put("WAKFU_CONFIG_FILE_PATH", new File("config.properties").getAbsolutePath());
                    env.put("WAKFU_PREF_FILE_DIRECTORY", new File(MainForm.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getAbsolutePath());
                    env.put("WAKFU_CACHE_FILE_DIRECTORY", new File("cache/").getAbsolutePath());

                    Process p = builder.start();

                    try (BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                        String line;

                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void addPacketToTable(Class<? extends Packet> message, Packet packet) {
        int index = tableModel.getRowCount();
        tableModel.insertRow(index, new Object[]{ packet.packetType, packet.opcode, message.getSimpleName(), packet.toString()});
    }

    public JTextArea getLogArea() {
        return log;
    }
}
