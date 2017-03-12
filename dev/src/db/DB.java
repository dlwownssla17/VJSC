package db;

import org.bson.Document;

public interface DB<T> {
	public Document toDocument(T entity);
	
	public T fromDocument(Document document);
}
