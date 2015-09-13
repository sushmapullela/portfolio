package com.samplegame;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class PlayGame {

	private static JFrame guiFrame;
	private static JFrame mainFrame;
	private static Player mainPlayer;
	private static final String STRPATH = System.getProperty("user.dir");
	//private static final String PATH = System.getProperty("user.dir")
			//+ "\\images\\";
	private static final String PATH = "C:\\Users\\swathi\\Desktop\\FinalProject\\images\\";
	private static final String IMAGE_EXTENSION = ".jpg";
	private static final List<String> orderedImages = new ArrayList<String>(10);
	public static ArrayList<Integer> arryR;
	public static ArrayList<JLabel> arryLabels;
	public static ArrayList<Player> highScores;
	private static int imgCounter = 0;
	private static int score = 0;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainScreen();
			}

			private void mainScreen() {
				getInfoFromFile();
				mainFrame = new JFrame();
				mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				mainFrame.setTitle("Simple Game");
				mainFrame.setSize(300, 200);
				mainFrame.setResizable(false);
				mainFrame.setLocationRelativeTo(null);
				JButton buttonPlay = new JButton("PLAY GAME");
				buttonPlay.addActionListener(new ActionListener() {

					private JFrame playerFrame;

					@Override
					public void actionPerformed(ActionEvent e) {
						mainFrame.setVisible(false);
						createPlayer();
					}

					private void createPlayer() {
						playerFrame = new JFrame();
						playerFrame
								.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						playerFrame.setTitle("Create Player");
						playerFrame.setSize(400, 80);
						playerFrame.setResizable(false);
						playerFrame.setLocationRelativeTo(null);
						JPanel cpanel = new JPanel();
						cpanel.add(new JLabel("Player Name"));
						final JTextField playerName = new JTextField(10);
						JButton createButton = new JButton("CREATE");
						createButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent arg0) {
								if (playerName.getText().equals("")) {

								} else {
									Player playerDetails = new Player(
											playerName.getText(), 0);
									mainPlayer = playerDetails;
									createImgArray();
									
									showPage();
									playerFrame.setVisible(false);
								}
							}
						});
						cpanel.add(playerName);
						cpanel.add(createButton);
						playerFrame.add(cpanel);
						playerFrame.setVisible(true);
					}
				});
				JButton buttonHighScore = new JButton("HIGH SCORES");
				buttonHighScore.addActionListener(new ActionListener() {

					private JFrame highFrame;

					@Override
					public void actionPerformed(ActionEvent e) {
						getInfoFromFile();
						highFrame = new JFrame();
						highFrame
								.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						highFrame.setTitle("Create Player");
						highFrame.setSize(250, 350);
						highFrame.setResizable(false);
						highFrame.setLocationRelativeTo(null);
						JPanel panel = new JPanel();
						panel.setLayout(new GridLayout(12, 1));
						JLabel l = new JLabel("HIGH SCORES");
						l.setHorizontalAlignment(JTextField.CENTER);
						panel.add(l);
						if (highScores.size() == 0) {
							l = new JLabel("NO HIGH SCORES");
							l.setHorizontalAlignment(JTextField.CENTER);
							panel.add(l);
						} else {
							for (int i= highScores.size()-1;i>=0;i--) {
								JPanel highPanel= new JPanel();
								highPanel.setLayout(new GridLayout(1, 2));
								l = new JLabel("" + highScores.get(i).getName());
								l.setHorizontalAlignment(JTextField.CENTER);
								highPanel.add(l);
								l = new JLabel("" + highScores.get(i).getScore());
								l.setHorizontalAlignment(JTextField.CENTER);
								highPanel.add(l);
								panel.add(highPanel);
							}
						}
						JButton exit = new JButton("EXIT");
						exit.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent arg0) {
								highFrame.dispose();
							}
						});
						panel.add(exit);
						highFrame.add(panel);
						highFrame.setVisible(true);
					}
				});
				JButton buttonExit = new JButton("EXIT");
				buttonExit.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						mainFrame.dispose();
					}
				});
				JPanel panel = new JPanel();
				GridLayout experimentLayout = new GridLayout(3, 1);
				panel.setLayout(experimentLayout);
				panel.add(buttonPlay);
				panel.add(buttonHighScore);
				panel.add(buttonExit);
				mainFrame.add(panel);
				mainFrame.setVisible(true);
			}
		});
	}

	protected static void getInfoFromFile() {
		highScores = new ArrayList<Player>();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(STRPATH
					+ "//highScores.txt");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				String[] split = strLine.split(" ");
				highScores
						.add(new Player(split[0], Integer.parseInt(split[1])));
			}
			// Close the input stream
			in.close();
			insertionSort();
		} catch (Exception e) {// Catch exception if any
		}
	}

	private static void addLeftGridAndImage() {
		JPanel optionPanel = new JPanel();
		Border outline = BorderFactory.createLineBorder(Color.black);
		optionPanel.setBorder(outline);
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		GridLayout grid = new GridLayout(10, 10);
		grid.setHgap(20);
		grid.setVgap(2);
		optionPanel.setLayout(grid);
		int randomNum, count = 0;
		List<Integer> position = new ArrayList<Integer>();

		String image = orderedImages.get(imgCounter);
		ImageIcon guy = new ImageIcon(PATH + image + IMAGE_EXTENSION);
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JLabel(guy));

		guiFrame.add(buttonPanel, BorderLayout.EAST);

		randomNum = (int) (Math.random() * 10);

		if (randomNum % 2 == 0) {
			for (;;) {
				randomNum = (int) (Math.random() * 100);
				if ((randomNum / 10) < 10) {
					if (((randomNum % 10) + image.length()) <= 10) {
						for (int i = 0; i < image.length(); i++) {
							position.add(randomNum + i);
						}
						break;
					}
				}
			}
		} else {
			for (;;) {
				randomNum = (int) (Math.random() * 100);
				if ((randomNum / 10) + image.length() < 10) {
					for (int i = 0; i < image.length(); i++) {
						position.add(randomNum + 10 * i);
					}
					break;
				}
			}
		}

		for (;;) {
			if (position.contains(count)) {
				JLabel addLabel = new JLabel(image.charAt(position
						.indexOf(count)) + "");
				optionPanel.add(addLabel);
				arryLabels.add(addLabel);
				count++;
			} else {
				randomNum = (int) (Math.random() * 100);
				if (randomNum >= 0 && randomNum <= 25) {
					optionPanel.add(new JLabel(Alphabets.values()[randomNum].toString()));
					count++;
				}
			}
			if (count == 100) {
				break;
			}
		}
		guiFrame.add(optionPanel, BorderLayout.WEST);
		JPanel panel = new JPanel();
		guiFrame.add(panel, BorderLayout.SOUTH);
	}

	private static void playSound(String fileName) {
		try {
			Applet.newAudioClip(new URL("file:" + PATH + fileName)).play();
		} catch (MalformedURLException e) {
			// TODO
		}
	}

	private static void addButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(6, 1));
		JLabel answerlable= new JLabel("ENTER YOUR ANSWER");
		answerlable.setHorizontalAlignment(JLabel.CENTER);
		buttonPanel.add(answerlable);
		final JTextField answer = new JTextField(10);
		JButton nextButton = new JButton("Next");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (imgCounter != 10) {
					if (orderedImages.get(imgCounter - 1).equalsIgnoreCase(
							answer.getText())) {
						playSound("Trumpet1.wav");
						guiFrame.dispose();
						score++;
						showPage();
					} else {
						playSound("lost.wav");
						String warningMsg;
						if (answer.getText().trim().isEmpty()) {
							warningMsg = "Please enter your answer into Textbox and click next button.";
							JOptionPane.showMessageDialog(guiFrame, warningMsg);
						} else {
							warningMsg = "Your Answer is wrong.";
							for (int i = 0; i < arryLabels.size(); i++) {
								JLabel lbl = (JLabel) arryLabels.get(i);
								lbl.setForeground(Color.BLUE);
							}
							JOptionPane.showMessageDialog(guiFrame, warningMsg);
							guiFrame.dispose();
							showPage();
						}
					}
				} else if (imgCounter == 10) {
					addScoresToFile();
					guiFrame.dispose();
					guiFrame = new JFrame();
					guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					guiFrame.setTitle("Sample Game");
					guiFrame.setSize(500, 100);
					guiFrame.setResizable(false);
					guiFrame.setLocationRelativeTo(null);
					JPanel panel = new JPanel();
					panel.add(new JLabel(
							"You have successfully completed the game.\n Your Score is "
									+ score + "/10"));
					JButton closeBUtton = new JButton("Close");
					closeBUtton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent event) {
							guiFrame.dispose();
							mainFrame.setVisible(true);
						}
					});				
					panel.add(closeBUtton);
					guiFrame.add(panel, BorderLayout.CENTER);
					guiFrame.setVisible(true);
				}
			}

			private void addScoresToFile() {
				try {
					// Create file
					File f = new File(STRPATH + "//highScores.txt");
					if (f.exists())
						f.delete();
					FileWriter fstream = new FileWriter(STRPATH
							+ "//highScores.txt");
					BufferedWriter out = new BufferedWriter(fstream);
					highScores.add(new Player(mainPlayer.getName(), score));
					insertionSort();
					if (highScores.size() == 0) {
						out.write(mainPlayer.getName() + " " + score);
						out.newLine();
					} else {
						int count = 0;
						for (int i =highScores.size()-1;i>=0; i--) {

							out.write(highScores.get(i).getName() + " "
									+ highScores.get(i).getScore());
							out.newLine();
							count++;
							if (count == 10)
								break;
						}

					}
					// Close the output stream
					out.close();
					fstream.close();
				} catch (Exception e) {// Catch exception if any
				}
			}
		});
		buttonPanel.add(answer);
		buttonPanel.add(nextButton);
		buttonPanel.add(new JLabel(" "));
		JPanel scorePanel= new JPanel();
		scorePanel.setLayout(new GridLayout(2,1));
		JLabel liveScores = new JLabel();
		liveScores.setText("SCORE");
		liveScores.setFont(new Font("TIMES NEW ROMAN", 27, 27));
		liveScores.setHorizontalAlignment(JTextField.CENTER);
		scorePanel.add(liveScores);
		liveScores = new JLabel();
		liveScores.setText(score + "");
		liveScores.setFont(new Font("TIMES NEW ROMAN", 27, 27));
		liveScores.setHorizontalAlignment(JTextField.CENTER);
		scorePanel.add(liveScores);
		buttonPanel.add(scorePanel);
		guiFrame.add(buttonPanel, BorderLayout.CENTER);
	}
    public static void insertionSort (){
        for (int i = 0; i < highScores.size(); i++)
        {
            int value = highScores.get(i).getScore(), j = i-1;
            String name=highScores.get(i).getName();
            while (j >= 0 && highScores.get(j).getScore() > value)
            {
            	highScores.get(j+1).setScore(highScores.get(j).getScore());
            	highScores.get(j+1).setName(highScores.get(j).getName());
                j--;
            }
            highScores.get(j+1).setScore(value);
            highScores.get(j+1).setName(name);
        }
    }
	private static void createImgArray() {
		imgCounter=0;
		orderedImages.clear();
		int randomNum;
		for (;;) {
			randomNum = (int) (Math.random() * 10);
			if (!orderedImages.contains(Images.values()[randomNum].toString())) {
				orderedImages.add(Images.values()[randomNum].toString());
				if (orderedImages.size() == 10)
					break;
			}
		}
	}

	private static void showPage() {
		arryR = new ArrayList<Integer>();
		arryLabels = new ArrayList<JLabel>();
		guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Simple Game");
		guiFrame.setSize(800, 350);
		guiFrame.setResizable(false);
		guiFrame.setLocationRelativeTo(null);
		JPanel scorePnael = new JPanel();
		JLabel title = new JLabel("SEE, THINK , IDENTIFY & TYPE MATCHING WORD");
		title.setHorizontalAlignment(JTextField.CENTER);
		scorePnael.add(title);
		guiFrame.add(scorePnael, BorderLayout.NORTH);
		addLeftGridAndImage();
		imgCounter++;
		addButtonPanel();
		guiFrame.setVisible(true);
	}

	public PlayGame(Player playerDetails) {
		
		mainPlayer = playerDetails;
		createImgArray();
		showPage();
	}

}


