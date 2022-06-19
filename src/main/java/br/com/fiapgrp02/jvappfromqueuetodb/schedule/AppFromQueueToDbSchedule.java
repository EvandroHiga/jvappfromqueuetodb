package br.com.fiapgrp02.jvappfromqueuetodb.schedule;

import br.com.fiapgrp02.jvappfromqueuetodb.model.Objeto;
import br.com.fiapgrp02.jvappfromqueuetodb.utils.AzConnUtils;
import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableClientBuilder;
import com.azure.data.tables.TableServiceClient;
import com.azure.data.tables.TableServiceClientBuilder;
import com.azure.data.tables.models.TableEntity;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.models.QueueMessageItem;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
@EnableScheduling
public class AppFromQueueToDbSchedule {

    @Value("${env.prd}")
    private String ENV_PRD;

    @Value("${env.hml}")
    private String ENV_HML;

    @Value("${table.field.vlr01}")
    private String TABLE_FIELD_VLR_01;

    @Value("${table.field.vlr02}")
    private String TABLE_FIELD_VLR_02;

    @Scheduled(fixedDelayString = "${get.msg.fixed.delay}")
    public void getMsgFromQueueInsertIntoDb(){
        this.getSchedulePrepared();

        Objeto objeto = getMsgFromQueue();

        if(Objects.nonNull(objeto)){
            String rowKey = UUID.randomUUID().toString().toUpperCase();
            String partitionKey = objeto.getEnv();

            Map<String, Object> tableValues= new HashMap<>();

            tableValues.put(TABLE_FIELD_VLR_01, objeto.getValor01());
            tableValues.put(TABLE_FIELD_VLR_02, objeto.getValor02());

            TableEntity tableEntityToCreate = new TableEntity(partitionKey, rowKey).setProperties(tableValues);

            insertIntoDb(partitionKey).createEntity(tableEntityToCreate);
        }
    }

    private void getSchedulePrepared(){
        TableServiceClient tableServiceClient = new TableServiceClientBuilder()
                .connectionString(AzConnUtils.getAzConnStr())
                .buildClient();

        tableServiceClient.createTableIfNotExists(System.getenv("TABLE_NAME_HML"));
        tableServiceClient.createTableIfNotExists(System.getenv("TABLE_NAME_PRD"));
    }

    private Objeto getMsgFromQueue(){
        QueueClient queueClient = new QueueClientBuilder()
                .connectionString(AzConnUtils.getAzConnStr())
                .queueName(System.getenv("QUEUE_NAME"))
                .buildClient();

        QueueMessageItem queueMessageItem = queueClient.receiveMessage();

        if(Objects.nonNull(queueMessageItem)){
            String bodyJson = queueMessageItem.getMessageText();
            queueClient.deleteMessage(queueMessageItem.getMessageId(), queueMessageItem.getPopReceipt());

            Gson gson = new Gson();
            return gson.fromJson(bodyJson, Objeto.class);
        } else {
            return null;
        }

    }

    private TableClient insertIntoDb(String partitionKey){
        if(ENV_PRD.equalsIgnoreCase(partitionKey)){
            return new TableClientBuilder()
                    .connectionString(AzConnUtils.getAzConnStr())
                    .tableName(System.getenv("TABLE_NAME_PRD"))
                    .buildClient();
        } else if(ENV_HML.equalsIgnoreCase(partitionKey)){
            return new TableClientBuilder()
                    .connectionString(AzConnUtils.getAzConnStr())
                    .tableName(System.getenv("TABLE_NAME_HML"))
                    .buildClient();
        } else {
            return null;
        }
    }

}
