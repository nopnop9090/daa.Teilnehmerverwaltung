package com.nopnop9090.daa.Teilnehmerverwaltung;

public class TeilnehmerController {

	private TeilnehmerModel model;
	private TeilnehmerView view;
	
	public TeilnehmerController(TeilnehmerModel model, TeilnehmerView view) {
		this.model = model;
		this.view = view;
		view.setController(this);
	}
}
