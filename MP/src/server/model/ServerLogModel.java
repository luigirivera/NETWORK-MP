package server.model;

import server.view.ServerLogObserver;

public interface ServerLogModel {
	public void updateAll();
	public void attach(ServerLogObserver obs);
	public void detach(ServerLogObserver obs);
	public String getLast();
	public void log(String text);
}
