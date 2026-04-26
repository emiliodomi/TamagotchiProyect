package tamagotchi_proyect;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Pet {
	//atributos
	private String especie;
	private String nombre;
	private int peso_min;
	private int peso_max;
	private int peso;
	private int vida_max;
	private int vida;
	private int salud_max = 100;
	private int salud;
	private boolean enfermo;
	private int hambre;
	private int sed;
	private int aburrimiento;
	private int sueño;
	private boolean dormido = false;
	private int tiempo_dormido = 0;
	private int edad;
	private int tiempo = 0;
	private Random probabilidad = new Random();
	private int suerte;
	
	
	protected Pet(String especie, String nombre, int peso_min, int peso_max, int peso, int vida_max, int vida,
			int salud_max, int salud, int hambre, int sed, int aburrimiento, int sueño, boolean dormido,
			int tiempo_dormido, int edad, int tiempo, Random probabilidad, int suerte) {
		super();
		this.especie = especie;
		this.nombre = nombre;
		this.peso_min = peso_min;
		this.peso_max = peso_max;
		this.peso = peso;
		this.vida_max = vida_max;
		this.vida = vida;
		this.salud_max = salud_max;
		this.salud = salud;
		this.hambre = hambre;
		this.sed = sed;
		this.aburrimiento = aburrimiento;
		this.sueño = sueño;
		this.dormido = dormido;
		this.tiempo_dormido = tiempo_dormido;
		this.edad = edad;
		this.tiempo = tiempo;
		this.probabilidad = probabilidad;
		this.suerte = suerte;
	}
	
	protected void Suerte() {
		this.suerte = probabilidad.nextInt(100);
	}

	protected void pasarTiempo() {
		Suerte();
		if(suerte < 20) {
			this.salud -= 3;
			this.hambre += 5;
			this.sed += 5;
			this.aburrimiento += 5;
			this.sueño += 5;
			this.aburrimiento += 5;
		}else {
			this.salud -= 1;
			this.hambre += 3;
			this.sed += 3;
			this.aburrimiento += 3;
			this.sueño += 3;
			this.aburrimiento += 3;
		}
		this.tiempo += 1;
		
		if (this.dormido == true) {
			this.tiempo_dormido += 1;
		}
		
		if(tiempo == 100) {
			cumpleaños();
			tiempo = 0;
		}
	}
	
	public void CicloVida() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(() -> {
			this.pasarTiempo();
		},0 , 10, TimeUnit.SECONDS);
	}
	
	protected void cumpleaños() {
		this.edad += 1;
		cambiar_peso_max_min();
		cambiar_vida_max();
	}

	protected void cambiar_peso_max_min() {
		peso_max += 10;
		peso_min += 10;
	}

	protected void sumar_peso(int peso_sumada) {
		if(this.peso < peso_max) {
			this.peso += peso_sumada;
		}
	}
	
	protected void restar_peso(int peso_restada) {
		if(this.peso > peso_min && this.suerte < 15) {
			this.peso -= peso_restada - 10;
		}else {
			if(this.peso > peso_min) {
				this.peso -= peso_restada;
			}
		}
	}
	
	protected void cambiar_vida_max() {
		if (this.edad < 4) {
			this.vida_max += 10;
		}else {
			if(this.edad > 6) {
				this.vida_max -= 10;
			}
		}
	}
	
	protected void restar_vida(int vida_restada) {
		if(this.vida >= 0) {
			this.vida -= vida_restada;
		}
	}
	
	protected void sumar_vida(int vida_sumada) {
		if(this.vida + vida_sumada <= vida_max) {
			this.vida += vida_sumada;
		}
	}
	
	protected void sumar_hambre(int hambre_sumada) {
		if(this.hambre < 100 && this.suerte <= 30) {
			this.hambre += hambre_sumada + 20;
		}else {
			if(this.hambre < 100) {
				this.hambre += hambre_sumada;
			}
		}
	}
	
	protected void restar_hambre(int hambre_restada) {
		if(this.hambre > 0) {
			this.hambre -= hambre_restada;
		}
	}
	
	protected void sumar_sed(int sed_sumada) {
		if(this.sed < 100 && this.suerte <= 35 ) {
			this.sed += sed_sumada + 25;
		}else {
			if(this.sed < 100) {
				this.sed += sed_sumada;
			}
		}
	}
	
	protected void restar_sed(int sed_restada) {
		if(this.sed > 0) {
			this.sed -= sed_restada;
		}
	}
	
	protected void sumar_aburrimiento(int aburrimiento_sumada) {
		if(this.aburrimiento < 100) {
			this.aburrimiento += aburrimiento_sumada;
		}
	}
	
	protected void restar_aburrimiento(int aburrimiento_restada) {
		if(this.aburrimiento > 0) {
			this.aburrimiento -= aburrimiento_restada;
		}
	}
	
	protected void sumar_sueño(int sueño_sumada) {
		if(this.sueño < 100) {
			this.sueño += sueño_sumada;
		}
	}
	
	protected void restar_sueño(int sueño_restada) {
		if(this.sueño > 0) {
			this.sueño -= sueño_restada;
		}
	}
	
	protected void estado_dormido() {
		if(dormido == false) {
			dormido = true;
		}else {
			if(this.tiempo_dormido <= 5) {
				dormido = false;
				this.tiempo_dormido = 0;
			}
		}
	}
	
	protected void sumar_salud(int salud_sumada) {
		if(this.salud + salud_sumada <= this.salud_max) {
			salud += salud_sumada;
		}
	}
	
	protected void restar_salud(int salud_restada) {
		if(this.salud > 0) {
			this.salud -= salud_restada;
		}
	}
	
	protected void jugar(int salud_sumada, int aburrimiento_restada, int sueño_sumada, int hambre_sumada, int sed_sumada, int peso_restada) {
		sumar_salud(salud_sumada);
		restar_aburrimiento(aburrimiento_restada);
		sumar_sueño(sueño_sumada);
		sumar_hambre(hambre_sumada);
		sumar_sed(sed_sumada);
		restar_peso(peso_restada);
		//agregar probabilidad de restar vida
	}
	
	protected void comer(int salud_sumada, int salud_restada,int sueño_sumada, int hambre_restada, int sed_sumada, int peso_sumada) {
		sumar_salud(salud_sumada);
		restar_salud(salud_restada);
		sumar_sueño(sueño_sumada);
		restar_hambre(hambre_restada);
		sumar_sed(sed_sumada);
		sumar_peso(peso_sumada);
	}
	
	protected void tomar(int salud_sumada, int hambre_restada, int sed_restada, int aburrimiento_sumada) {
		sumar_salud(salud_sumada);
		restar_hambre(hambre_restada);
		restar_sed(sed_restada);
		sumar_aburrimiento(aburrimiento_sumada);
	}
	
	protected void dormir(int salud_sumada, int sueño_restada, int hambre_sumada, int sed_sumada, int peso_sumada) {
		if(this.sueño > 60 && dormido == false) {
			sumar_salud(salud_sumada);
			restar_sueño(sueño_restada);
			sumar_hambre(hambre_sumada);
			sumar_sed(sed_sumada);
			sumar_peso(peso_sumada);
			estado_dormido();
		}
	}
	
	protected void despertar() {
		if(dormido == true) {
			estado_dormido();
		}
	}
	
}
