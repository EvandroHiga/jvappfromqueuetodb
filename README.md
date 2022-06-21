## JvAppFromQueueToDb

### Sobre
> Projeto de conclusão da matéria de *Cloud Development* do MBA ***Full Stack Development - Design, Engineering and Deployment*** na [FIAP](https://www.fiap.com.br/).
> Trata-se de um webapp Java que irá receber mensagens em formato JSON, validá-las e inserí-las em uma queue do Azure Storage Account.
> Trabalha em conjunto com o webapp [jvappfromqueuetodb](https://github.com/EvandroHiga/jvappfromqueuetodb).

### O que é necessário
> JDK 11+, Maven, azure-cli e o Postman.

### Como subir
> 1. Clone este repositório.
> 2. Logue na [Azure](https://portal.azure.com).
> 3. Provisione uma 'Storage Acccount' com os seguintes dados:
> - Resource Group: rsg-fiap-40scj-grp02
> - Storage Account: fiapgrp02storageaccount
> - Criar uma Queue com o seguinte nome: fiapgrp02queue
> 4. Configurar variáveis de ambiente conforme abaixo:
> - ACCOUNT_KEY=O-TOKEN-DO-STORAGE-ACCOUNT
> - ACCOUNT_NAME=fiapgrp02storageaccount
> - ENDPOINT_PROTOCOL=https
> - ENDPOINT_SUFFIX=core.windows.net
> - QUEUE_NAME=fiapgrp02queue
> 5. Iniciar a aplicação


### Casos de teste e como utilizá-los

- **POST : /**

```
[VALIDO]

{
    "valor01":"carlos",
    "valor02":"higa",
    "env":"prd"
}
```

```
[VALIDO]

{
    "valor01":"carlos",
    "valor02":"higa",
    "env":"hml"
}
```

```
[INVALIDO]

{
    "valor01":"carlos",
    "valor02":"higa",
    "env":"abc"
}
```
