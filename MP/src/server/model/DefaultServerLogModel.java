package server.model;

import java.util.ArrayList;
import java.util.List;

import server.view.ServerLogObserver;

public class DefaultServerLogModel implements ServerLogModel {
	private String lastLog;
	private List<ServerLogObserver> observers;
	private List<ServerLogObserver> toAdd;
	private List<ServerLogObserver> toRemove;

	public DefaultServerLogModel() {
		this.lastLog = "";
		this.observers = new ArrayList<ServerLogObserver>();
		this.toAdd = new ArrayList<ServerLogObserver>();
		this.toRemove = new ArrayList<ServerLogObserver>();
	}

	@Override
	public void updateAll() {
		this.observers.addAll(toAdd);
		toAdd.clear();
		this.observers.removeAll(toRemove);
		toRemove.clear();
		for (ServerLogObserver obs : observers)
			obs.updateLog();
	}

	@Override
	public void attach(ServerLogObserver obs) {
		this.toAdd.add(obs);
	}

	@Override
	public void detach(ServerLogObserver obs) {
		this.toRemove.add(obs);
	}

	@Override
	public String getLast() {
		return lastLog;
	}

	@Override
	public void log(String text) {
		this.lastLog = text;
		System.out.println(text);
		this.updateAll();
	}

}
