package db;

import org.bson.Document;

import fitbit.FitbitAccount;

public class FitbitAccountDB implements DB<FitbitAccount> {
	
	@Override
	public Document toDocument(FitbitAccount fitbitAccount) {
		if (fitbitAccount == null) return new Document();
		
		return new Document("associated-username", fitbitAccount.getAssociatedUsername())
					.append("access-token", fitbitAccount.getAccessToken())
					.append("refresh-token", fitbitAccount.getRefreshToken())
					.append("scope", fitbitAccount.getScope());
	}
	
	@Override
	public FitbitAccount fromDocument(Document document) {
		if (document.isEmpty()) return null;
		
		return new FitbitAccount(document.getString("associated-username"), document.getString("access-token"),
				document.getString("refresh-token"), document.getString("scope"));
	}
	
}
