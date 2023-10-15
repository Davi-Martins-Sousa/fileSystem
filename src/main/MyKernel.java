package main;

import hardware.HardDisk;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import operatingSystem.Kernel;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
        //System.out.println(lerRaiz);
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

        String[] filhosDir = new String[40];
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
            
            for (int i = 0; i < 40; i++) {
                filhosDir[i] = dir.substring(97 + i * 10, 107 + i * 10);
            }

            for (int i = 0; i < 40; i++) {
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
                if ((parte.contains(" ")) || (!parte.matches("^[A-Za-z].*"))) {
                    result = "Nome de diretório inválido!";
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

                        boolean filhosPaiCoube = escreveDirFilhoNoPai(dirAtualTemporario, posicaoVazia, HD);
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
        int dirNum = encontraDiretorio(parameters, dirAtual,HD);

        if( dirNum == -1){
            result = "Caminho incorreto!";
        }else{
            dirAtual = dirNum;
            while(dirNum!=0){
                currentDir = encontraNomeDiretorio(dirNum,HD) + "/" + currentDir;
                dirNum = encontraDiretorioPai(dirNum, HD);
            }
            currentDir = "/" + currentDir;   
        }

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
        int dirNum = encontraDiretorio(parameters, dirAtual,HD);
        String dir;
        String[] filhosDir = new String[40];
        boolean vazio = true;

        if( dirNum == -1){
            result = "Caminho incorreto!";
        }else{
            dir = lerStringDoHardDisk(HD, dirNum, 512);

            for (int i = 0; i < 40; i++) {
                filhosDir[i] = dir.substring(97 + i * 10, 107 + i * 10).replaceAll("\\s+", "");
                if (!filhosDir[i].equals("") && vazio == true){
                    vazio = false;
                }
            }

            if (vazio == false){
                result = "Diretório ainda possui filhos e não pode ser apagado pelo rmdir!";
            }else{
                apagaDiretorio(dirNum, HD);
                result = "Diretório apagado!";
            }

        }

        //fim da implementacao do aluno
        return result;
    }

    public String cp(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: cp");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        String[] parte = parameters.split(" ");
        String origemCaminho = "";
        String destinoCaminho = "";
        String parametro = "";
        int dirOrigem = -1;
        int dirDestino = -1;

        if(parte.length == 2){
            origemCaminho = parte[0];
            destinoCaminho = parte[1];
        }else if( parte.length == 3){
            parametro = parte[0];
            origemCaminho = parte[1];
            destinoCaminho = parte[2];
        }else{
            result = "Parametros inválidos!";
        }

        dirOrigem = encontraDiretorio(origemCaminho, dirAtual, HD);
        dirDestino = encontraDiretorio(destinoCaminho, dirAtual, HD);

        if (result.equals("")){
            if(!parametro.equals("") && !parametro.equals("-r")){
                result = "Parametro inválido!";
            }if(dirOrigem == -1 || dirDestino == -1){
                result = "Caminho inválido!";
            }
        }

        if (result.equals("")){
            String resultado = lerStringDoHardDisk(HD, dirOrigem, 512);
            String estadoOrigem = resultado.substring(0, 1);
            String nomeOrigem = resultado.substring(1, 87).replaceAll("\\s+", "");
            resultado = lerStringDoHardDisk(HD, dirDestino, 512);
            String estadoDestino = resultado.substring(0, 1);
            String nomeDestino = resultado.substring(1, 87).replaceAll("\\s+", "");

            if(estadoDestino.equals("a")){
                result = "Caminho de destino deve ser um diretório!";
            }else if( (parametro.equals("")) || (parametro.equals("-r") && estadoOrigem.equals("a")) ){
                result = copiaDiretorio(dirOrigem, dirDestino, HD);
            }else{
                result = copiaDiretorioRecursivamente(dirOrigem, dirDestino, HD);
            }
        }



        //fim da implementacao do aluno
        return result;
    }

    public String mv(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: mv");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        String[] partes = parameters.split(" ", 2);
        String origem = "";
        String destino = "";
        String origemPai;
        String destinoPai;
        String origemNome;
        String destinoNome;

        if (partes.length == 2) {
            origem = partes[0];
            destino = partes[1];
        }else{
            result = "Parametros incorretos!";
        }


        if(result.equals("")){
            int lastIndex = origem.lastIndexOf('/');
            if (lastIndex >= 0 && lastIndex < origem.length() - 1) {
                origemPai = origem.substring(0, lastIndex);
                if(origemPai.equals("")){
                    origemPai = "/";
                }
                origemNome = origem.substring(lastIndex + 1);
            } else {
                origemNome = origem;
                origemPai = ".";
            }

            lastIndex = destino.lastIndexOf('/');
            if (lastIndex >= 0 && lastIndex < destino.length() - 1) {
                destinoPai = destino.substring(0, lastIndex);
                if(destinoPai.equals("")){
                    destinoPai = "/";
                }
                destinoNome = destino.substring(lastIndex + 1);
            } else {
                destinoNome = destino;
                destinoPai = ".";
            }

            System.out.println("origem nome "+origemNome);
            System.out.println("origem pai "+origemPai);
            System.out.println("destino nome "+destinoNome);
            System.out.println("destino pai "+destinoPai);

            int origemNum = encontraDiretorio(origem, dirAtual, HD);
            int destinoNum = encontraDiretorio(destino, dirAtual, HD);
            int origemPaiNum = encontraDiretorio(origemPai, dirAtual, HD);
            int destinoPaiNum = encontraDiretorio(destinoPai, dirAtual, HD);

            int arqNum = -1;
            if(origemPaiNum != -1){
                arqNum = comparaNomesArquivosFilhos(origemPaiNum,origemNome,HD);
            }

            System.out.println("origemNum: " + origemNum);
            System.out.println("destinoNum: " + destinoNum);
            System.out.println("origemPaiNum: " + origemPaiNum);
            System.out.println("destinoPaiNum: " + destinoPaiNum);
            System.out.println("arqNum: " + arqNum);
            

            if(origemNum != -1 && destinoNum != -1){
                boolean filhosPaiCoube = escreveDirFilhoNoPai(destinoNum, origemNum, HD);
                if(filhosPaiCoube == true){
                    removeFilhoDoPai(origemPaiNum,origemNum, HD);
                    trocaPai(origemNum, destinoNum, HD);
                }else{
                    result = "Diretorio: "+encontraNomeDiretorio(destinoNum,HD)+" está cheio!";
                }
            }else if(origemNum != -1 && destinoNum == -1 &&  origemPaiNum == destinoPaiNum && destinoPaiNum!= -1){
                trocaNome(origemNum, destinoNome, HD);
            }else if(arqNum != -1  && destinoNum != -1){
                boolean filhosPaiCoube = escreveArgFilhoNoPai(destinoNum, arqNum, HD);
                if(filhosPaiCoube == true){
                    removeFilhoDoPai(origemPaiNum,arqNum, HD);
                    trocaPai(arqNum, destinoNum, HD);
                }else{
                    result = "Diretorio: "+encontraNomeDiretorio(destinoNum,HD)+" está cheio!";
                }
            }else if(arqNum != -1  && destinoNum == -1 &&  origemPaiNum == destinoPaiNum && destinoPaiNum!= -1){
                trocaNome(arqNum, destinoNome+".txt", HD);
            }else{
                result = "Caminhos incorretos!";
            }

        }
        //fim da implementacao do aluno
        return result;
    }

    public String rm(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: rm");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        String[] parte = parameters.split(" ");
        String caminho = "";
        String parametro = "";

        if(parte.length == 1){
            caminho = parte[0];
        }else if( parte.length == 2){
            parametro = parte[0];
            caminho = parte[1];
        }else{
            result = "Parametros inválidos!";
        }

        int dir = encontraDiretorio(caminho, dirAtual, HD);

        if (result.equals("")){
            if(!parametro.equals("") && !parametro.equals("-r")){
                result = "Parametro inválido!";
            }if(dir == -1){
                result = "Caminho inválido!";
            }
        }


        if (result.equals("")){
            String resultado = lerStringDoHardDisk(HD, dir, 512);
            String estado = resultado.substring(0, 1);
            String nome = resultado.substring(1, 87).replaceAll("\\s+", "");
            if(estado.equals("a") && parametro.equals("")){
                apagaDiretorio(dir, HD);
                result = nome +  " removido!";
            }else if(estado.equals("d") &&parametro.equals("-r")){
                apagaDiretorioRecursivamente (dir, HD);
                result = nome +  " e seus filhos removidos!";
            }else if(estado.equals("a") &&parametro.equals("-r")){
                result = nome +  " é um arquivo, retire -r para apaga-lo!";
            }else if(estado.equals("d") &&parametro.equals("")){
                result = nome +  " é um diretorio, nada foi removido!";
            }
        }

        //fim da implementacao do aluno
        return result;
    }

    public String chmod(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: chmod");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        String[] parte = parameters.split(" ");
        String caminho = "";
        String permissao = "";
        String parametro = "";
        int dir = -1;

        if(parte.length == 2){
            permissao = parte[0];
            caminho = parte[1];
        }else if( parte.length == 3){
            parametro = parte[0];
            permissao = parte[1];
            caminho = parte[2];
        }else{
            result = "Parametros inválidos!";
        }

        dir = encontraDiretorio(caminho, dirAtual, HD);

        if (result.equals("")){
            if(!parametro.equals("") && !parametro.equals("-r")){
                result = "Parametro inválido!";
            }if (!permissao.matches("\\d{3}$")){
               result = "Permissão inválida!";
            }if(dir == -1){
                result = "Caminho inválido!";
            }
        }

        if (result.equals("")){
            if(parametro.equals("")){
                trocaPermissao(dir,permissao,HD);
            }else{
                trocaPermissaoRecursivamente(dir,permissao,HD);
            }
        }

        //fim da implementacao do aluno
        return result;
    }

    public String createfile(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: createfile");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        String caminho = "";
        String nome = "";
        String caminho_Nome = "";
        String conteudo = "";
        String[] partes = parameters.split(" ", 2);

        if (partes.length >= 2) {
            caminho_Nome = partes[0];
            conteudo = partes[1];
        } else if (partes.length == 1) {
            caminho_Nome = partes[0];
            conteudo = "";
        }

        int lastIndex = caminho_Nome.lastIndexOf('/');
        if (lastIndex >= 0 && lastIndex < caminho_Nome.length() - 1) {
            caminho = caminho_Nome.substring(0, lastIndex);
            nome = caminho_Nome.substring(lastIndex + 1);
        } else {
            nome = caminho_Nome;
            caminho = ".";
        }

        System.out.println("Caminho: " + caminho);
        System.out.println("Nome: " + nome);
        System.out.println("Conteudo: "+conteudo);

        int dirPai = encontraDiretorio(caminho,dirAtual , HD);
        if(dirPai == -1){
            result = "Caminho incorreto!";
        }else if ((nome.contains(" ")) || (!nome.matches("^[A-Za-z].*"))) {
            result = "Nome de arquivos inválido!";
        }else{

            int nomeIgual = -1;
            nomeIgual = comparaNomesArquivosFilhos(dirPai,nome+".txt",HD);
            if(nomeIgual != -1){
                result = "Já existe um arquivo com este nome! ";
            }else{
                int posicaoVazia = procuraPosicaoVaziaHD(HD);
                System.out.println("posição: "+posicaoVazia);
                if(posicaoVazia == -1){
                    result = "HD está cheio!";
                }else{
                    boolean filhosPaiCoube = escreveArgFilhoNoPai(dirPai, posicaoVazia, HD);
                    if(filhosPaiCoube == true){
                        escreverStringNoHardDisk(HD, criaArquivo(nome+".txt",conteudo,dirPai), posicaoVazia);
                    }else{
                        result = "Diretorio: "+encontraNomeDiretorio(dirPai,HD)+" está cheio!";
                    }
                }
            }
        }
        //fim da implementacao do aluno
        return result;
    }

    public String cat(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: cat");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        String caminho;
        String nome;
        int lastIndex = parameters.lastIndexOf('/');

        if (lastIndex >= 0 && lastIndex < parameters.length() - 1) {
            caminho = parameters.substring(0, lastIndex);
            nome = parameters.substring(lastIndex + 1);
        } else {
            nome = parameters;
            caminho = ".";
        }

        System.out.println("Caminho: " + caminho);
        System.out.println("Nome: " + nome);

        int dirPai = encontraDiretorio(caminho,dirAtual , HD);
        int argNum = -1;
        argNum = comparaNomesArquivosFilhos(dirPai,nome+".txt",HD);
        if(dirPai == -1 || argNum == -1){
            result = "Caminho incorreto!";
        }else{
            String arg = lerStringDoHardDisk(HD, argNum, 512);
            result = arg.substring(97, 497).replaceAll("\\s+$", "");
        }

        if(result.equals("")){
            result = "Arquivo vazio!";
        }

        //fim da implementacao do aluno
        return result;
    }

    public String batch(String parameters) {
        //variavel result deverah conter o que vai ser impresso na tela apos comando do usuário
        String result = "";
        System.out.println("Chamada de Sistema: batch");
        System.out.println("\tParametros: " + parameters);

        //inicio da implementacao do aluno
        int arg = encontraDiretorio(parameters, dirAtual, HD);

        if(arg != -1){
            String resultado = lerStringDoHardDisk(HD, arg, 512);
            String estado = resultado.substring(0, 1).replaceAll("\\s+", "");
            if(estado.equals("d")){
                result = "Caminho incorreto!";
            }
        }else{
            result = "Caminho incorreto!";
        }

        if(result.equals("")){
            String resultado = lerStringDoHardDisk(HD, arg, 512);
            String conteudo = resultado.substring(97, 497).replaceAll("\\s+$", "");
            String comando, parametros;

            String[] arrayDeStrings = conteudo.split("\n");
            List<String> config = Arrays.asList(arrayDeStrings);

            for (String linha : config) {
                System.out.println(linha);
            }

            for (int i = 0; i < config.size(); i++) {
                comando = config.get(i).substring(0, config.get(i).indexOf(" "));
                parametros = config.get(i).substring(config.get(i).indexOf(" ") + 1);

                if (comando.equals("ls")) {
                    ls(parametros);
                    result = "Comando executado: ls.";
                } else if (comando.equals("mkdir")) {
                    mkdir(parametros);
                    result = "Comando executado: mkdir.";
                } else if (comando.equals("cd")) {
                    cd(parametros);
                    result = "Comando executado: cd.";
                } else if (comando.equals("rmdir")) {
                    rmdir(parametros);
                    result = "Comando executado: rmdir.";
                } else if (comando.equals("cp")) {
                    cp(parametros);
                    result = "Comando executado: cp.";
                } else if (comando.equals("mv")) {
                    mv(parametros);
                    result = "Comando executado: mv.";
                } else if (comando.equals("rm")) {
                    rm(parametros);
                    result = "Comando executado: rm.";
                } else if (comando.equals("chmod")) {
                    chmod(parametros);
                    result = "Comando executado: chmod.";
                } else if (comando.equals("createfile")) {
                    createfile(parametros);
                    result = "Comando executado: createfile.";
                } else if (comando.equals("cat")) {
                    cat(parametros);
                    result = "Comando executado: cat.";
                } else if (comando.equals("batch")) {
                    batch(parametros);
                    result = "Comando executado: batch.";
                } else if (comando.equals("dump")) {
                    dump(parametros);
                    result = "Comando executado: dump.";
                }
            }
        }

        
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
        String name = "Davi Martins de Sousa";
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

    public static String criaArquivo(String nome, String conteudo, int dirPai) {
        String estado = "a";
        nome = String.format("%-" + 86 + "s", nome);
        String pai = String.format("%-" + 10 + "s", Integer.toString(dirPai));
        conteudo = String.format("%-" + 400 + "s", conteudo);
        String data = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
        String permissao = "777";

        return estado + nome + pai + conteudo + data + permissao;
    }

    public static boolean escreveDirFilhoNoPai(int dir,int filho, HardDisk hd) {
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
            String dirNovo = estado + nome + pai;
            for (int i = 0; i < 20; i++) {
                dirNovo += filhosDir[i];
            }
            dirNovo += filhosArg+ data + permissao;
            escreverStringNoHardDisk(hd, dirNovo , dir);
            return true;
        }else {
            return false;
        }
    }

    public static boolean escreveArgFilhoNoPai(int dir,int filho, HardDisk hd) {
        String resultado = lerStringDoHardDisk(hd, dir, 512);
        
        String estado = resultado.substring(0, 1);
        String nome = resultado.substring(1, 87);
        String pai = resultado.substring(87, 97);
        String filhosDir = resultado.substring(97, 297);

        String[] filhosArg = new String[20];
        for (int i = 0; i < 20; i++) {
            filhosArg[i] = resultado.substring(297 + i * 10, 307 + i * 10);
        }

        String data = resultado.substring(497, 509);
        String permissao = resultado.substring(509);

        boolean encontrado = false;
        for (int i = 0; i < 20; i++) {
            if(filhosArg[i].replaceAll("\\s+", "").equals("") && encontrado == false){
                filhosArg[i] = String.format("%-" + 10 + "s", Integer.toString(filho));
                encontrado = true;
            } 
        }
        
        if(encontrado == true){
            String dirNovo = estado + nome + pai + filhosDir;
            for (int i = 0; i < 20; i++) {
                dirNovo += filhosArg[i];
            }
            dirNovo +=  data + permissao;
            escreverStringNoHardDisk(hd, dirNovo , dir);
            return true;
        }else {
            return false;
        }
    }

    public static void trocaPai(int dir,int paiNum, HardDisk hd) {
        String resultado = lerStringDoHardDisk(hd, dir, 512);
        
        String estado = resultado.substring(0, 1);
        String nome = resultado.substring(1, 87);
        String pai =  String.format("%-" + 10 + "s", Integer.toString(paiNum));
        String restante = resultado.substring(97, 512);
        
        String dirNovo = estado + nome + pai + restante;
        escreverStringNoHardDisk(hd, dirNovo , dir);
    }

    public static void trocaNome(int dir,String nome, HardDisk hd) {
        String resultado = lerStringDoHardDisk(hd, dir, 512);
        
        String estado = resultado.substring(0, 1);
        nome = String.format("%-" + 86 + "s", nome);
        String restante = resultado.substring(87, 512);
        
        String dirNovo = estado + nome + restante;
        escreverStringNoHardDisk(hd, dirNovo , dir);
    }

    public static void trocaPermissao(int dir,String permissao, HardDisk hd){
        String resultado = lerStringDoHardDisk(hd, dir, 512);
        String restante = resultado.substring(0,509);

        String dirNovo = restante + permissao;
        escreverStringNoHardDisk(hd, dirNovo , dir);
    }

    public static void trocaPermissaoRecursivamente(int dir,String permissao, HardDisk hd){
        String resultado = lerStringDoHardDisk(hd, dir, 512);
        
        String estado = resultado.substring(0, 1);
        String nome = resultado.substring(1, 87);
        String pai = resultado.substring(87, 97);

        String[] filhos = new String[40];
        String conteudo = "";

        if(estado.equals("a")){
            conteudo =  resultado.substring(97, 497);
        }else if(estado.equals("d")){
            for (int i = 0; i < 40; i++) {
                filhos[i] = resultado.substring(97 + i * 10, 107 + i * 10);
                if(!filhos[i].replaceAll("\\s+", "").equals("")){
                    trocaPermissaoRecursivamente(Integer.parseInt(filhos[i].replaceAll("\\s+", "")),permissao,hd);
                }
            }
        }
        
        String data = resultado.substring(497, 509);

        if(estado.equals("a")){
            String dirNovo = estado + nome + pai + conteudo + data + permissao;
            escreverStringNoHardDisk(hd, dirNovo , dir);
        }else if(estado.equals("d")){
            String dirNovo = estado + nome + pai;
            for (int i = 0; i < 40; i++) {
                dirNovo += filhos[i];
            }
            dirNovo +=  data + permissao;
            escreverStringNoHardDisk(hd, dirNovo , dir);
        }
    }

    public static void removeFilhoDoPai(int dir,int filho, HardDisk hd) {
        String resultado = lerStringDoHardDisk(hd, dir, 512);
        
        String estado = resultado.substring(0, 1);
        String nome = resultado.substring(1, 87);
        String pai = resultado.substring(87, 97);

        String[] filhos = new String[40];
        for (int i = 0; i < 40; i++) {
            filhos[i] = resultado.substring(97 + i * 10, 107 + i * 10);
        }


        String data = resultado.substring(497, 509);
        String permissao = resultado.substring(509);

        for (int i = 0; i < 40; i++) {
            if(filhos[i].replaceAll("\\s+", "").equals(filho+"")){
                filhos[i] = String.format("%-" + 10 + "s", "");
            } 
        }

        String dirNovo = estado + nome + pai;
        for (int i = 0; i < 40; i++) {
            dirNovo += filhos[i];
        }
        dirNovo += data + permissao;
        escreverStringNoHardDisk(hd, dirNovo , dir);

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

    public static int comparaNomesArquivosFilhos(int dirNum, String nome, HardDisk hd) {
        String dir = lerStringDoHardDisk(hd, dirNum, 512);
        String[] filhosArg = new String[20];

        for (int i = 0; i < 20; i++) {
            filhosArg[i] = dir.substring(297 + i * 10, 307 + i * 10);
        }

        for (int i = 0; i < 20; i++) {
            String num = filhosArg[i].replaceAll("\\s+", "");
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
            //System.out.println("parte do caminho: raiz");
            //System.out.println("diretorio encontrado: raiz");
            return 0;
        }if (caminho.startsWith("/")) {
            caminho = caminho.replaceFirst("/", ""); // Remove o "/" do início da string caminho
            dirAtual = 0;
        }

        caminhos= caminho.split("/");

        //for (String parte : caminhos) {
            //System.out.println("parte do caminho: " + parte);
        //}

        for (int i = 0; i < caminhos.length; i++) {
            String parte = caminhos[i];
            if (!parte.equals(".")) {
                if(parte.equals("..")){
                    dirAtual = encontraDiretorioPai(dirAtual, hd);
                }else{
                    int dirAnt = dirAtual;
                    dirAtual = comparaNomesDiretorioFilhos(dirAtual, parte, hd);
                    if(dirAtual == -1){
                        if (i == caminhos.length - 1){
                            dirAtual = comparaNomesArquivosFilhos(dirAnt, parte, hd);
                            if(dirAtual == -1){
                                System.out.println("Caminho incorreto!");
                                return -1;
                            }
                        }else{
                            System.out.println("Caminho incorreto!");
                            return -1;
                        }    
                    }
                }
            }
        }

        //System.out.println("Diretorio encontrado: "+dirAtual+"");
        return dirAtual;
    }

    public static void apagaDiretorio (int dirNum,HardDisk hd){
        String dir = String.format("%-" + 512 + "s", "");
        int dirPai = encontraDiretorioPai(dirNum,hd);
        removeFilhoDoPai(dirPai, dirNum, hd);
        escreverStringNoHardDisk(hd, dir, dirNum);
    }

    public static void apagaDiretorioRecursivamente (int dirNum,HardDisk hd){
        String resultado = lerStringDoHardDisk(hd, dirNum, 512);

        String[] filhos = new String[40];
        for (int i = 0; i < 40; i++) {
            filhos[i] = resultado.substring(97 + i * 10, 107 + i * 10);
            if(!filhos[i].replaceAll("\\s+", "").equals("")){
                apagaDiretorioRecursivamente(Integer.parseInt(filhos[i].replaceAll("\\s+", "")),hd);
            }
        }
        String dir = String.format("%-" + 512 + "s", "");
        int dirPai = encontraDiretorioPai(dirNum,hd);
        removeFilhoDoPai(dirPai, dirNum, hd);
        escreverStringNoHardDisk(hd, dir, dirNum);
    }

    public static String copiaDiretorio (int origem, int destino ,HardDisk hd){
        String dirOrigem = lerStringDoHardDisk(hd, origem, 512);
        String estadoOrigem = dirOrigem.substring(0, 1).replaceAll("\\s+", "");
        String nomeOrigem = dirOrigem.substring(1, 87).replaceAll("\\s+", "");

        int dirAux = comparaNomesDiretorioFilhos(destino,nomeOrigem,hd);
        if(dirAux != -1){
            return "Nome já presente nos diretórios!";
        }

        dirAux = comparaNomesArquivosFilhos(destino,nomeOrigem,hd);
        if(dirAux != -1){
            return "Nome já presente nos arquivos!";
        }

        int posicaoVazia = procuraPosicaoVaziaHD(hd);
        if(posicaoVazia == -1){
            return "HD está cheio!";
        }

        boolean filhosPaiCoube = escreveDirFilhoNoPai(destino, posicaoVazia, hd);
        if(filhosPaiCoube == true){
            if(estadoOrigem.equals("a")){
                String estado = dirOrigem.substring(0, 1);
                String nome =  dirOrigem.substring(1, 87);
                String pai = String.format("%-" + 10 + "s", Integer.toString(destino));
                String conteudo = dirOrigem.substring(97, 497);
                String data = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                String permissao = dirOrigem.substring(509);
                String dir = estado + nome + pai + conteudo + data + permissao;
                escreverStringNoHardDisk(hd, dir, posicaoVazia);
                return "Arquivo copiado!";
            }else{ // if(estadoOrigem.equals("d"))
                String estado = dirOrigem.substring(0, 1);
                String nome =  dirOrigem.substring(1, 87);
                String pai = String.format("%-" + 10 + "s", Integer.toString(destino));
                String filhos = String.format("%-" + 400 + "s", "");
                String data = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                String permissao = dirOrigem.substring(509);
                String dir = estado + nome + pai + filhos + data + permissao;
                escreverStringNoHardDisk(hd, dir, posicaoVazia);
                return "Diretório copiado!";
            }
        }else{
            return "Diretorio destino cheio!";
        }
    }

    public static String copiaDiretorioRecursivamente (int origem, int destino ,HardDisk hd){
        String dirOrigem = lerStringDoHardDisk(hd, origem, 512);
        String estadoOrigem = dirOrigem.substring(0, 1).replaceAll("\\s+", "");
        String nomeOrigem = dirOrigem.substring(1, 87).replaceAll("\\s+", "");

        int dirAux = comparaNomesDiretorioFilhos(destino,nomeOrigem,hd);
        if(dirAux != -1){
            return "Nome já presente nos diretórios!";
        }

        dirAux = comparaNomesArquivosFilhos(destino,nomeOrigem,hd);
        if(dirAux != -1){
            return "Nome já presente nos arquivos!";
        }

        int posicaoVazia = procuraPosicaoVaziaHD(hd);
        if(posicaoVazia == -1){
            return "HD está cheio!";
        }

        boolean filhosPaiCoube = escreveDirFilhoNoPai(destino, posicaoVazia, hd);
        if(filhosPaiCoube == true){
            if(estadoOrigem.equals("a")){
                String estado = dirOrigem.substring(0, 1);
                String nome =  dirOrigem.substring(1, 87);
                String pai = String.format("%-" + 10 + "s", Integer.toString(destino));
                String conteudo = dirOrigem.substring(97, 497);
                String data = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                String permissao = dirOrigem.substring(509);
                String dir = estado + nome + pai + conteudo + data + permissao;
                escreverStringNoHardDisk(hd, dir, posicaoVazia);
                return "Arquivo copiado!";
            }else{ // if(estadoOrigem.equals("d"))
                String estado = dirOrigem.substring(0, 1);
                String nome =  dirOrigem.substring(1, 87);
                String pai = String.format("%-" + 10 + "s", Integer.toString(destino));
                String filhos = String.format("%-" + 400 + "s", "");
                String data = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                String permissao = dirOrigem.substring(509);
                String dir = estado + nome + pai + filhos + data + permissao;
                escreverStringNoHardDisk(hd, dir, posicaoVazia);
                
                String[] filhosOrigem = new String[40];
                for (int i = 0; i < 40; i++) {
                    filhosOrigem[i] = dirOrigem.substring(97 + i * 10, 107 + i * 10);
                    if(!filhosOrigem[i].replaceAll("\\s+", "").equals("")){
                        copiaDiretorioRecursivamente(Integer.parseInt(filhosOrigem[i].replaceAll("\\s+", "")),posicaoVazia, hd);
                    }
                }
                return "Diretório copiado!";
            }
        }else{
            return "Diretorio destino cheio!";
        }
    }
}
