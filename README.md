gm-driver-android-integration-example
=====================================
É necessário se registar um receiver de greenmile na aplicação a ser integrada.
```xml
<receiver
    android:name="com.greenmile.integration.core.receiver.GmBroadcastReceiver"
    android:enabled="true"
    android:exported="true" >
    <intent-filter>
        <action android:name="com.greenmile.gmintegration" />
    </intent-filter>
</receiver>
```

Todas as ações a serem realizadas pela integração deverão usar uma instancia do GmIntegration. Os parâmetros construtores são o contexto de uma activity e um GmIntegrationListener.
```java
private GmIntegration gmIntegration = new GmIntegration(this, gmIntegrationListener);
```

O GmIntegrationListener será o responsável por receber a resposta de todas as ações realizadas pelo GmIntegration. Desde que essas ações não sejam de consulta de dados. O sucesso ou erro dessa ações serão recebidas nesse listener.
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
Os seguintes métodos estão disponíveis.
```java
//Realiza o login de um usuário usando o login e senha informados. Essa ação exige rede e pode demorar consideravelmente.
public void login(String login, String password);
```
```java
//Realiza a carga de uma rota, usando a chave de equipamento e id de rota informados e o motorista logado. Essa ação exige rede e pode demorar consideravelmente.
public void loadRoute(String equipmentKey, Integer routeId);
```
```java
//Lista todas as rotas disponíveis para baixar, usando a chave de equipamento informada e o motorista logado. A lista de rotas disponíveis será retornada no onSuccess do GmIntegrationListener. Essa ação exige rede e pode demorar consideravelmente.
public void listAvailableRoutes(String equipmentKey);
```
```java
//Realiza a saida de um cliente com a chave de parada informada.
public void departStop(String stopKey);
```
```java
//Realiza a chegada a um cliente com a chave de parada informada.
public void arriveStop(String stopKey);
```
```java
//Realiza as ações de iniciar rota e sair da origem, na rota previamente carregada. Essa ação exige rede e pode demorar consideravelmente.
public void startAndDepartRoute();
```
```java
//Realiza as ações de chegar na origem e finalizar rota, na rota previamente carregada. Essa ação exige rede e pode demorar consideravelmente.
public void ArriveDestinationAndCompleteRoute();
```
```java
//Abre uma tela no greenmile com a mapa da rota carregada no momento.
public void openMap();
```
```java
//Pega o objeto de rota correspondente a rota que está atualmente carregada no greenmile.
public Route getLoadedRoute();
```
```java
//Pega o objeto de rota correspondente a rota que está atualmente carregada no greenmile. Em adicional esse objeto trará todas as paradas da rota e todas as localidades das paradas.
public Route getFullLoadedRoute();
```
```java
//Pega uma lista de objetos de Parada, correspondente as paradas atualmente carregadas no greenmile.
public List<Stop> getLoadedStops();
```
```java
//Pega um objeto de parada, que possua o id informado como parâmetro.
public Stop getLoadedStopById(Integer id);
```
```java
//Pega um lista de objetos de localidade. Essa listagem corresponde a todos as localidades carregadas no greenmile driver.
public List<Location> getLoadedLocations();
```
```java
//Pega um objeto de localidade carregado no greenmile driver. Esse objeto corresponde ao objeto com o id informado como parâmetro.
public Location getLoadedLocationById(Integer id);
```
```java
//Pega a lista de stops carregados no greenmile driver. Os objetos virão com todos os seus objetos aninhados também preenchidos.
public List<Stop> getFullLoadedStops();
```
```java
//Pega o objeto de stop com o id indicado, que esteja carregado no greenmile driver. Os objetos virão com todos os seus objetos aninhados também preenchidos.
public Stop getFullLoadedStopById(Integer id);
```
```java
//Pega o objeto de stopType com o id indicado, que esteja carregado no greenmile driver.
public StopType getLoadedStopTypeById(Integer stopTypeId);
```
```java
//Pega um lista de objetos de stopType. Essa listagem corresponde a todos os tipos de stopTypes carregadas no greenmile driver.
public List<StopType> getLoadedStopTypes();
```
```java
//Pega um lista de objetos de cancelCode. Essa listagem corresponde a todos os tipos de cancelCode carregadas no greenmile driver.
public List<CancelCode> getLoadedCancelCodes();
```
```java
//Pega o objeto de cancelCode com o id indicado, que esteja carregado no greenmile driver.
public CancelCode getLoadedCancelCodeById(Integer id);
```
```java
//Pega um lista de objetos de undeliverableCode. Essa listagem corresponde a todos os tipos de undeliverableCode carregadas no greenmile driver.
public List<UndeliverableCode> getLoadedUndeliverableCodes();
```
```java
//Pega o objeto de undeliverableCode com o id indicado, que esteja carregado no greenmile driver.
public UndeliverableCode getLoadedUndeliverableCodeById(Integer id);
```
```java
//Cancela a parada que possua a chave de cliente indicada. O motivo de cancelamento usado, sera o usado como parametro.
public void cancelStop(String stopKey, Integer cancelCodeId);
```
```java
//Realiza a ação de não entrega na parada que possua a chave de cliente indicada. O motivo de não entrega usado, sera o usado como parametro.
public void undeliveryStop(String stopKey, Integer undeliverableCodeId);
```
```java
//Realiza a ação de voltar depois na parada que possua a chave de cliente indicada. O motivo de não entrega usado, sera o usado como parametro.
public void redeliveryStop(String stopKey, Integer undeliverableCodeId);
```
```java
//Adicionada como parada e o id do tipo de parada. Os tipos de paradas disponíveis podem ser consultados com a chamada getLoadedStopTypes(). Essa ação exige rede e pode demorar consideravelmente.
public void addStop(String locationKey, Integer stopTypeId);
```
