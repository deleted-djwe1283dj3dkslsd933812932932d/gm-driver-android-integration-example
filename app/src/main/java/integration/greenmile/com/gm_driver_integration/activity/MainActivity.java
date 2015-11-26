package integration.greenmile.com.gm_driver_integration.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.greenmile.integration.core.GmIntegrationListener;
import com.greenmile.integration.core.domain.IntegrationResponse;
import com.greenmile.integration.core.domain.Route;
import com.greenmile.integration.core.integration.GmIntegration;

import integration.greenmile.com.gm_driver_integration.R;

public class MainActivity extends Activity {

    private ProgressDialog progressDialog;

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

    private GmIntegration gmIntegration;

    private EditText editStopKey, editLogin, editPassword, editEquipment, editRouteId;
    private Button buttonStartRoute, buttonCompleteRoute, buttonOpenMap, buttonArriveStop, buttonDepartStop, buttonLoadRoute, buttonLogin, buttonGetLoadedRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViewElements();
        setOnClickListeners();
        gmIntegration = new GmIntegration(this, gmIntegrationListener);
    }

    private void getViewElements() {
        editStopKey = (EditText) findViewById(R.id.edit_stop_key);
        editEquipment = (EditText) findViewById(R.id.edit_equipment_key);
        editLogin = (EditText) findViewById(R.id.edit_login);
        editPassword = (EditText) findViewById(R.id.edit_password);
        editRouteId = (EditText) findViewById(R.id.edit_route_id);
        buttonStartRoute = (Button) findViewById(R.id.button_start_route);
        buttonCompleteRoute = (Button) findViewById(R.id.button_complete_route);
        buttonOpenMap = (Button) findViewById(R.id.button_open_map);
        buttonArriveStop = (Button) findViewById(R.id.button_arrive_stop);
        buttonDepartStop = (Button) findViewById(R.id.button_depart_stop);
        buttonLoadRoute = (Button) findViewById(R.id.button_load_route);
        buttonLogin = (Button) findViewById(R.id.button_login);
        buttonGetLoadedRoute = (Button) findViewById(R.id.button_get_loaded_route);
    }

    private void setOnClickListeners() {
        buttonStartRoute.setOnClickListener(onClickStartRoute);
        buttonCompleteRoute.setOnClickListener(onClickCompleteRoute);
        buttonOpenMap.setOnClickListener(onClickOpenMap);
        buttonArriveStop.setOnClickListener(onClickArriveStop);
        buttonDepartStop.setOnClickListener(onClickDepartStop);
        buttonLoadRoute.setOnClickListener(onClickLoadRoute);
        buttonLogin.setOnClickListener(onClickLogin);
        buttonGetLoadedRoute.setOnClickListener(onClickGetLoadedRoute);
    }

    private final View.OnClickListener onClickGetLoadedRoute = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Route route = gmIntegration.getFullLoadedRoute();
            if (route != null) {
                Toast.makeText(MainActivity.this, "Chave da Rota: " + route.getAkey(), Toast.LENGTH_LONG).show();
            }
        }
    };

    private final View.OnClickListener onClickLoadRoute = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgressDialog("Downloading Route");
            gmIntegration.loadRoute(editEquipment.getText().toString(), Integer.parseInt(editRouteId.getText().toString()));
        }
    };

    private final View.OnClickListener onClickListRoutes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgressDialog("Downloading Route");
            gmIntegration.listAvailableRoutes(editEquipment.getText().toString());
        }
    };

    private final View.OnClickListener onClickLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgressDialog("Login driver");
            gmIntegration.login(editLogin.getText().toString(), editPassword.getText().toString());
        }
    };

    private final View.OnClickListener onClickStartRoute = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgressDialog("Starting Route");
            gmIntegration.startAndDepartRoute();
        }
    };

    private final View.OnClickListener onClickCompleteRoute = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showProgressDialog("Completing Route");
            gmIntegration.ArriveDestinationAndCompleteRoute();
        }
    };

    private final View.OnClickListener onClickOpenMap = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gmIntegration.openMap();
        }
    };

    private final View.OnClickListener onClickArriveStop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gmIntegration.arriveStop(editStopKey.getText().toString());
        }
    };

    private final View.OnClickListener onClickDepartStop = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gmIntegration.departStop(editStopKey.getText().toString());
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog(String message) {
        progressDialog = ProgressDialog.show(this, "Greenmile", message, true);
    }

    private void showErrorDialog(String action, String errorMessage) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errorMessage);
        alertDialogBuilder.setTitle(action);
        alertDialogBuilder.setPositiveButton("Ok", null);
        alertDialogBuilder.create().show();
    }
}