package game;

import game.arena.WinterArena;
import game.competition.Competition;
import game.competition.Competitor;
import utilities.ValidationUtils;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameEngine {

	private static GameEngine instance;

	/**.
	 * @return singleton instance of the game engine
	 */
	public static GameEngine getInstance() {
		if (instance == null) {
			instance = new GameEngine();
		}
		return instance;
	}

	/**
	 * private empty Ctor for game engine
	 */
	private GameEngine() {
	}

	/**
	 * Start a race at a competition
	 * This method will play competition turns until finished then print the results.
	 * @param competition The competition to be run
	 */
	public void startRace(Competition competition) throws InterruptedException {
		ValidationUtils.assertNotNull(competition);
		ExecutorService executorService;
		synchronized (competition.getActiveCompetitors()) {
			executorService = Executors.newFixedThreadPool(competition.getActiveCompetitors().size());
			synchronized (this) {
				for (Competitor competitor : competition.getActiveCompetitors()) {
					executorService.execute((Runnable) competitor);
				}
			}
		}
	}

	/**
	 * print the game results
	 */
	private void printResults(Competition competition){
		System.out.println("Race results:");
		int place = 1;
		for(Competitor skier : competition.getFinishedCompetitors()){
			System.out.println(place + ". " + skier);
			place++;
		}
	}
}
