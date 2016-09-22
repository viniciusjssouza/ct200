## CT-200 - Trabalho 1 - Autômatos Finitos
---
**Equipe**
- Ederson Monteiro de Oliveira Donde
- Filipe Spuri Ribeiro Silva
- Vinicius Jose Silveira de Souza
     
---    
Os algoritmos foram implementador em Java 8. O arquivo _jar_ para execução se encontra na pasta _bin_ do zip e pode ser gerado a partir do comando Maven:
```
mvn clean package
```

Para executar a aplicação, há duas opções. A primeira é fornecendo como primeiro parâmetro a expressão regular a ser convertida em um autômato finito através do parâmetro _regex_, como no exemplo abaixo:

```
java -jar trabalho1.jar regex='(a+b)*bb(b+a)*'
```
A segunda opção é fornecer um arquivo de entrada contendo a descrição do autômata, onde cada linha do arquivo representa uma transição no formato:
```
<nodeA>, <nodeB>, <input>
```
Por exemplo:
```
0, 4, &
4, 4, a
4, 4, b
4, 2, &
2, 3, b
3, 5, b
5, 6, &
6, 6, a
6, 6, b
6, 1, & 
```
Exemplo da chamada de linha de comando:
```
java -jar trabalho1.jar < automata1.txt
```

Para verificar se o autômata aceita uma cadeia de entrada, basta fornecê-la como parâmetro para a linha de comando:

```
java -jar trabalho1.jar abbba < automata1.txt
```

Executado a aplicação, ela apresentará textualmente o autômato, removerá as arestas Epsilon e gerará a regex que representa o autômata. Ao final, caso seja pedido, também informará se a cadeia de entrada é aceita ou não pelo autômata fornecido.

  


~~~