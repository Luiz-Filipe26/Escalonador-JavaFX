package com.mycompany.escalonador.core;

import java.util.Random;

import com.mycompany.escalonador.controle.EscalonadorController;

public class TarefaStringAleatoria extends TarefaAbstrata {
	private EscalonadorController escalonadorController;

    public TarefaStringAleatoria(int _idProcesso, String _nomeProcesso) {
        super(_idProcesso, _nomeProcesso);
        this.escalonadorController = EscalonadorController.getInstancia();
    }

    @Override
    public void run() {
        pronto = true;
        if (novo) {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        
        escalonadorController.adicionarTexto("Começo da execução da Thread " + idProcesso + "\n");
               

        Random random = new Random();

        while (!encerrado) {
            String randomString = gerarStringAleatoria(random, 4);
            escalonadorController.adicionarTexto(nomeProcesso + ": " + randomString + "\n");
            

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if (pronto) {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String gerarStringAleatoria(Random random, int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder stringAleatoria = new StringBuilder();

        for (int i = 0; i < length; i++) {
            char randomChar = chars.charAt(random.nextInt(chars.length()));
            stringAleatoria.append(randomChar);
            if (i < length - 1) {
                stringAleatoria.append(" ");
            }
        }

        return stringAleatoria.toString();
    }
}
