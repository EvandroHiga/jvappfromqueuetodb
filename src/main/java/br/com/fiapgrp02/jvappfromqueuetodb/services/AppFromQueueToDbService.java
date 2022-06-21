package br.com.fiapgrp02.jvappfromqueuetodb.services;

import br.com.fiapgrp02.jvappfromqueuetodb.model.Objeto;
import br.com.fiapgrp02.jvappfromqueuetodb.utils.AzConnUtils;
import com.azure.data.tables.TableClient;
import com.azure.data.tables.TableClientBuilder;
import com.azure.data.tables.models.ListEntitiesOptions;
import com.azure.data.tables.models.TableEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AppFromQueueToDbService {

    @Value("${table.field.vlr01}")
    private String VALOR_01;

    @Value("${table.field.vlr02}")
    private String VALOR_02;

    @Value("${env.prd}")
    private String ENV_PRD;

    @Value("${env.hml}")
    private String ENV_HML;

    public ResponseEntity getAllEntities(String env){
        if(ENV_PRD.equalsIgnoreCase(env) ||
                ENV_HML.equalsIgnoreCase(env)){
            List<String> propertiesToSelect = new ArrayList<>();
            propertiesToSelect.add(VALOR_01);
            propertiesToSelect.add(VALOR_02);

            ListEntitiesOptions options = new ListEntitiesOptions().setSelect(propertiesToSelect);

            List list = new ArrayList<Objeto>();

            for(TableEntity tableEntity : getTableClient(env).listEntities(options,null,null)){
                Map<String, Object> properties = tableEntity.getProperties();
                Objeto objeto = new Objeto();
                objeto.setValor01(properties.get(VALOR_01).toString());
                objeto.setValor02(properties.get(VALOR_02).toString());
                objeto.setEnv(env);
                list.add(objeto);
            }
            return ResponseEntity.status(HttpStatus.OK).body(list);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(env + " : " + "Nao ha ambiente com este nome.");
        }

    }

    private TableClient getTableClient(String env){
        String tableName;
        if(ENV_PRD.equalsIgnoreCase(env)){
            tableName = System.getenv("TABLE_NAME_PRD");
        } else {
            tableName = System.getenv("TABLE_NAME_HML");
        }

        return new TableClientBuilder()
                .connectionString(AzConnUtils.getAzConnStr())
                .tableName(tableName)
                .buildClient();
    }

}
