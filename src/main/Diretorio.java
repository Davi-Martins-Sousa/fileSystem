package main;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Diretorio {
    private String diretorioNome;
    private Diretorio diretorioPai;
    private List<Diretorio> diretorioFilhos;
    private LocalDateTime dataCriacao;
    private String permicao;

    //mkdir - cria diretorios
    public Diretorio mkdir(String diretorioNome,Diretorio diretorioPai) {
        this.diretorioNome = diretorioNome;
        this.diretorioPai = diretorioPai;
        this.permicao = "drwxrwxrwx";
        this.dataCriacao = LocalDateTime.now();
        this.diretorioFilhos = new ArrayList<Diretorio>();

        if (diretorioPai != null) {
            diretorioPai.diretorioFilhos.add(this);
        }

        return this;
    }

    //ls: lista diretorios filhos
    public void ls() {
        if (diretorioFilhos.isEmpty()) {
            //System.out.println( diretorioNome +" não possue diretórios filhos.");
            return;
        }

        //System.out.println("Diretórios filhos de " + diretorioNome + ":");
        for (Diretorio filho : diretorioFilhos) {
            System.out.println("\t"+filho.getDiretorioNome());
        }
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

    public List<Diretorio> getDiretorioFilhost() {
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
