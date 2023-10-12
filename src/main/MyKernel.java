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

    int raiz = 0;
    int dirAtual = raiz;
    HardDisk HD = new HardDisk(128);

    public MyKernel() {
        String dirRaiz = criaDiretorio("/", raiz);
        escreverStringNoHardDisk(HD, dirRaiz, raiz);
        //desmembrarString(raiz, HD);
        //String lerRaiz = lerStringDoHardDisk(HD, 0, 512);
        //002System.out.println(lerRaiz);
    }

    public String ls(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: ls");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        StringBuilder resultado = new StringBuilder();
        String caminho = "";
        String parametro = "";

        if (!parameters.equals("")) {
            if (parameters.equals("-l")) {
                parametro = parameters;
            } else if (parameters.startsWith("-l")) {
                int firstSpaceIndex = parameters.indexOf(" ");
                parametro = (firstSpaceIndex != -1) ? parameters.substring(0, firstSpaceIndex) : parameters;
                caminho = (firstSpaceIndex != -1) ? parameters.substring(firstSpaceIndex + 1) : "";
            } else {
                caminho = parameters;
            }
        }

        System.out.println("parametro: " + parametro);
        System.out.println("caminho: " + caminho);

        String[] filhosDir = new String[20];
        String dir;
        int dirNum = 0;

        if(!caminho.equals("")){
            dirNum = encontraDiretorio(caminho, dirAtual,HD);
        }

        if( dirNum == -1){
            result = "Parametros incorretos!";
        }else{

            if (caminho.equals("")) {
                dir = lerStringDoHardDisk(HD, dirAtual, 512);
            }else{
                dir = lerStringDoHardDisk(HD, dirNum, 512);
            }
            
            for (int i = 0; i < 20; i++) {
                filhosDir[i] = dir.substring(97 + i * 10, 107 + i * 10);
            }

            for (int i = 0; i < 20; i++) {
                String num = filhosDir[i].replaceAll("\\s+", "");
                if(!num.equals("")){
                    if (parametro.equals("")){
                        resultado.append(encontraNomeDiretorio(Integer.parseInt(num),HD)).append(" ");
                    }else{
                        resultado.append(encontraNomeDataPermisaoDiretorio(Integer.parseInt(num),HD)).append("\n");
                    }
                }
            }

            result = resultado.toString().trim();

            if(result.equals("")){
                result = "Diretório vazio!";
            }
        }

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

        int posicaoVazia = 0;
        boolean diretoriCheio = false;
        if(result.equals("")){
            for (String parte : dirNomes) {
                //System.out.println("parte "+parte);
                if(parte.equals(".")||posicaoVazia == -1||diretoriCheio == true){
                }else if(parte.equals("..")){
                    dirAtualTemporario = encontraDiretorioPai(dirAtualTemporario, HD);
                }else{
                    int dirAux = comparaNomesDiretorioFilhos(dirAtualTemporario,parte,HD);
                    if(dirAux != -1){
                        dirAtualTemporario = dirAux;
                        System.out.println("dir atual: "+dirAtualTemporario);
                    }else{
                        posicaoVazia = procuraPosicaoVaziaHD(HD);
                        System.out.println("posição: "+posicaoVazia);
                        if(posicaoVazia == -1){
                            result = "HD está cheio!";
                        }

                        boolean filhosPaiCoube = escreveDirFilhoNoPai(dirAtualTemporario, posicaoVazia, parte, HD);
                        if(filhosPaiCoube == true){
                            escreverStringNoHardDisk(HD, criaDiretorio(parte, dirAtualTemporario), posicaoVazia);
                            dirAtualTemporario = posicaoVazia;
                        }else{
                            result = "Diretorio: "+encontraNomeDiretorio(dirAtualTemporario,HD)+" está cheio!";
                            diretoriCheio = true;
                        }
                    }
                }
            }
        }

        //desmembrarString(0, HD);

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
        String name = "Davi Martins de SOusa";
        //numero de matricula
        String registration = "2018.11.02.00.06";
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

    public static int procuraPosicaoVaziaHD(HardDisk hd){
        for(int i = 0; i < 134217728; i++){
            String info = lerStringDoHardDisk(hd,i,1);
            if(!info.equals("d")&&!info.equals("a")){
                return i;
            }
        }
        return -1;
    }

    public static void escreverStringNoHardDisk(HardDisk hd, String texto, int posicao) {
        boolean[] bits = stringToBinaryArray(texto);
        
        for (int i = 0; i < bits.length; i++) {
            hd.setBitDaPosicao(bits[i], posicao*512*8 + i);
        }
    }

    public static String lerStringDoHardDisk(HardDisk hd, int posicao, int tamanho) {
        boolean[] bits = new boolean[tamanho * 8];
        
        for (int i = 0; i < bits.length; i++) {
            bits[i] = hd.getBitDaPosicao(posicao*512*8 + i);
        }
        
        return binaryArrayToString(bits);
    }
    
    public static String criaDiretorio(String nome, int dirPai) {
        String estado = "d";
        nome = String.format("%-" + 86 + "s", nome);
        String pai = String.format("%-" + 10 + "s", Integer.toString(dirPai));
        String filhosDir = String.format("%-" + 200 + "s", "");
        String filhosArg = String.format("%-" + 200 + "s", "");
        String data = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String permissao = "777";

        return estado + nome + pai + filhosDir + filhosArg + data + permissao;
    }

    public static boolean escreveDirFilhoNoPai(int dir,int filho, String dirNome, HardDisk hd) {
        String resultado = lerStringDoHardDisk(hd, dir, 512);
        
        String estado = resultado.substring(0, 1);
        String nome = resultado.substring(1, 87);
        String pai = resultado.substring(87, 97);

        String[] filhosDir = new String[20];
        for (int i = 0; i < 20; i++) {
            filhosDir[i] = resultado.substring(97 + i * 10, 107 + i * 10);
        }

        String filhosArg = resultado.substring(297, 497);

        String data = resultado.substring(497, 509);
        String permissao = resultado.substring(509);

        boolean encontrado = false;
        for (int i = 0; i < 20; i++) {
            if(filhosDir[i].replaceAll("\\s+", "").equals("") && encontrado == false){
                filhosDir[i] = String.format("%-" + 10 + "s", Integer.toString(filho));
                encontrado = true;
            } 
        }
        
        if(encontrado == true){
            String dirNovo = estado + nome + pai + filhosDir[0] + filhosDir[1] + filhosDir[2] + filhosDir[3] + filhosDir[4] + filhosDir[5] + filhosDir[6] + filhosDir[7] + filhosDir[8] + filhosDir[9] + filhosDir[10] + filhosDir[11] + filhosDir[12] + filhosDir[13] + filhosDir[14] +  filhosDir[15] + filhosDir[16] + filhosDir[17] + filhosDir[18] + filhosDir[19] + filhosArg + data + permissao;
            escreverStringNoHardDisk(hd, dirNovo , dir);
            return true;
        }else {
            return false;
        }
    }

    public static void desmembrarString(int dirNum, HardDisk hd) {
        String resultado = lerStringDoHardDisk(hd, dirNum, 512);
        String estado = resultado.substring(0, 1).replaceAll("\\s+", "");
        String nome = resultado.substring(1, 87).replaceAll("\\s+", "");
        String pai = resultado.substring(87, 97).replaceAll("\\s+", "");
        
        String[] filhosDir = new String[20];
        for (int i = 0; i < 20; i++) {
            filhosDir[i] = resultado.substring(97 + i * 10, 107 + i * 10).replaceAll("\\s+", "");
        }
    
        String[] filhosArg = new String[20];
        for (int i = 0; i < 20; i++) {
            filhosArg[i] = resultado.substring(297 + i * 10, 307 + i * 10).replaceAll("\\s+", "");
        }
    
        String data = resultado.substring(497, 509).replaceAll("\\s+", "");
        String permissao = resultado.substring(509).replaceAll("\\s+", "");
    
        // Agora você tem as variáveis individuais desmembradas sem espaços em branco
        System.out.println("Estado: " + estado);
        System.out.println("Nome: " + nome);
        System.out.println("Pai: " + pai);
        for (int i = 0; i < 20; i++) {
            System.out.println("FilhoDir" + (i + 1) + ": " + filhosDir[i]);
        }
        for (int i = 0; i < 20; i++) {
            System.out.println("FilhoArg" + (i + 1) + ": " + filhosArg[i]);
        }
        System.out.println("Data: " + data);
        System.out.println("Permissao: " + permissao);
    }

    public static int encontraDiretorioPai(int diratual, HardDisk hd) {
        String resultado = lerStringDoHardDisk(hd, diratual, 512);
        return Integer.parseInt(resultado.substring(87, 97).replaceAll("\\s+", ""));
    }
        
    public static int comparaNomesDiretorioFilhos(int dirNum, String nome, HardDisk hd) {
        String dir = lerStringDoHardDisk(hd, dirNum, 512);
        String[] filhosDir = new String[20];

        for (int i = 0; i < 20; i++) {
            filhosDir[i] = dir.substring(97 + i * 10, 107 + i * 10);
        }

        for (int i = 0; i < 20; i++) {
            String num = filhosDir[i].replaceAll("\\s+", "");
            if(!num.equals("")){
                if(encontraNomeDiretorio(Integer.parseInt(num), hd).equals(nome)){
                    return Integer.parseInt(num);
                } 
            }
        }

        return -1;
    }

    public static String encontraNomeDiretorio(int dirNum, HardDisk hd){
        String dir = lerStringDoHardDisk(hd, dirNum, 512);
        return dir.substring(1, 87).replaceAll("\\s+", "");
    }

    public static String encontraNomeDataPermisaoDiretorio(int dirNum, HardDisk hd){
        String dir = lerStringDoHardDisk(hd, dirNum, 512);
        String permissao = dir.substring(509).replaceAll("\\s+", "");
        permissao = convertePermissao(permissao, dir.substring(0, 1).replaceAll("\\s+", ""));
        String data = dir.substring(497, 509).replaceAll("\\s+", "");
        data = data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4) + " " + data.substring(8, 10) + ":" + data.substring(10, 12);
        String nome = dir.substring(1, 87).replaceAll("\\s+", "");
        return permissao + "\t" + data + "\t" + nome;
    }

    public static String convertePermissao(String numericPermissions, String estado) {
        if (numericPermissions.length() != 3) {
            return "Permissão numérica inválida";
        }

        for (int i = 0; i < 3; i++) {
            char numericChar = numericPermissions.charAt(i);
            estado += (numericChar >= '4' ? 'r' : '-');
            estado += (numericChar % 2 == 1 ? 'w' : '-');
            estado += (numericChar % 2 == 1 ? 'x' : '-');
        }

        return estado;
    }

    public static int encontraDiretorio(String caminho, int dirAtual, HardDisk hd){
        String[] caminhos = new String[0];

        if(caminho.equals("/")){
            System.out.println("parte do caminho: raiz");
            System.out.println("diretorio encontrado: raiz");
            return 0;
        }if (caminho.startsWith("/")) {
            caminho = caminho.replaceFirst("/", ""); // Remove o "/" do início da string caminho
            dirAtual = 0;
        }

        caminhos= caminho.split("/");

        for (String parte : caminhos) {
            System.out.println("parte do caminho: " + parte);
        }

        for (String parte : caminhos) {
            if (!parte.equals(".")) {
                if(parte.equals("..")){
                    dirAtual = encontraDiretorioPai(dirAtual, hd);
                }else{
                    dirAtual = comparaNomesDiretorioFilhos(dirAtual, parte, hd);
                    if(dirAtual == -1){
                        System.out.println("Caminho incorreto!");
                        return -1;
                    }
                }
            }
        }

        System.out.println("Diretorio encontrado: "+dirAtual+"");
        return dirAtual;
    }
}
