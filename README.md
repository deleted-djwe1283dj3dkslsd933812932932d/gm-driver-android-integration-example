gm-driver-android-integration-example
=====================================
É necessário se registar um receiver de greenmile na aplicação a ser integrada.
```xml
<receiver
            android:name=".receiver.GmBroadcastReceiver"
            android:enabled="true"
            android:exported="true" >
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
        public void onSuccess(String action) {
            Toast.makeText(MainActivity.this, "Action realized with success. " + action, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String action, String errorMessage) {
            Toast.makeText(MainActivity.this, "Error to realize action: " + action, Toast.LENGTH_SHORT).show();
        }
};
```
Os seguintes métodos estão disponíveis.
```java
//Realiza o login de um usuário. Essa ação exige rede e pode demorar consideravelmente.
public void login(String login, String password);
```
```java
//Realiza a carga de uma rota. Essa ação exige rede e pode demorar consideravelmente.
public void loadRoute(String equipmentKey);
```
```java
//Realiza a saida de um cliente.
public void departStop(String stopKey);
```
```java
//Realiza a chegada a um cliente.
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
//Pela um lista de objetos de localidade. Essa listagem corresponde a todos as localidades carregadas no greenmile driver.
public List<Location> getLoadedLocations();
```
```java
//Pela um objeto de localidade. Esse objeto corresponde ao objeto com o id informado como parâmetro.
public Location getLoadedLocationById(Integer id);
```
