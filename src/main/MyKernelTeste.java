package main;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import operatingSystem.Kernel;

/**
 * Kernel desenvolvido pelo aluno. Outras classes criadas pelo aluno podem ser
 * utilizadas, como por exemplo: - Arvores; - Filas; - Pilhas; - etc...
 *
 * @author nome do aluno...
 */
public class MyKernelTeste implements Kernel {

    private Diretorio raiz;
    private Diretorio atualDiretorio;
    private Diretorio temporarioDiretorio;
    private String currentDir = "/";

    public MyKernelTeste() {

        this.raiz = new Diretorio();
        raiz.mkdir("/", null);
        raiz.setDiretorioPai(raiz);
        atualDiretorio = raiz;
    }

    public String ls(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: ls");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        // System.out.println("ola pessoal!");
        result = atualDiretorio.ls(parameters, raiz);
        // fim da implementacao do aluno
        return result;
    }

    public String mkdir(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: mkdir");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        String[] nomesDiretorio = new String[0];
        Diretorio temporarioAtualDiretorio;

        if ((parameters.startsWith("/"))) {
            temporarioAtualDiretorio = raiz;
            parameters = parameters.substring(1);
            nomesDiretorio = parameters.split("/");
        } else {
            temporarioAtualDiretorio = atualDiretorio;
            nomesDiretorio = parameters.split("/");
        }

        for (String parte : nomesDiretorio) {
            if (!parte.equals(".")) {
                if ((parte.contains(" ")) || (!parte.matches("^[A-Za-z0-9].*"))) {
                    // throw new IllegalArgumentException("O nome do diretório não está em um
                    // formato válido.");
                    result = "Nome de diretório pode começar apenas com letras e números e não pode conter espaços!";
                }
                if (parte == nomesDiretorio[nomesDiretorio.length - 1]) {
                    for (Diretorio filho : temporarioAtualDiretorio.getDiretorioFilhos()) {
                        if (filho.getDiretorioNome().equals(parte)) {
                            result = parte + " já existe!";
                        }
                    }
                }
            }
        }

        if (result.equals("")) {
            for (String parte : nomesDiretorio) {
                boolean encontrado = false;
                for (Diretorio filho : temporarioAtualDiretorio.getDiretorioFilhos()) {
                    if (filho.getDiretorioNome().equals("..")) {
                        temporarioAtualDiretorio = temporarioDiretorio.getDiretorioPai();
                        encontrado = true;
                        break;
                    }
                    if (filho.getDiretorioNome().equals(".")) {
                        encontrado = true;
                        break;
                    }
                    if (filho.getDiretorioNome().equals(parte)) {
                        temporarioAtualDiretorio = filho;
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    Diretorio novoDiretorio = new Diretorio();
                    temporarioAtualDiretorio = novoDiretorio.mkdir(parte, temporarioAtualDiretorio);
                }
            }
        }

        // fim da implementacao do aluno
        return result;
    }

    public String cd(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        // String currentDir = "";
        System.out.println("Chamada de Sistema: cd");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        String[] nomesDiretorio = new String[0];
        Diretorio temporarioAtualDiretorio;

        if(parameters.equals("/")){
            atualDiretorio = raiz;
            temporarioAtualDiretorio = raiz;
        }else if ((parameters.startsWith("/"))) {
            temporarioAtualDiretorio = raiz;
            parameters = parameters.substring(1);
            nomesDiretorio = parameters.split("/");
        } else {
            temporarioAtualDiretorio = atualDiretorio;
            nomesDiretorio = parameters.split("/");
        }

        for (String parte : nomesDiretorio) {
            boolean encontrado = false;
            if (parte.equals("..")) {
                temporarioAtualDiretorio = temporarioAtualDiretorio.getDiretorioPai();
                encontrado = true;
            } else if (parte.equals(".")) {
                encontrado = true;
            } else {
                for (Diretorio filho : temporarioAtualDiretorio.getDiretorioFilhos()) {
                    if (filho.getDiretorioNome().equals(parte)) {
                        temporarioAtualDiretorio = filho;
                        encontrado = true;
                        break;
                    }
                }
            }

            if (!encontrado) {
                result = "caminho incorreto";
                break;
            }
        }
        if (result.equals("")) {
            atualDiretorio = temporarioAtualDiretorio;
        }

        currentDir = "";
        while (!temporarioAtualDiretorio.equals(raiz)) {
            if(temporarioAtualDiretorio.equals(atualDiretorio)){
                currentDir = temporarioAtualDiretorio.getDiretorioNome() + currentDir;
            }
            else{
                currentDir = temporarioAtualDiretorio.getDiretorioNome() + "/" + currentDir;
            }
            temporarioAtualDiretorio = temporarioAtualDiretorio.getDiretorioPai();
        }
        currentDir = "/" + currentDir;

        // indique o diretório atual. Por exemplo... /
        // currentDir = "/";

        // setando parte gráfica do diretorio atual
        operatingSystem.fileSystem.FileSytemSimulator.currentDir = currentDir;

        // fim da implementacao do aluno
        return result;
    }

    public String rmdir(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: rmdir");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        result = atualDiretorio.rmdir(parameters, raiz);

        // fim da implementacao do aluno
        return result;
    }

    public String cp(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: cp");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        result = atualDiretorio.cp(parameters, raiz);
        // fim da implementacao do aluno
        return result;
    }

    public String mv(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: mv");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        result = atualDiretorio.mv(parameters, raiz);
        // fim da implementacao do aluno
        return result;
    }

    public String rm(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: rm");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        // fim da implementacao do aluno
        return result;
    }

    public String chmod(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: chmod");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        // fim da implementacao do aluno
        return result;
    }

    public String createfile(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: createfile");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        result = atualDiretorio.createfile(parameters, raiz);
        // fim da implementacao do aluno
        return result;
    }

    public String cat(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: cat");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        result = atualDiretorio.cat(parameters, raiz);
        // fim da implementacao do aluno
        return result;
    }

    public String batch(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: batch");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        // fim da implementacao do aluno
        return result;
    }

    public String dump(String parameters) {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: dump");
        System.out.println("\tParametros: " + parameters);

        // inicio da implementacao do aluno
        // fim da implementacao do aluno
        return result;
    }

    public String info() {
        // variavel result deverah conter o que vai ser impresso na tela apos comando do
        // usuário
        String result = "";
        System.out.println("Chamada de Sistema: info");
        System.out.println("\tParametros: sem parametros");

        // nome do aluno
        String name = "Davi Martins";
        // numero de matricula
        String registration = "2018.11.02.00.06";
        // versao do sistema de arquivos
        String version = "0.1";

        result += "Nome do Aluno:        " + name;
        result += "\nMatricula do Aluno:   " + registration;
        result += "\nVersao do Kernel:     " + version;

        return result;
    }

}
