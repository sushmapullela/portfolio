package com.samplegame;

public class Player {

	private String Name;
	private int Score;
	public Player(String text, int i) {
		this.setName(text);
		this.setScore(i);
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getScore() {
		return Score;
	}
	public void setScore(int score) {
		Score = score;
	}

}

