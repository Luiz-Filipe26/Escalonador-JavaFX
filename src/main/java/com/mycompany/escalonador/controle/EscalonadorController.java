package com.mycompany.escalonador.controle;

import java.util.List;
import java.util.Map;

import com.mycompany.escalonador.core.DesenhoGrafico;
import com.mycompany.escalonador.core.Processo;
import com.mycompany.escalonador.fxmlController.View;

public class EscalonadorController {
	private static EscalonadorController escalonadorController;
	private View view;
	private DesenhoGrafico desenhoGrafico;
	
	public synchronized static EscalonadorController getInstancia() {
		
		if(escalonadorController == null) {
			escalonadorController = new EscalonadorController();
		}
		
		return escalonadorController;
	}
	
	private EscalonadorController() {
		
	}

	public void setFXMLController(View view) {
		this.view = view;
	}
	
	public void setDesenhoGrafico(DesenhoGrafico desenhoGrafico) {
		this.desenhoGrafico = desenhoGrafico;
	}
	
	public void adicionarOffset(int xOffset, int yOffset) {
		desenhoGrafico.adicionarOffset(xOffset, yOffset);
	}
	
	public void darZoom(double fatorZoom) {
		desenhoGrafico.darZoom(fatorZoom);
	}
	
	public void resetarGrafico() {
		desenhoGrafico.resetarGrafico();
	}
	
	public void adicionarTexto(String texto_add) {
		view.adicionarTexto(texto_add);
	}
	
	public void mostrarResultadoEscalonador(int numTrocasContexto, float tempoMedioExecucao, float tempoMedioEspera) {
        view.mostrarResultadoEscalonador(numTrocasContexto, tempoMedioExecucao, tempoMedioEspera);
    }
	
	public void repintarGrafico(Map<Processo, List<Integer>> processosExecutando, int instanteAtual) {
		desenhoGrafico.repintarGrafico(processosExecutando, instanteAtual);
	}

	public void atualizarGrafico(Map<Processo, List<Integer>> mapaDeInstantes, int instanteAtual) {
		desenhoGrafico.atualizarGrafico(mapaDeInstantes, instanteAtual);
	}
}
