package tamagotchi_proyect;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class Pet {
	//atributos
	private boolean vivo = true;
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
	private boolean puede_comer;
	private int hambre;
	private boolean puede_beber;
	private int sed;
	private int min_sed = 0;
	private int puede_jugar;
	private int aburrimiento;
	private boolean puede_dormir;
	private int sueño;
	private boolean dormido = false;
	private int tiempo_dormido = 0;
	private int edad;
	private int tiempo = 0;
	private Random probabilidad = new Random();
	private int suerte;
	
	
	protected Pet(boolean vivo, String especie, String nombre, int peso_min, int peso_max, int peso, int vida_max,
			int vida, int salud_max, int salud, boolean enfermo, boolean puede_comer, int hambre, boolean puede_beber,
			int sed, int min_sed, int puede_jugar, int aburrimiento, boolean puede_dormir, int sueño, boolean dormido,
			int tiempo_dormido, int edad, int tiempo, Random probabilidad, int suerte) {
		super();
		this.vivo = vivo;
		this.especie = especie;
		this.nombre = nombre;
		this.peso_min = peso_min;
		this.peso_max = peso_max;
		this.peso = peso;
		this.vida_max = vida_max;
		this.vida = vida;
		this.salud_max = salud_max;
		this.salud = salud;
		this.enfermo = enfermo;
		this.puede_comer = puede_comer;
		this.hambre = hambre;
		this.puede_beber = puede_beber;
		this.sed = sed;
		this.min_sed = min_sed;
		this.puede_jugar = puede_jugar;
		this.aburrimiento = aburrimiento;
		this.puede_dormir = puede_dormir;
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
	
	protected void restar_suerte(int suerte_restada) {
		this.suerte -= suerte_restada;
	} 

	protected void pasarTiempo() {
		Suerte();
		if(suerte < 20) {
			restar_salud(2);
			sumar_hambre(5);
			sumar_sed(5);
			sumar_sueño(5);
			sumar_aburrimiento(5);
		}else {
			restar_salud(1);
			sumar_hambre(3);
			sumar_sed(3);
			sumar_sueño(3);
			sumar_aburrimiento(3);
		}
		this.tiempo += 1;
		
		if (this.dormido == true) {
			this.tiempo_dormido += 1;
		}
		
		if(tiempo == 100) {
			cumpleaños();
			tiempo = 0;
		}
		
		//resta vida si algun atributo esta muy alto o muy bajo
		if(this.salud <= 45) {
			restar_vida(3);
		}
		if(this.hambre >= 50) {
			restar_vida(3);
		}
		if(this.sed >= 50) {
			restar_vida(3);
		}
		if(this.aburrimiento >= 60) {
			restar_vida(3);
		}
		if(this.sueño >= 30) {
			restar_vida(3);
		}
		if(this.peso >= peso_max-40) {
			restar_vida(3);
		}
		if(this.peso <= peso_min+40) {
			restar_vida(3);
		}
		if(this.vida <= 0) {
			muerto();
		}
	}
	
	public void CicloVida() {
		ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
		scheduler.scheduleAtFixedRate(() -> {
			this.pasarTiempo();
		},0 , 10, TimeUnit.SECONDS);
	}
	
	protected void cumpleaños() {
		if(vivo) {
			if(this.edad < 10) {
				this.edad += 1;
				cambiar_peso_max_min();
				cambiar_vida_max();
			}else {
				muerto();
			}
		}
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
		if(this.peso - peso_restada > this.peso_min) {
			this.peso -= peso_restada;
			}else {
				this.peso = this.peso_min;
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
		if(this.vivo && this.vida - vida_restada < 0) {
			this.vida = 0;
		}else {
			if(this.vivo && this.vida - vida_restada > 0) {
				this.vida -= vida_restada;
			}
		}
	}
	
	protected void sumar_vida(int vida_sumada) {
		if(vivo) {
			if(this.suerte >= 45) {
				if(this.vida + vida_sumada + 20 <= vida_max) {
					this.vida += vida_sumada + 20;
				}else {
					this.vida = vida_max;
				}
			}else {
				if(this.vida + vida_sumada <= vida_max) {
					this.vida += vida_sumada;
				}else {
					this.vida = vida_max;
				}
			}
		}
	}
	
	protected void sumar_hambre(int hambre_sumada) {
		if(this.suerte <= 20) {
			this.hambre += hambre_sumada + 20;
		}else {
			this.hambre += hambre_sumada;
		}
	}
	
	protected void restar_hambre(int hambre_restada) {
		Suerte();
		if(this.hambre >= 30) {
			this.puede_comer = true;
		}else {
			this.puede_comer = false;
		}
		if(vivo && this.puede_comer) {
			if (this.suerte >= 30) {
				if(this.hambre - hambre_restada - 10 < 0) {
					this.hambre = hambre_restada;
				}else {
					this.hambre -= hambre_restada - 10;
				}
			}else {
				if(this.hambre - hambre_restada < 0) {
					this.hambre = hambre_restada;
				}else {
					this.hambre -= hambre_restada;
				}
			}
		}
	}
	
	protected void sumar_sed(int sed_sumada) {
		if(this.suerte <= 35 ) {
			this.sed += sed_sumada + 25;
		}else {
			if(this.sed < 100) {
				this.sed += sed_sumada;
			}
		}
	}
	
	protected void restar_sed(int sed_restada) {
		Suerte();
		if(this.sed >= 30) {
			this.puede_beber = true;
		}else {
			this.puede_beber = false;
		}
		if(this.vivo && this.puede_beber) {
			if(this.suerte <= 5 && this.sed - sed_restada - 20 < 0) {
				this.sed = min_sed ;
			}else {
				if(this.suerte <= 5) {
					this.sed -= sed_restada - 20;
				}else {
					if(this.sed - sed_restada < 0) {
						this.sed = this.min_sed;
					}else {
						this.sed -= sed_restada;
					}
				}
			}
		}
	}
	
	protected void sumar_aburrimiento(int aburrimiento_sumada) {
		this.aburrimiento += aburrimiento_sumada;
	}
	
	protected void restar_aburrimiento(int aburrimiento_restada) {
		if(this.aburrimiento > 0 && this.vivo) {
			this.aburrimiento -= aburrimiento_restada;
		}
	}
	
	protected void sumar_sueño(int sueño_sumada) {
		this.sueño += sueño_sumada;
	}
	
	protected void restar_sueño(int sueño_restada) {
		Suerte();
		if(this.puede_dormir && this.vivo && this.suerte >= 35) {
			this.sueño -= sueño_restada - 10;
		}else {
			if(this.puede_dormir && this.vivo) {
				this.sueño -= sueño_restada;
			}
		}
	}
	
	protected void estado_dormido() {
		if(dormido == false) {
			dormido = true;
		}else {
			if(this.tiempo_dormido >= 5) {
				dormido = false;
				this.tiempo_dormido = 0;
			}
		}
	}
	
	protected void sumar_salud(int salud_sumada) {
		if(this.salud + salud_sumada <= this.salud_max) {
			salud += salud_sumada;
		}else {
			this.salud = this.salud_max;
		}
	}
	
	protected void restar_salud(int salud_restada) {
		this.salud -= salud_restada;
	}
	
	protected void jugar(int salud_sumada, int aburrimiento_restada, int sueño_sumada, int hambre_sumada, int sed_sumada, int peso_restada) {
		Suerte();
		sumar_salud(salud_sumada);
		restar_aburrimiento(aburrimiento_restada);
		sumar_sueño(sueño_sumada);
		sumar_hambre(hambre_sumada);
		sumar_sed(sed_sumada);
		restar_peso(peso_restada);
		
		if(this.suerte <= 25) {
			restar_salud(-5);
		}
	}
	
	protected void comer(int salud_sumada, int sueño_sumada, int hambre_restada, int sed_sumada, int peso_sumada) {
		Suerte();
		if(this.suerte <= 10) {
			sumar_hambre(5);
			sumar_sueño(5);
			sumar_sed(5);
			restar_salud(-5);
		}
		sumar_salud(salud_sumada);
		sumar_salud(salud_sumada);
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
	
	protected void muerto() {
		this.vivo = false;
	}
	
}
