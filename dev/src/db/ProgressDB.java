package db;

import org.bson.Document;

import model.BooleanProgress;
import model.PercentageProgress;
import model.Progress;

public class ProgressDB implements DB<Progress> {
	
	@Override
	public Document toDocument(Progress progress) {
		// level progress type not implemented yet
		String progressType = progress instanceof BooleanProgress ?
				"boolean" : (progress instanceof PercentageProgress ? "percnetage" : null);
		
		return new Document("progress-type", progressType)
					.append("progress-value", progress.getProgress());
	}
	
	@Override
	public Progress fromDocument(Document document) {
		String progressType = document.getString("progress-type");
		double progressValue = document.getDouble("progress-value");
		
		// level progress type not implemented yet
		Progress progress = progressType.equals("boolean") ?
				new BooleanProgress() : (progressType.equals("percentage") ? new PercentageProgress() : null);
		progress.setProgress(progressValue);
		
		return progress;
	}
	
}
