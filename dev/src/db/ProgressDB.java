package db;

import org.bson.Document;

import model.BooleanProgress;
import model.PercentageProgress;
import model.Progress;

public class ProgressDB implements DB<Progress> {
	
	@Override
	public Document toDocument(Progress progress) {
		String progressType = null;
		if (progress instanceof BooleanProgress) {
			progressType = "boolean";
		} else if (progress instanceof PercentageProgress) {
			progressType = "percentage";
		} else { // level progress type not implemented yet
			return null;
		}
		
		return new Document("progress-type", progressType)
					.append("progress-value", progress.getProgress());
	}
	
	@Override
	public Progress fromDocument(Document document) {
		String progressType = document.getString("progress-type");
		double progressValue = document.getDouble("progress-value");
		
		Progress progress = null;
		if (progressType.equals("boolean")) {
			progress = new BooleanProgress();
			progress.setProgress(progressValue);
		} else if (progressType.equals("percentage")) {
			progress = new PercentageProgress();
			progress.setProgress(progressValue);
		} else { // level progress type not implemented yet
			return null;
		}
		
		return progress;
	}
	
}
