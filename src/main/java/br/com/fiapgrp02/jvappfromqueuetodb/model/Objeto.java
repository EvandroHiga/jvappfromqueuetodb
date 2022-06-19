package br.com.fiapgrp02.jvappfromqueuetodb.model;

import java.io.Serializable;

public class Objeto implements Serializable {

    private static final long serialVersionUID = 1L;

    String valor01;
    String valor02;
    String env;

    public String getValor01() {
        return valor01;
    }
    public void setValor01(String valor01) {
        this.valor01 = valor01;
    }
    public String getValor02() {
        return valor02;
    }
    public void setValor02(String valor02) {
        this.valor02 = valor02;
    }
    public String getEnv() { return env; }
    public void setEnv(String env) { this.env = env; }
}

