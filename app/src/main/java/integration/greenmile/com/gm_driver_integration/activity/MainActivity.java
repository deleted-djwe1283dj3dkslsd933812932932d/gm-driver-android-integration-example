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
import com.greenmile.integration.core.integration.GmIntegration;

import integration.greenmile.com.gm_driver_integration.R;

public class MainActivity extends Activity {

    private ProgressDialog progressDialog;

    private final GmIntegrationListener gmIntegrationListener = new GmIntegrationListener() {
        @Override
        public void onSuccess(String action) {
            dismissProgressDialog();
            Toast.makeText(MainActivity.this, "Action realized with success. " + action, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(String action, String errorMessage) {
            dismissProgressDialog();
            showErrorDialog(action, errorMessage);
            Toast.makeText(MainActivity.this, "Error to realize action: " + action, Toast.LENGTH_SHORT).show();
        }
    };

    private GmIntegration gmIntegration;

    private EditText editStopKey;
    private Button buttonStartRoute, buttonCompleteRoute, buttonOpenMap, buttonArriveStop, buttonDepartStop, buttonLoadRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViewElements();
        setOnClickListeners();
        gmIntegration = new GmIntegration(this, gmIntegrationListener);
    }

    private void getViewElements(){
        editStopKey = (EditText) findViewById(R.id.edit_stop_key);
        buttonStartRoute = (Button) findViewById(R.id.button_start_route);
        buttonCompleteRoute = (Button) findViewById(R.id.button_complete_route);
        buttonOpenMap = (Button) findViewById(R.id.button_open_map);
        buttonArriveStop = (Button) findViewById(R.id.button_arrive_stop);
        buttonDepartStop = (Button) findViewById(R.id.button_depart_stop);
        buttonLoadRoute = (Button) findViewById(R.id.button_load_route);
    }

    private void setOnClickListeners() {
        buttonStartRoute.setOnClickListener(onClickStartRoute);
        buttonCompleteRoute.setOnClickListener(onClickCompleteRoute);
        buttonOpenMap.setOnClickListener(onClickOpenMap);
        buttonArriveStop.setOnClickListener(onClickArriveStop);
        buttonDepartStop.setOnClickListener(onClickDepartStop);
        buttonLoadRoute.setOnClickListener(onClickLoadRoute);
    }

    private final View.OnClickListener onClickLoadRoute = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            gmIntegration.getLoadedRoute();
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
            gmIntegration.openMap(editStopKey.getText().toString());
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

    private void dismissProgressDialog(){
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog(String message) {
        progressDialog = ProgressDialog.show(this, "Greenmile", message, true);
    }

    private void showErrorDialog(String action, String errorMessage){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(errorMessage);
        alertDialogBuilder.setTitle(action);
        alertDialogBuilder.setPositiveButton("Ok", null);
        alertDialogBuilder.create().show();
    }

}
