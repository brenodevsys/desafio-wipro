package com.breno.despesa.configuration;

import com.breno.despesa.entitie.Despesa;
import com.breno.despesa.repository.DespesaRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
public class DespesaConfig {

    @Value("classpath:teste.json")
    private Resource jsonFile;

    @Autowired
    private DespesaRepository despesaRepository;

    @PostConstruct
    public void loadData() throws IOException {
        try (InputStreamReader reader = new InputStreamReader(jsonFile.getInputStream())) {
            JSONArray jsonArray = new JSONArray(new JSONTokener(reader));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray record = jsonArray.getJSONArray(i);
                Despesa despesa = new Despesa();
                despesa.setAnoMovimentacao(getIntValue(record, 1));
                despesa.setMesMovimentacao(getIntValue(record, 2));
                despesa.setOrgaoNome(record.getString(4));
                despesa.setValorPago(record.getString(39));
                despesa.setCategoria(record.getString(8));
                despesaRepository.save(despesa);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getIntValue(JSONArray record, int index) {
        try {
            return record.getInt(index);
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }

}