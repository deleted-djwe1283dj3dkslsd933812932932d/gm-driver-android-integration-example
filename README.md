# gm-driver-android-integration-example

### Aplicativo de exemplo para integração com GM Driver Android

É necessário se registar um **receiver** da `GreenMile` na aplicação a ser integrada.

```xml
<receiver
    android:name="com.greenmile.integration.core.receiver.GmBroadcastReceiver"
    android:enabled="true"
    android:exported="true">
    <intent-filter>
        <action android:name="com.greenmile.gmintegration"/>
    </intent-filter>
</receiver>
```

Todas as ações a serem realizadas pela **integração** deverão utilizar **uma instância do GmIntegration**.
Os parâmetros do construtor são o `contexto` de uma *activity* e um `GmIntegrationListener`.

```java
private GmIntegration gmIntegration = new GmIntegration(this, gmIntegrationListener);
```

O GmIntegrationListener será o responsável por receber a resposta de todas as ações realizadas pelo
GmIntegration. Desde que essas ações não sejam de consulta de dados. O **sucesso** ou **erro** dessa ações
serão recebidas nesse **listener**.

```java
private final GmIntegrationListener gmIntegrationListener = new GmIntegrationListener() {
        @Override
        public void onSuccess(IntegrationResponse response) {
            dismissProgressDialog();
            Toast.makeText(MainActivity.this, "Action realized with success. " + response.getAction(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(IntegrationResponse response) {
            dismissProgressDialog();
            showErrorDialog(response.getAction(), response.getErrorMessage());
            Toast.makeText(MainActivity.this, "Error to realize action: " + response.getAction(), Toast.LENGTH_SHORT).show();
        }
    };
```

Os seguintes métodos estão disponíveis:

- **login**

Realiza o **login** de um usuário usando o login e senha informados.
 
> Essa ação exige rede e pode demorar consideravelmente.

```java
public void login(String login, String password);
```

- **loadRoute**

Realiza a **carga** de uma rota, usando a chave do equipamento e o id da rota informados e o motorista
logado.
 
> Essa ação exige rede e pode demorar consideravelmente.

```java
public void loadRoute(String equipmentKey, Integer routeId);
```

- **listAvailableRoutes**

Lista todas as rotas **disponíveis** para **baixar**, usando a chave de equipamento informada e o
motorista logado. A lista de rotas disponíveis será retornada no **onSuccess** do
**GmIntegrationListener**. 

> Essa ação exige rede e pode demorar consideravelmente.

```java
public void listAvailableRoutes(String equipmentKey);
```

- **departStop**

Realiza a **saida** de um cliente com a chave de parada informada.

```java
public void departStop(String stopKey);
```

- **arriveStop**

Realiza a **chegada** a um cliente com a chave de parada informada.

```java
public void arriveStop(String stopKey);
```

- **startAndDepartRoute**

Realiza as ações de **iniciar rota** e **sair da origem**, na **rota previamente carregada**. 

> Essa ação exige rede e pode demorar consideravelmente.

```java
public void startAndDepartRoute();
```

- **arriveDestinationAndCompleteRoute**

Realiza as ações de **chegar na origem** e **finalizar rota**, na **rota previamente carregada**. 

> Essa ação exige rede e pode demorar consideravelmente.

```java
public void arriveDestinationAndCompleteRoute();
```

- **openMap**

Abre uma tela no **GreenMile Driver** com o **mapa da rota carregada** no momento.

```java
public void openMap();
```

- **getLoadedRoute**

Pega o objeto de **rota correspondente** a **rota que está atualmente carregada** no **GreenMile Driver**.

```java
public Route getLoadedRoute();
```

- **getFullLoadedRoute**

Pega o objeto de **rota correspondente** a **rota que está atualmente carregada** no **GreenMile Driver**. 

> Em adicional esse objeto trará todas as **paradas da rota** e todas as **localidades das paradas**.

As paradas retornadas nesse método podem ainda não ter sido enviadas para o nosso servidor.
Nesse caso as paradas que ainda não foram sincronizadas terão o **id negativo**, e qualquer ação
sobre eles deverá ser feito sobre a **key**.

```java
public Route getFullLoadedRoute();
```

- **getLoadedStops**

Pega uma lista de objetos de **Parada**, correspondente as **paradas atualmente carregadas** no 
**GreenMile Driver**.

```java
public List<Stop> getLoadedStops();
```

- **getLoadedStopById**

Pega um objeto de **parada**, que possua o **id** informado como parâmetro.

```java
public Stop getLoadedStopById(Integer id);
```

- **getLoadedLocations**

Pega um lista de objetos de **localidade**. 

> Essa listagem corresponde a todas as localidades carregadas no **GreenMile Driver**.

```java
public List<Location> getLoadedLocations();
```

- **getLoadedLocationById**

Pega um objeto de **localidade** carregado no **GreenMile Driver**. 

> Esse objeto corresponde ao objeto com o **id** informado como parâmetro.

```java
public Location getLoadedLocationById(Integer id);
```

- **getFullLoadedStops**

Pega a lista de **paradas carregadas** no **GreenMile Driver**.
 
> Os objetos virão com todos os seus **objetos aninhados** também preenchidos.

```java
public List<Stop> getFullLoadedStops();
```

- **getFullLoadedStopById**

Pega o objeto de **parada** com o **id** indicado, que esteja carregado no **GreenMile Driver**.
 
> Os objetos virão com todos os seus **objetos aninhados** também preenchidos.

```java
public Stop getFullLoadedStopById(Integer id);
```

- **getLoadedStopTypeById**

Pega o objeto de **StopType** com o **id** indicado, que esteja carregado no **GreenMile Driver**.

```java
public StopType getLoadedStopTypeById(Integer stopTypeId);
```

- **getLoadedStopTypes**

Pega um lista de objetos de **StopType**. 

> Essa listagem corresponde a todos os tipos de stopTypes carregadas no **GreenMile Driver**.

```java
public List<StopType> getLoadedStopTypes();
```

- **getLoadedCancelCodes**

Pega um lista de objetos de **CancelCode**. 

> Essa listagem corresponde a todos os tipos de cancelCode carregadas no **GreenMile Driver**.

```java
public List<CancelCode> getLoadedCancelCodes();
```

- **getLoadedCancelCodeById**

Pega o objeto de **CancelCode** com o **id** indicado, que esteja carregado no **GreenMile Driver**.

```java
public CancelCode getLoadedCancelCodeById(Integer id);
```

- **getLoadedUndeliverableCodes**

Pega um lista de objetos de **UndeliverableCode**. 

> Essa listagem corresponde a todos os tipos de undeliverableCode carregadas no **GreenMile Driver**.

```java
public List<UndeliverableCode> getLoadedUndeliverableCodes();
```

- **getLoadedUndeliverableCodeById**

Pega o objeto de **UndeliverableCode** com o **id** indicado, que esteja carregado no **GreenMile Driver**.

```java
public UndeliverableCode getLoadedUndeliverableCodeById(Integer id);
```

- **cancelStop**

Cancela a **parada** que possua a **chave de cliente indicada**. 

> O motivo de cancelamento usado, será o utilizado como parâmetro.

```java
public void cancelStop(String stopKey, Integer cancelCodeId);
```

- **undeliveryStop**

Realiza a **ação** de **não entrega** na **parada** que possua a chave de cliente indicada. 

> O motivo de não entrega usado, será o utilizado como parâmetro.

```java
public void undeliveryStop(String stopKey, Integer undeliverableCodeId);
```

- **redeliveryStop**

Realiza a **ação** de **voltar depois** na **parada** que possua a chave de cliente indicada. 

> O motivo de não entrega usado, será o utilizado como parâmetro.

```java
public void redeliveryStop(String stopKey, Integer undeliverableCodeId);
```

- **addStop**

Adicionada como parada e o id do tipo de parada. Os tipos de paradas disponíveis podem ser
consultados com a chamada **getLoadedStopTypes()**. 

> Essa ação exige rede e pode demorar consideravelmente.

```java
public void addStop(String locationKey, Integer stopTypeId);
```