package main;

import hardware.HardDisk;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import operatingSystem.Kernel;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Kernel desenvolvido pelo aluno. Outras classes criadas pelo aluno podem ser
 * utilizadas, como por exemplo: - Arvores; - Filas; - Pilhas; - etc...
 *
 * @author Davi Martins de Sousa
 */
public class MyKernel implements Kernel {

    int dirAtual = 0;
    int raiz = 0;

    public MyKernel() {
        HardDisk HD = new HardDisk(128);
        String Raiz = criaDiretorio("/", Integer.toString(raiz));
        escreverStringNoHardDisk(HD, Raiz, raiz);

        //String lerRaiz = lerStringDoHardDisk(HD, 0, 512);
        //System.out.println(lerRaiz);
    }

    public String ls(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: ls");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        System.out.println("ola pessoal!");

        //fim da implementacao do aluno
        return result;
    }

    public String mkdir(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: mkdir");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        String[] dirNomes = new String[0];
        int dirAtualTemporario;

        if ((parameters.startsWith("/"))) {
            dirAtualTemporario = raiz;
            parameters = parameters.substring(1);
            dirNomes = parameters.split("/");
        } else {
            dirAtualTemporario = dirAtual;
            dirNomes = parameters.split("/");
        }

        for (String parte : dirNomes) {
            if (!parte.equals(".") && !parte.equals("..")) {
                if ((parte.contains(" ")) || (!parte.matches("^[A-Za-z0-9].*"))) {
                    result = "Nome de diretório pode começar apenas com letras e números e não pode conter espaços!";
                }
            }
        }

        if(result.equals("")){
            for (String parte : dirNomes) {

            }
        }


        for (String nome : dirNomes) {
            System.out.println(nome);
        }

        //fim da implementacao do aluno
        return result;
    }

    public String cd(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        String currentDir = "";
        System.out.println("Chamada de Sistema: cd");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //indique o diretório atual. Por exemplo... /
        currentDir = "/";

        //setando parte gráfica do diretorio atual
        operatingSystem.fileSystem.FileSytemSimulator.currentDir = currentDir;

        //fim da implementacao do aluno
        return result;
    }

    public String rmdir(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: rmdir");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //fim da implementacao do aluno
        return result;
    }

    public String cp(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: cp");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //fim da implementacao do aluno
        return result;
    }

    public String mv(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: mv");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //fim da implementacao do aluno
        return result;
    }

    public String rm(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: rm");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //fim da implementacao do aluno
        return result;
    }

    public String chmod(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: chmod");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //fim da implementacao do aluno
        return result;
    }

    public String createfile(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: createfile");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //fim da implementacao do aluno
        return result;
    }

    public String cat(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: cat");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //fim da implementacao do aluno
        return result;
    }

    public String batch(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: batch");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //fim da implementacao do aluno
        return result;
    }

    public String dump(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: dump");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        //fim da implementacao do aluno
        return result;
    }

    public String info() {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: info");
        System.out.println("\tParametros: sem parametros");

        //nome do aluno
        String name = "Fulano da Silva";
        //numero de matricula
        String registration = "2001.xx.yy.00.11";
        //versao do sistema de arquivos
        String version = "0.1";

        result += "Nome do Aluno:        " + name;
        result += "\nMatricula do Aluno:   " + registration;
        result += "\nVersao do Kernel:     " + version;

        return result;
    }









    //Funções auxiliares

    public static boolean[] stringToBinaryArray(String input) {
        boolean[] binaryArray = new boolean[input.length() * 8]; // Cada caractere é convertido em 8 bits
    
        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
    
            // Convertendo cada caractere em binário
            String binaryString = Integer.toBinaryString(character);
    
            // Preenchendo com zeros à esquerda, se necessário
            while (binaryString.length() < 8) {
                binaryString = "0" + binaryString;
            }
    
            // Armazenando os bits no array de booleanos
            for (int j = 0; j < 8; j++) {
                binaryArray[i * 8 + j] = (binaryString.charAt(j) == '1');
            }
        }
    
        return binaryArray;
    }

    public static String binaryArrayToString(boolean[] binaryArray) {
        if (binaryArray.length % 8 != 0) {
            throw new IllegalArgumentException("O tamanho do vetor deve ser múltiplo de 8 para representação binária correta.");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < binaryArray.length; i += 8) {
            int charValue = 0;
            for (int j = 0; j < 8; j++) {
                charValue = (charValue << 1) | (binaryArray[i + j] ? 1 : 0);
            }
            sb.append((char) charValue);
        }

        return sb.toString();
    }

    public void escreverStringNoHardDisk(HardDisk hd, String texto, int posicao) {
        boolean[] bits = stringToBinaryArray(texto);
        
        for (int i = 0; i < bits.length; i++) {
            hd.setBitDaPosicao(bits[i], posicao + i);
        }
    }

    public String lerStringDoHardDisk(HardDisk hd, int posicao, int tamanho) {
        boolean[] bits = new boolean[tamanho * 8];
        
        for (int i = 0; i < bits.length; i++) {
            bits[i] = hd.getBitDaPosicao(posicao + i);
        }
        
        return binaryArrayToString(bits);
    }

    public static int procuraPosicaoVaziaHD(){

    }
    
    public static String criaDiretorio(String nome, String pai) {
        String estado = "d";
        nome = String.format("%-" + 86 + "s", nome);
        pai = String.format("%-" + 10 + "s", pai);
        String filhoDir1 = String.format("%-" + 10 + "s", "");
        String filhoDir2 = String.format("%-" + 10 + "s", "");
        String filhoDir3 = String.format("%-" + 10 + "s", "");
        String filhoDir4 = String.format("%-" + 10 + "s", "");
        String filhoDir5 = String.format("%-" + 10 + "s", "");
        String filhoDir6 = String.format("%-" + 10 + "s", "");
        String filhoDir7 = String.format("%-" + 10 + "s", "");
        String filhoDir8 = String.format("%-" + 10 + "s", "");
        String filhoDir9 = String.format("%-" + 10 + "s", "");
        String filhoDir10 = String.format("%-" + 10 + "s", "");
        String filhoDir11 = String.format("%-" + 10 + "s", "");
        String filhoDir12 = String.format("%-" + 10 + "s", "");
        String filhoDir13 = String.format("%-" + 10 + "s", "");
        String filhoDir14 = String.format("%-" + 10 + "s", "");
        String filhoDir15 = String.format("%-" + 10 + "s", "");
        String filhoDir16 = String.format("%-" + 10 + "s", "");
        String filhoDir17 = String.format("%-" + 10 + "s", "");
        String filhoDir18 = String.format("%-" + 10 + "s", "");
        String filhoDir19 = String.format("%-" + 10 + "s", "");
        String filhoDir20 = String.format("%-" + 10 + "s", "");
        String filhoArg1 = String.format("%-" + 10 + "s", "");
        String filhoArg2 = String.format("%-" + 10 + "s", "");
        String filhoArg3 = String.format("%-" + 10 + "s", "");
        String filhoArg4 = String.format("%-" + 10 + "s", "");
        String filhoArg5 = String.format("%-" + 10 + "s", "");
        String filhoArg6 = String.format("%-" + 10 + "s", "");
        String filhoArg7 = String.format("%-" + 10 + "s", "");
        String filhoArg8 = String.format("%-" + 10 + "s", "");
        String filhoArg9 = String.format("%-" + 10 + "s", "");
        String filhoArg10 = String.format("%-" + 10 + "s", "");
        String filhoArg11 = String.format("%-" + 10 + "s", "");
        String filhoArg12 = String.format("%-" + 10 + "s", "");
        String filhoArg13 = String.format("%-" + 10 + "s", "");
        String filhoArg14 = String.format("%-" + 10 + "s", "");
        String filhoArg15 = String.format("%-" + 10 + "s", "");
        String filhoArg16 = String.format("%-" + 10 + "s", "");
        String filhoArg17 = String.format("%-" + 10 + "s", "");
        String filhoArg18 = String.format("%-" + 10 + "s", "");
        String filhoArg19 = String.format("%-" + 10 + "s", "");
        String filhoArg20 = String.format("%-" + 10 + "s", "");
        String data = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String permissao = "777";

        String resultado = estado + nome + pai + 
                  filhoDir1 + filhoDir2 + filhoDir3 + filhoDir4 + filhoDir5 + 
                  filhoDir6 + filhoDir7 + filhoDir8 + filhoDir9 + filhoDir10 + 
                  filhoDir11 + filhoDir12 + filhoDir13 + filhoDir14 + filhoDir15 +
                  filhoDir16 + filhoDir17 + filhoDir18 + filhoDir19 + filhoDir20 +
                  filhoArg1 + filhoArg2 + filhoArg3 + filhoArg4 + filhoArg5 + 
                  filhoArg6 + filhoArg7 + filhoArg8 + filhoArg9 + filhoArg10 + 
                  filhoArg11 + filhoArg12 + filhoArg13 + filhoArg14 + filhoArg15 +
                  filhoArg16 + filhoArg17 + filhoArg18 + filhoArg19 + filhoArg20 +
                  data + permissao;

        
        return resultado;
    }

}
