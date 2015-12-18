package fr.xs.jtk.beans.universe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringTokenizer;

import fr.xs.jtk.helpers.MediaHelper;

public class AstralCorpseBean {

	String name, parent;
	
	double radius;
	double periastre, apoastre;
	double high_axe, small_axe;
	double atmosphere_radius; // from ground
	double radiation_radius; // from ground
	
	double disc_min, disc_max;

	double inclinaison_axe, inclinaison_orbit;
	
	double orbital_period, self_period;

	public AstralCorpseBean() {
		name              = "Nemesis";
		parent            = "None";
		radius            = 666.666;
		periastre         = 1e6;
		apoastre          = 1e6;
		high_axe          = 1e6;
		small_axe         = 1e6;
		atmosphere_radius = 0.0;
		radiation_radius  = 0.0;
		disc_min          = 0.0;
		disc_max          = 0.0;
		inclinaison_axe   = 0.0;
		inclinaison_orbit = 0.0;
		orbital_period    = 365.0;
		self_period       = 24.0;
	}
	public AstralCorpseBean(String _name, double _radius) {
		this();
		name              = _name;
		radius            = _radius;
	}

	public void load(URL path) {
		try {
			InputStream is = path.openStream();
			
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
				String line;
				while ((line = br.readLine()) != null) {
					if(line.length() > 0 && line.charAt(0) != '#') {
						StringTokenizer tokens = new StringTokenizer(line, " ");
						if(tokens.countTokens() >= 2) {
							String key   = tokens.nextToken();
							String value = tokens.nextToken();
							
							switch(key) {
							case "newac" : 	name              = value; break;
							case "Ac" : 	parent            = value; break;
							case "Kr" : 	radius            = Double.parseDouble(value); break;
							case "Kp" : 	periastre         = Double.parseDouble(value); break;
							case "Ka" : 	apoastre          = Double.parseDouble(value); break;
							case "Kh" : 	high_axe          = Double.parseDouble(value); break;
							case "Ks" : 	small_axe         = Double.parseDouble(value); break;
							case "Ar" : 	atmosphere_radius = Double.parseDouble(value); break;
							case "Rr" : 	radiation_radius  = Double.parseDouble(value); break;
							case "Dm" : 	disc_min          = Double.parseDouble(value); break;
							case "DM" : 	disc_max          = Double.parseDouble(value); break;
							case "Ia" : 	inclinaison_axe   = Double.parseDouble(value); break;
							case "Io" : 	inclinaison_orbit = Double.parseDouble(value); break;
							case "To" : 	orbital_period    = Double.parseDouble(value); break;
							case "Ts" : 	self_period       = Double.parseDouble(value); break;
							}
						}
					}
				}
			}
			
		} catch(IOException e) { e.printStackTrace(); }
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getPeriastre() {
		return periastre;
	}

	public void setPeriastre(double periastre) {
		this.periastre = periastre;
	}

	public double getApoastre() {
		return apoastre;
	}

	public void setApoastre(double apoastre) {
		this.apoastre = apoastre;
	}

	public double getHigh_axe() {
		return high_axe;
	}

	public void setHigh_axe(double high_axe) {
		this.high_axe = high_axe;
	}

	public double getSmall_axe() {
		return small_axe;
	}

	public void setSmall_axe(double small_axe) {
		this.small_axe = small_axe;
	}

	public double getAtmosphere_radius() {
		return atmosphere_radius;
	}

	public void setAtmosphere_radius(double atmosphere_radius) {
		this.atmosphere_radius = atmosphere_radius;
	}

	public double getRadiation_radius() {
		return radiation_radius;
	}

	public void setRadiation_radius(double radiation_radius) {
		this.radiation_radius = radiation_radius;
	}

	public double getDisc_min() {
		return disc_min;
	}

	public void setDisc_min(double disc_min) {
		this.disc_min = disc_min;
	}

	public double getDisc_max() {
		return disc_max;
	}

	public void setDisc_max(double disc_max) {
		this.disc_max = disc_max;
	}

	public double getInclinaison_axe() {
		return inclinaison_axe;
	}

	public void setInclinaison_axe(double inclinaison_axe) {
		this.inclinaison_axe = inclinaison_axe;
	}

	public double getInclinaison_orbit() {
		return inclinaison_orbit;
	}

	public void setInclinaison_orbit(double inclinaison_orbit) {
		this.inclinaison_orbit = inclinaison_orbit;
	}

	public double getOrbital_period() {
		return orbital_period;
	}

	public void setOrbital_period(double orbital_period) {
		this.orbital_period = orbital_period;
	}

	public double getSelf_period() {
		return self_period;
	}

	public void setSelf_period(double self_period) {
		this.self_period = self_period;
	}

	public String toString() {
		String toStr = "";
		toStr += "Name=" + name + "\n";
		toStr += "parent=" + parent + "\n";
		toStr += "radius=" + radius + "\n";
		toStr += "periastre=" + periastre + "\n";
		toStr += "apoastre=" + apoastre + "\n";
		toStr += "high_axe=" + high_axe + "\n";
		toStr += "small_axe=" + small_axe + "\n";
		toStr += "atmosphere_radius=" + atmosphere_radius + "\n";
		toStr += "radiation_radius=" + radiation_radius + "\n";
		toStr += "disc_min=" + disc_min + "\n";
		toStr += "disc_max=" + disc_max + "\n";
		
		return toStr;
	}
		
}
