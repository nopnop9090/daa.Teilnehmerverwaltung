package com.nopnop9090.daa.Teilnehmerverwaltung;

public class Teilnehmer {
	private int id;
	private String Gruppe;
	private String Name;
	private String Vorname;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGruppe() {
		return Gruppe;
	}
	public void setGruppe(String gruppe) {
		Gruppe = gruppe;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getVorname() {
		return Vorname;
	}
	public void setVorname(String vorname) {
		Vorname = vorname;
	}
	
	Teilnehmer(int id, String Gruppe, String Name, String Vorname) {
		this.setId(id);
		this.setGruppe(Gruppe);
		this.setName(Name);
		this.setVorname(Vorname);
	}
	
    @Override
    public String toString() {
        return id + " - " + Name + ", " + Vorname;
    }
}
