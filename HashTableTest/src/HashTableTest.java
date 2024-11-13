import java.util.Random;

public class HashTableTest {

    public static void main(String[] args) {
        int[] tamanhosVetores = {100, 1000, 10000};
        int[] dadosTamanhos = {1000000, 5000000, 20000000};

        for (int tamanho : tamanhosVetores) {
            System.out.println("Tamanho da Tabela Hash: " + tamanho);

            for (int tipoHash = 1; tipoHash <= 3; tipoHash++) {
                for (boolean rehashing : new boolean[]{false, true}) {
                    HashTable hashTable = new HashTable(tamanho, tipoHash, rehashing);
                    String modoColisao = rehashing ? "Rehashing Linear" : "Encadeamento";
                    System.out.println("Função Hash Tipo: " + tipoHash + " | Modo de Colisão: " + modoColisao);

                    Random random = new Random(42);
                    long inicioInsercao = System.nanoTime();
                    for (int i = 0; i < dadosTamanhos[0]; i++) {
                        int codigo = 100000000 + random.nextInt(900000000);
                        hashTable.inserir(new Registro(codigo));
                    }
                    long fimInsercao = System.nanoTime();
                    System.out.println("Tempo de Inserção: " + (fimInsercao - inicioInsercao) / 1e6 + " ms");
                    System.out.println("Colisões: " + hashTable.getColisoes());

                    long inicioBusca = System.nanoTime();
                    int totalComparacoes = 0;
                    int buscas = 5;
                    for (int i = 0; i < buscas; i++) {
                        int codigo = 100000000 + random.nextInt(900000000);
                        hashTable.buscar(codigo);
                        totalComparacoes += hashTable.getComparacoesBusca();
                    }
                    long fimBusca = System.nanoTime();
                    System.out.println("Tempo de Busca Médio: " + ((fimBusca - inicioBusca) / buscas) / 1e6 + " ms");
                    System.out.println("Comparações de Busca Médias: " + (totalComparacoes / buscas));
                    System.out.println();
                }
            }
        }
    }
}
