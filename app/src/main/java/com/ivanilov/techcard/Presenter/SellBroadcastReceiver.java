package com.ivanilov.techcard.Presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.room.Room;

import com.ivanilov.techcard.Model.HistoryTable;
import com.ivanilov.techcard.Model.IngerdientTable;
import com.ivanilov.techcard.Model.TechCartTable;
import com.ivanilov.techcard.Presenter.Entity.History;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import ru.evotor.framework.core.action.event.receipt.receipt_edited.ReceiptClosedEvent;
import ru.evotor.framework.receipt.Payment;
import ru.evotor.framework.receipt.Position;
import ru.evotor.framework.receipt.Receipt;
import ru.evotor.framework.receipt.ReceiptApi;


public class SellBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        String uuid = ReceiptClosedEvent.create(bundle).getReceiptUuid();

        List<Position> position = ReceiptApi.getReceipt(context, uuid).getPositions();

        IngerdientTable ingerdientTable = Room.databaseBuilder(context,
                IngerdientTable.class, "ingerdient_database").allowMainThreadQueries().build();

        TechCartTable techCartTable = Room.databaseBuilder(context,
                TechCartTable.class, "techcart_database").fallbackToDestructiveMigration().allowMainThreadQueries().build();


        ComingWriteOff comingWriteOff = new ComingWriteOff();


        for (Position i:position){
            try {
                comingWriteOff.writeOffTechCartReceiver(context, i.getName(),i.getQuantity().intValue(), ingerdientTable, techCartTable);
            }
            catch (NullPointerException e){
                continue;
            }
        }

    }
}
