package com.mycompany.escalonador.core;

import java.util.List;
import java.util.Map;

import javax.swing.JScrollBar;

import com.mycompany.escalonador.controle.EscalonadorController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DesenhoGrafico {
	private int xInicial;
    private int yInicial;
    private int xOffset;
    private int yOffset;
    private int unidadeLargura;
    private int alturaLinha;
    private int margemDasLinhas;
    private int instanteInicio;
    private int tamanhoFonte;
    private GraphicsContext gc;
    private Color[] cores;
    private double zoom;
    
    public DesenhoGrafico(GraphicsContext gc) {
    	this.gc = gc;
    	
		xInicial = 20;
        yInicial = 20;
        xOffset = 0;
        yOffset = 0;
        unidadeLargura = 30;
        alturaLinha = 30;
        margemDasLinhas = 10;
        tamanhoFonte = 16;
        instanteInicio = 0;
        zoom = 1.0;
        cores = new Color[]
		{Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK, Color.CYAN, 
        Color.MAGENTA, Color.LIGHTGRAY, Color.DARKGRAY, Color.GRAY, Color.BLACK, Color.WHITE,
        Color.LIGHTGRAY, Color.web("#FF5733"), Color.web("#4B0082")};

    }
    
    public void adicionarOffset(int xOffset, int yOffset) {
    	this.xOffset += xOffset;
    	this.yOffset += yOffset;
    	repintarGrafico(Escalonador.getInstancia().getMapaInstantes(), Escalonador.getInstancia().getInstanteAtual());
    }
    
    public void darZoom(double fatorZoom) {
    	this.zoom *= fatorZoom;
        repintarGrafico(Escalonador.getInstancia().getMapaInstantes(), Escalonador.getInstancia().getInstanteAtual());
    }

	public void repintarGrafico(Map<Processo, List<Integer>> processosExecutando, int instanteAtual) {
		gc.setFont(new Font("Serif", 14));
        
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

        for (int i = Processo.listaDeProcessos.size() - 1, numProcesso = 0; i >= 0; i--, numProcesso++) {
            Processo processo = Processo.listaDeProcessos.get(i);
            List<Integer> instantesDeExecucao = processosExecutando.get(processo);

            if (instantesDeExecucao == null) {
            	continue;
            }
            for (int instante = 0; instante <= instanteAtual; instante++) {
                if (!instantesDeExecucao.contains(instante)) {
                	continue;
                }
                desenharUnidadeDeBarra(cores[i % cores.length], numProcesso, instante-instanteInicio);
                if (instantesDeExecucao.get(0) == instante) {
                    desenharTexto(Color.BLACK, processo.getNome(), numProcesso);
                }
            }
        }
	}

    public void atualizarGrafico(Map<Processo, List<Integer>> processosExecutando, int instanteAtual) {
        int xTam = xInicial + unidadeLargura*(instanteAtual-instanteInicio);
        int yTam = yInicial +processosExecutando.size()*(margemDasLinhas+alturaLinha);
        
        if(xTam>gc.getCanvas().getWidth() || yTam>gc.getCanvas().getHeight()) {
            unidadeLargura = (int) unidadeLargura * 2/3;
            alturaLinha = (int) alturaLinha * 2/3;
            margemDasLinhas = (int) margemDasLinhas * 2/3;
            tamanhoFonte = (int) tamanhoFonte * 2/3;
            repintarGrafico(processosExecutando, instanteAtual);
        }
        
        for (int i = Processo.listaDeProcessos.size() - 1, numProcesso = 0; i >= 0; i--, numProcesso++) {
            Processo processo = Processo.listaDeProcessos.get(i);
            List<Integer> instantesDeExecucao = processosExecutando.get(processo);
            
            if (instantesDeExecucao != null && instantesDeExecucao.contains(instanteAtual)) {
                desenharUnidadeDeBarra(cores[i%cores.length], numProcesso, instanteAtual-instanteInicio);
                if (instantesDeExecucao.get(0) == instanteAtual) {
                	desenharTexto(Color.BLACK, processo.getNome(), numProcesso);
                }
            }
        }
    }
    
    private void desenharUnidadeDeBarra(Color barColor, int processo, int instante) {
        gc.setFill(barColor);
        gc.fillRect((xInicial + zoom * (xOffset + unidadeLargura * instante)),
                    (yInicial + zoom * (yOffset + processo * (margemDasLinhas + alturaLinha))),
                    unidadeLargura * zoom,
                    alturaLinha * zoom);
    }
    
    private void desenharTexto(Color barColor, String nomeProcesso, int processo) {
    	gc.setFill(barColor);
        gc.fillText(nomeProcesso, xInicial - 15, yInicial + zoom * (yOffset + (processo * (margemDasLinhas + alturaLinha) + alturaLinha / 2)));
    }
    
    public void resetarGrafico() {
        zoom = 1;
        instanteInicio = Escalonador.getInstancia().getInstanteAtual();
        repintarGrafico(Escalonador.getInstancia().getMapaInstantesLimpo(), instanteInicio);
    }
}
