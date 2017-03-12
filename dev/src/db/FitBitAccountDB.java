package db;

import org.bson.Document;

import fitbit.FitBitAccount;

public class FitBitAccountDB implements DB<FitBitAccount> {
	
	@Override
	public Document toDocument(FitBitAccount fitBitAccount) {
		if (fitBitAccount == null) return new Document();
		
		return new Document("fitbit-user-id", fitBitAccount.getUserId())
					.append("fitbit-access-token", fitBitAccount.getAccessToken())
					.append("fitbit-refresh-token", fitBitAccount.getRefreshToken())
					.append("fitbit-scope", fitBitAccount.getScope())
					.append("fitbit-token-type", fitBitAccount.getTokenType())
					.append("fitbit-expires-in", fitBitAccount.getExpiresIn());
	}
	
	@Override
	public FitBitAccount fromDocument(Document document) {
		if (document.isEmpty()) return null;
		
		return new FitBitAccount(document.getString("fitbit-access-token"),
				document.getString("fitbit-refresh-token"), document.getString("fitbit-user-id"),
				document.getString("fitbit-scope"), document.getString("fitbit-token-type"),
				document.getLong("fitbit-expires-in"));
	}
	
}
