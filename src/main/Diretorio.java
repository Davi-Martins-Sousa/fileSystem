package main;

import java.util.ArrayList;
import java.util.List;
import java.net.SocketAddress;
import java.time.LocalDateTime;

public class Diretorio {
    private String diretorioNome;
    private Diretorio diretorioPai;
    private List<Diretorio> diretorioFilhos;
    private ArrayList<Arquivo> arquivosFilhos;
    private LocalDateTime dataCriacao;
    private String permicao;

    public Diretorio encontraDiretorio(String caminho, Diretorio raiz, Diretorio atualDiretorio) {
        String[] caminhoDiretorio = new String[0];
        Diretorio diretorioAtual;

        if(caminho.equals("/")){
            System.out.println("parte do caminho: raiz");
            System.out.println("diretorio encontrado: raiz");
            return raiz;
        }
        

        if (caminho.startsWith("/")) {
            caminho = caminho.replaceFirst("/", ""); // Remove o "/" do início da string caminho
            diretorioAtual = raiz;
        } else {
            diretorioAtual = atualDiretorio;
        }

        caminhoDiretorio = caminho.split("/");

        for (String parte : caminhoDiretorio) {
            System.out.println("parte do caminho: " + parte);
        }
       

        for (String parte : caminhoDiretorio) {
            if (!parte.equals(".")) {
                boolean encontrado = false;
                for (Diretorio filho : diretorioAtual.diretorioFilhos) {
                    if(parte.equals("..")){
                        encontrado = true;
                        diretorioAtual = diretorioAtual.diretorioPai;
                        break;
                    }
                    else if (filho.getDiretorioNome().equals(parte)) {
                        diretorioAtual = filho;
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    System.out.println("Caminho não encontrado");
                    return null; // Se não encontrar algum diretório, retorna null
                }
            }
        }

        System.out.println("diretorio encontrado: "+diretorioAtual.getDiretorioNome());
        return diretorioAtual;
    }

    // ls: lista diretorios filhos
    public String ls(String parameters, Diretorio raiz) {
        StringBuilder result = new StringBuilder();
        String parametro = "";
        String caminho = "";
        Diretorio alvoDiretorio = null;

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

        if (caminho.equals("")) {
            if (diretorioFilhos.isEmpty() && arquivosFilhos.isEmpty()) {
                return "Não contém arquivos ou diretórios!";
            }

            else if (parametro.equals("")) {
                for (Diretorio filho : this.diretorioFilhos) {
                    result.append(filho.getDiretorioNome()).append(" ");
                }
                for (Arquivo filho : this.arquivosFilhos) {
                    result.append(filho.getArquivoNome()).append(" ");
                }
                return result.toString().trim();
            }

            else if (parametro.equals("-l")) {
                for (Diretorio filho : this.diretorioFilhos) {
                    result.append(filho.getDiretorioNome()).append(" ");
                    result.append(filho.getDataCriacao()).append(" ");
                    result.append(filho.getPermicao()).append(" ");
                    result.append("\n");
                }
                for (Arquivo filho : this.arquivosFilhos) {
                    result.append(filho.getArquivoNome()).append(" ");
                    result.append(filho.getDataCriacao()).append(" ");
                    result.append(filho.getPermicao()).append(" ");
                    result.append("\n");
                }
                return result.toString().trim();
            }
        }

        alvoDiretorio = encontraDiretorio(caminho, raiz, this);
        if (alvoDiretorio == null) {
            return "Caminho incorreto";
        }

        else if (alvoDiretorio.diretorioFilhos.isEmpty() && alvoDiretorio.arquivosFilhos.isEmpty()) {
            return "Não contém arquivos ou diretórios!";
        }

        else if (parametro.equals("")) {
            for (Diretorio filho : alvoDiretorio.diretorioFilhos) {
                result.append(filho.getDiretorioNome()).append(" ");
            }
            for (Arquivo filho : alvoDiretorio.arquivosFilhos) {
                result.append(filho.getArquivoNome()).append(" ");
            }
            return result.toString().trim();
        }

        else if (parametro.equals("-l")) {
            for (Diretorio filho : alvoDiretorio.diretorioFilhos) {
                result.append(filho.getDiretorioNome()).append(" ");
                result.append(filho.getDataCriacao()).append(" ");
                result.append(filho.getPermicao()).append(" ");
                result.append("\n");
            }
            for (Arquivo filho : alvoDiretorio.arquivosFilhos) {
                result.append(filho.getArquivoNome()).append(" ");
                result.append(filho.getDataCriacao()).append(" ");
                result.append(filho.getPermicao()).append(" ");
                result.append("\n");
            }
            return result.toString().trim();
        }
        return "erro";
    }

    // mkdir: cria diretorios
    public Diretorio mkdir(String diretorioNome, Diretorio diretorioPai) {
        this.diretorioNome = diretorioNome;
        this.diretorioPai = diretorioPai;
        this.permicao = "drwxrwxrwx";
        this.dataCriacao = LocalDateTime.now();
        this.diretorioFilhos = new ArrayList<Diretorio>();
        this.arquivosFilhos = new ArrayList<Arquivo>();

        if (diretorioPai != null) {
            diretorioPai.diretorioFilhos.add(this);
        }

        return this;
    }

    // cd: navega entre os diretorios
    public Diretorio cd(String parameters) {
        return null; // Retorna o próprio diretório se não encontrar o subdiretório desejado
    }

    // rmdir: remove diretorio sem filhos
    public String rmdir(String parameters, Diretorio raiz) {
        Diretorio alvoDiretorio =  encontraDiretorio(parameters,raiz,this);
        
        if (alvoDiretorio == null) {
            return "Caminho incorreto!";
        }else if (alvoDiretorio.diretorioFilhos.isEmpty() && alvoDiretorio.arquivosFilhos.isEmpty()){
            alvoDiretorio.diretorioPai.diretorioFilhos.remove(alvoDiretorio);
            alvoDiretorio = null;
            return "Diretorio apagado.";
        }else{
            return "Diretorio possui filhos e não pode ser apagado!";
        }
    }

    public String cp(String parameters, Diretorio raiz){
        String parametro = "";
        String origem = "";
        String destino = "";
        String nomeArquivo;
        Arquivo origemArquivo = null;
        Diretorio origemDiretorio = null;
        Diretorio destinoDiretorio = null;

        if (parameters.startsWith("-r ")){
            parameters = parameters.replaceFirst("-r ", "");
            parametro = "-r";
        }

        String[] partes = parameters.split(" ", 2);

        if (partes.length >= 2) {
            origem = partes[0];
            destino = partes[1];
        } else  {
            return "Parametro incorreto!";
        }

        System.out.println("Parametro: " + parametro);
        System.out.println("Origem: " + origem);
        System.out.println("Destino: "+ destino);

        origemDiretorio = encontraDiretorio(origem, raiz, this);
        destinoDiretorio = encontraDiretorio(destino, raiz, this);

        if(destinoDiretorio == null){
            return "Caminho de destino incorreto!";
        }

        if(origemDiretorio == null && parametro.equals("-r")){
            return "Caminho de origem incorreto!";
        }

        if(origemDiretorio == null){
            int lastIndex = origem.lastIndexOf('/');
            if (lastIndex >= 0 && lastIndex < origem.length() - 1) {
                origem = origem.substring(0, lastIndex);
                nomeArquivo = origem.substring(lastIndex + 1);
                destinoDiretorio = encontraDiretorio(origem, raiz, this);
                for (Arquivo filho : destinoDiretorio.arquivosFilhos) {
                    if(filho.getArquivoNome().equals(nomeArquivo)){
                        origemArquivo = filho;
                    }
                }
            }
        }

        if(origemArquivo == null && origemDiretorio == null){
            return "Caminho de origem do arquivo incorreto!";
        }

        if(parametro.equals("-r") && origemArquivo == null){
            //FAZER
            //FAZER
            //FAZER
            //FAZER
            //FAZER
            //FAZER
            //FAZER
            //FAZER
            //FAZER
            //FAZER
            //FAZER
            //FAZER
            return "";

        }else if (parametro.equals("") && origemArquivo == null){
            Diretorio diretorio = new Diretorio();

            for (Diretorio filho : destinoDiretorio.diretorioFilhos) {
                if(filho.getDiretorioNome().equals((origemDiretorio.getDiretorioNome()))){
                    diretorio.setDiretorioNome(origemDiretorio.getDiretorioNome()+"-novo");
                }
            }

            if(diretorio.getDiretorioNome().equals("")){
                diretorio.setDiretorioNome(origemDiretorio.getDiretorioNome());
            }

            diretorio.setDataCriacao(origemDiretorio.getDataCriacao());
            diretorio.setPermicao(origemDiretorio.getPermicao());
            diretorio.setDiretorioPai(destinoDiretorio);
            this.diretorioFilhos = new ArrayList<Diretorio>();
            this.arquivosFilhos = new ArrayList<Arquivo>();

            destinoDiretorio.diretorioFilhos.add(diretorio);

            return "Diretorio copiado.";
        }else{

            Arquivo arquivo = new Arquivo();

            for (Arquivo filho : destinoDiretorio.arquivosFilhos) {
                if(filho.getArquivoNome().equals((origemArquivo.getArquivoNome()))){
                    arquivo.setArquivoNome(origemArquivo.getArquivoNome()+"-novo");
                }
            }

            if(arquivo.getArquivoNome().equals("")){
                arquivo.setArquivoNome(origemArquivo.getArquivoNome());
            }

            arquivo.setDataCriacao(origemArquivo.getDataCriacao());
            arquivo.setPermicao(origemArquivo.getPermicao());
            arquivo.setTexto(origemArquivo.getTexto());
            arquivo.setDiretorioPai(destinoDiretorio);

            destinoDiretorio.arquivosFilhos.add(arquivo);

            return "Arquivo copiado.";
        }
    }

     public String mv(String parameters, Diretorio raiz) {

        String origem = "";
        String destino = "";
        String nomeArquivo;
        Arquivo origemArquivo = null;
        Diretorio origemDiretorio = null;
        Diretorio destinoDiretorio = null;
        String[] partes = parameters.split(" ", 2);

        if (partes.length >= 2) {
            origem = partes[0];
            destino = partes[1];
        } else  {
            return "Parametro incorreto!";
        }

        System.out.println("Origem: " + origem);
        System.out.println("Destino: "+ destino);

        origemDiretorio = encontraDiretorio(origem, raiz, this);
        destinoDiretorio = encontraDiretorio(destino, raiz, this);

        if(destinoDiretorio == null){
            return "Caminho de destino incorreto!";
        }

        if(origemDiretorio == null){
            int lastIndex = origem.lastIndexOf('/');
            if (lastIndex >= 0 && lastIndex < origem.length() - 1) {
                origem = origem.substring(0, lastIndex);
                nomeArquivo = origem.substring(lastIndex + 1);
                destinoDiretorio = encontraDiretorio(origem, raiz, this);
                for (Arquivo filho : destinoDiretorio.arquivosFilhos) {
                    if(filho.getArquivoNome().equals(nomeArquivo)){
                        origemArquivo = filho;
                    }
                }
            }
        }

        if(origemArquivo == null && origemDiretorio == null){
            return "Caminho de origem incorreto!";
        }

        if(origemArquivo == null){
            if(origemDiretorio.getDiretorioPai() == destinoDiretorio.getDiretorioPai()){
                
            }
        }else{

        }
        return"";

     }
    // createfile: cria arquivo de texto
    public String createfile(String parameters, Diretorio raiz) {
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

        if ((nome.contains(" ")) || (!nome.matches("^[A-Za-z0-9].*"))) {
            return "Nome de diretório pode começar apenas com letras e números e não pode conter espaços!";
        }

        Diretorio alvoDiretorio = encontraDiretorio(caminho, raiz, this);

        System.out.println("nome diretorio pai "+alvoDiretorio.getDiretorioNome());

        if(alvoDiretorio.equals(null)){
            return "Caminho incorreto!";
        }

        for (Arquivo filho : alvoDiretorio.arquivosFilhos) {
            if(filho.getArquivoNome().equals(nome)){
                return "Já existe um arquivo com este nome neste diretório!";
            }
        }

        Arquivo arquivo = new Arquivo();
        arquivo.setArquivoNome(nome);
        arquivo.setDataCriacao(LocalDateTime.now());
        arquivo.setPermicao("drwxrwxrwx");
        arquivo.setTexto(conteudo);

        alvoDiretorio.arquivosFilhos.add(arquivo);

        return "Arquivo "+nome+" criado!";
    }
     public String cat(String parameters, Diretorio raiz) {
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

        Diretorio alvoDiretorio = encontraDiretorio(caminho, raiz, this);

        System.out.println("nome diretorio pai "+alvoDiretorio.getDiretorioNome());

        if(alvoDiretorio.equals(null)){
            return "Caminho incorreto!";
        }

        for (Arquivo filho : alvoDiretorio.arquivosFilhos) {
            if(filho.getArquivoNome().equals(nome)){
                if(filho.getTexto().equals("")){
                    return"Arquivo vazio!";
                }
                return filho.getTexto();
            }
        }

        return "Arquivo vazio!";
     }

    // Getters e Setters
    public String getDiretorioNome() {
        return diretorioNome;
    }

    public void setDiretorioNome(String diretorioNome) {
        this.diretorioNome = diretorioNome;
    }

    public Diretorio getDiretorioPai() {
        return diretorioPai;
    }

    public void setDiretorioPai(Diretorio diretorioPai) {
        this.diretorioPai = diretorioPai;
    }

    public List<Diretorio> getDiretorioFilhos() {
        return diretorioFilhos;
    }

    public void setDiretorioFilhos(List<Diretorio> diretorioFilhos) {
        this.diretorioFilhos = diretorioFilhos;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getPermicao() {
        return permicao;
    }

    public void setPermicao(String permicao) {
        this.permicao = permicao;
    }
}
