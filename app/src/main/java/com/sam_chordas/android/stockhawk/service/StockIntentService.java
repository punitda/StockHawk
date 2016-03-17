package com.sam_chordas.android.stockhawk.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.gcm.TaskParams;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

/**
 * Created by sam_chordas on 10/1/15.
 */
public class StockIntentService extends IntentService {
  private static final String TAG = "StockIntentService";

  public StockIntentService(){
    super(StockIntentService.class.getName());
  }

  public StockIntentService(String name) {
    super(name);
  }

  @Override protected void onHandleIntent(Intent intent) {
    Log.d(StockIntentService.class.getSimpleName(), "Stock Intent Service");
    StockTaskService stockTaskService = new StockTaskService(this);
    Bundle args = new Bundle();
    if (intent.getStringExtra("tag").equals("add")){
      args.putString("symbol", intent.getStringExtra("symbol"));
    }
    // We can call OnRunTask from the intent service to force it to run immediately instead of
    // scheduling a task.
    int res  = stockTaskService.onRunTask(new TaskParams(intent.getStringExtra("tag"), args));
    Log.d(TAG, "IntentService : called  " + String.valueOf(res));
      Intent i = new Intent();
      i.setAction(MyStocksActivity.ProgressBarReceiver.RECEIVER_NAME);
      i.addCategory(Intent.CATEGORY_DEFAULT);
      i.putExtra("RESULT",res);
      sendBroadcast(i);

  }
}
