package main;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Diretorio {
    private String diretorioNome;
    private Diretorio diretorioPai;
    private List<Diretorio> diretorioFilhos;
    private ArrayList<Arquivo> arquivosFilhos;
    private LocalDateTime dataCriacao;
    private String permicao;

    public Diretorio encontraDiretorio(String caminho, Diretorio raiz) {
        String[] caminhoDiretorio = new String[0];

        caminho = caminho.replaceFirst("^/|^\\./", ""); // Remove o "/" ou "./" do início da string caminho
        caminhoDiretorio = caminho.split("/");

        for (String parte : caminhoDiretorio) {
            System.out.println("parte do caminho: " + parte);
        }

        Diretorio diretorioAtual = raiz;

        for (String parte : caminhoDiretorio) {
            if (!parte.isEmpty()) {
                boolean encontrado = false;
                for (Diretorio filho : diretorioAtual.diretorioFilhos) {
                    if (filho.getDiretorioNome().equals(parte)) {
                        diretorioAtual = filho;
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) {
                    return null; // Se não encontrar algum diretório, retorna null
                }
            }
        }

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

        alvoDiretorio = encontraDiretorio(caminho, raiz);
        if (alvoDiretorio == null) {
            return "Caminho Incorreto";
        }

        else if (alvoDiretorio.diretorioFilhos.isEmpty() && alvoDiretorio.arquivosFilhos.isEmpty()) {
            return "Não contém arquivos ou diretórios!";
        }

        else if (parametro.equals("")) {
            for (Diretorio filho : alvoDiretorio.diretorioFilhos) {
                result.append(filho.getDiretorioNome()).append(" ");
            }
            for (Arquivo filho : this.arquivosFilhos) {
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
            for (Arquivo filho : this.arquivosFilhos) {
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
