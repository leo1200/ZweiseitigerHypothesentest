package com.example.leona.zweiseitigerhypothesentest;

import java.math.BigInteger;
import java.util.Scanner;

import static java.math.BigInteger.ONE;

/**
 * Created by leona on 04.02.2018.
 */

public class Calculate {
    public void main() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("n=");
        int n = scanner.nextInt();

        System.out.println("p=");
        Double p = scanner.nextDouble();

        System.out.println("Sigma-Intervall (z.B. 1,96): ");
        Double sigmanummer = scanner.nextDouble();

        System.out.println("Verwerfungsbereich links: ");
        Double vl = scanner.nextDouble();

        System.out.println("Verwerfungsbereich rechts: ");
        Double vr = scanner.nextDouble();

        Double erwartungswert = getErwartungswert(p, n);
        Double sigma = getSigma(n, p);

        System.out.println("Der Erwartungswert beträgt: " + erwartungswert);
        System.out.println("Sigma beträgt: " + getSigma(n, p));

        int[] sigmabereich = getSigmaBereich(sigma, erwartungswert, sigmanummer);

        System.out.println("Nach der Sigma-Regel ergibt sich ein Sigma-Bereich von: [" + sigmabereich[0] + ";" + sigmabereich[1] + "]");

        int a = findA(vl, p, n, sigmabereich);
        int b = findB(vr, p, n, sigmabereich);

        System.out.println("Für a ergibt sich: " + a);
        System.out.println("Für b ergibt sich: " + b);

        Double irrtumswahrscheinlichkeit = getIrrtum(a, b, n, p);

        System.out.println("Die Irrtumswahrscheinlichkeit beträgt: " + irrtumswahrscheinlichkeit + "%");


    }

    public Double getSigma(int n, Double p){
        return Math.sqrt(n*p*(1-p));
    }

    public Double getErwartungswert(Double wahrscheinlichkeit, int n){
        return n*wahrscheinlichkeit;
    }

    public int[] getSigmaBereich(Double sigma, Double erwartungswert, Double sigmanummer){
        int[] intervall = {(int)Math.ceil(erwartungswert-(sigmanummer*sigma)), (int)(erwartungswert+(sigmanummer*sigma))};
        return intervall;
    }

    public int findA(Double ablehnungsbereich, Double p, int n, int[] sigmabereich){
        int ende = sigmabereich[0];
        Double wahr = bereichswahrscheinlichkeit(0, ende, n, p);

        while(wahr>(ablehnungsbereich)){
            ende-=1;
            wahr=bereichswahrscheinlichkeit(0, ende, n, p);
        }

        while(wahr<(ablehnungsbereich)){
            ende+=1;
            wahr=bereichswahrscheinlichkeit(0, ende, n, p);
        }

        return ende;
    }

    public int findB(Double ablehnungsbereich, Double p, int n, int[] sigmabereich){
        int ende = sigmabereich[1];
        Double wahr = bereichswahrscheinlichkeit(0, ende, n, p);
        while(wahr<(1-ablehnungsbereich)){
            ende+=1;
            wahr=bereichswahrscheinlichkeit(0, ende, n, p);
        }

        while(wahr>(1-ablehnungsbereich)){
            ende-=1;
            wahr=bereichswahrscheinlichkeit(0, ende, n, p);
        }

        if(wahr<(1-ablehnungsbereich)){
            ende+=1;
        }

        return ende;
    }

    public Double bereichswahrscheinlichkeit(int anfang, int ende, int n, Double p){
        Double wahrscheinlichkeit = 0.0D;
        for(int i=anfang; i<=ende; i++){
            BigInteger b = nCr(BigInteger.valueOf(n), BigInteger.valueOf(i));
            Double d = Math.pow(p,i)*Math.pow((1-p), (n-i));
            wahrscheinlichkeit+=b.doubleValue() * d;
        }
        return wahrscheinlichkeit;
    }

    public BigInteger nCr(BigInteger m,BigInteger k) {
        BigInteger res = ONE;
        for(BigInteger i = ONE; i.compareTo(k) <= 0; i = i.add(ONE)) {
            res = res.multiply(m).divide(i);
            m = m.subtract(ONE);
        }
        return res;
    }

    public Double getIrrtum(int a, int b, int n, Double p){
        return (1-bereichswahrscheinlichkeit(a, b, n, p))*100;
    }
}


