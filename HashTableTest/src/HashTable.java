import java.util.LinkedList;

public class HashTable {

    private LinkedList<Registro>[] tabelaEncadeada;
    private Registro[] tabelaLinear;
    private int tamanho;
    private int tipoHash;
    private boolean rehashing;
    private int colisoes;
    private int comparacoesBusca;

    @SuppressWarnings("unchecked")
    public HashTable(int tamanho, int tipoHash, boolean rehashing) {
        this.tamanho = tamanho;
        this.tipoHash = tipoHash;
        this.rehashing = rehashing;
        this.colisoes = 0;
        this.comparacoesBusca = 0;

        if (rehashing) {
            this.tabelaLinear = new Registro[tamanho];
        } else {
            this.tabelaEncadeada = new LinkedList[tamanho];
            for (int i = 0; i < tamanho; i++) {
                this.tabelaEncadeada[i] = new LinkedList<>();
            }
        }
    }

    private int hashRest(int key) {
        return key % tamanho;
    }

    private int hashMultiplicacao(int key) {
        double A = 0.6180339887;
        return (int) (tamanho * ((key * A) % 1));
    }

    private int hashDobramento(int key) {
        String keyStr = String.valueOf(key);
        int sum = 0;
        for (char c : keyStr.toCharArray()) {
            sum += c - '0';
        }
        return sum % tamanho;
    }

    private int hash(int key) {
        switch (tipoHash) {
            case 1:
                return hashRest(key);
            case 2:
                return hashMultiplicacao(key);
            case 3:
                return hashDobramento(key);
            default:
                throw new IllegalArgumentException("Tipo de função hash inválido");
        }
    }

    public void inserir(Registro registro) {
        int indice = hash(registro.codigo);

        if (rehashing) {
            while (tabelaLinear[indice] != null) {
                colisoes++;
                indice = (indice + 1) % tamanho;
            }
            tabelaLinear[indice] = registro;
        } else {
            if (!tabelaEncadeada[indice].isEmpty()) colisoes++;
            tabelaEncadeada[indice].add(registro);
        }
    }

    public boolean buscar(int codigo) {
        int indice = hash(codigo);
        comparacoesBusca = 0;

        if (rehashing) {
            while (tabelaLinear[indice] != null) {
                comparacoesBusca++;
                if (tabelaLinear[indice].codigo == codigo) {
                    return true;
                }
                indice = (indice + 1) % tamanho;
            }
        } else {
            LinkedList<Registro> lista = tabelaEncadeada[indice];
            for (Registro reg : lista) {
                comparacoesBusca++;
                if (reg.codigo == codigo) {
                    return true;
                }
            }
        }

        return false;
    }

    public int getColisoes() {
        return colisoes;
    }

    public int getComparacoesBusca() {
        return comparacoesBusca;
    }
}
