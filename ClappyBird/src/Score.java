
public class Score implements Comparable<Score>{

	public String name;
	public int score;
	
	public Score(String name, int score){
		this.name = name;
		this.score = score;
	}

	@Override
	public int compareTo(Score s) {
		
		return s.score-this.score;
	}
}
