## CT-200 - Trabalho 1 - Aut�matos Finitos
---
**Equipe**
- Ederson Monteiro de Oliveira Donde
- Filipe Spuri Ribeiro Silva
- Vinicius Jose Silveira de Souza
     
---    
Os algoritmos foram implementador em Java 8. O arquivo _jar_ para execu��o se encontra na pasta _bin_ do zip e pode ser gerado a partir do comando Maven:
```
mvn clean package
```

Para executar a aplica��o, h� duas op��es. A primeira � fornecendo como primeiro par�metro a express�o regular a ser convertida em um aut�mato finito atrav�s do par�metro _regex_, como no exemplo abaixo:

```
java -jar trabalho1.jar regex='(a+b)*bb(b+a)*'
```
A segunda op��o � fornecer um arquivo de entrada contendo a descri��o do aut�mata, onde cada linha do arquivo representa uma transi��o no formato:
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

Para verificar se o aut�mata aceita uma cadeia de entrada, basta fornec�-la como par�metro para a linha de comando:

```
java -jar trabalho1.jar abbba < automata1.txt
```

Executado a aplica��o, ela apresentar� textualmente o aut�mato, remover� as arestas Epsilon e gerar� a regex que representa o aut�mata. Ao final, caso seja pedido, tamb�m informar� se a cadeia de entrada � aceita ou n�o pelo aut�mata fornecido.

  


~~~