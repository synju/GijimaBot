package com.webfarming;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.net.URLConnection;

public class App implements ActionListener {
	// Timer
	Timer timer = new Timer(15000, this);

	JButton button;
	JLabel label;
	ImageIcon icon;

	// Background
	ImageIcon startingImage = new ImageIcon("./res/starting.jpg");
	ImageIcon downImage = new ImageIcon("./res/down.jpg");
	ImageIcon liveImage = new ImageIcon("./res/live.jpg");
	JLabel background = new JLabel();

	int count = 0;
	boolean running = false;

	public App() {
		// Timer
		timer.setActionCommand("netAvailable");
		timer.setInitialDelay(30000);
		timer.start();

		JFrame frame = new JFrame();
		JPanel panel = new JPanel();

		panel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		panel.setLayout(new GridLayout(0, 1));

		button = new JButton("Start GijimaBot");
		button.addActionListener(this);
		this.label = new JLabel("GijimaBot Offline");
		icon = new ImageIcon("./res/icon.png");
		frame.setIconImage(icon.getImage());

		background.setIcon(startingImage);
		panel.add(background);

		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GijimaBot for Telegram - A Webfarming Product");
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(450, 150));
		frame.setLocation(100, 100);
		frame.pack();
		frame.setVisible(true);

		runBot();
	}

	public void runBot() {
		if(netAvailable()) {
			try {
				TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
				telegramBotsApi.registerBot(new WebfarmingBot());
			}
			catch(TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean netAvailable() {
		System.out.println("checking internet access...");
		boolean netAvailable = false;
		try {
			final URL url = new URL("https://www.google.com/");
			final URLConnection conn = url.openConnection();
			conn.connect();
			conn.getInputStream().close();

			netAvailable = true;
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		if(netAvailable) {
			background.setIcon(liveImage);
			System.out.println("online");
		}
		else {
			background.setIcon(downImage);
			System.out.println("offline");
		}

		// Restart timer
		timer.restart();

		return netAvailable;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(e.getActionCommand().equals("netAvailable")) {
			netAvailable();
		}
	}
}
