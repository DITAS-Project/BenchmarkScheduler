package de.tub.benchmarkscheduler.service;

import de.tub.benchmarkscheduler.repo.SampleDataRepository;
import de.tub.benchmarkscheduler.model.SampleData;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleDataServiceImpl implements SampleDataService {

    @Autowired
    SampleDataRepository repo;

    @Autowired
    MongoTemplate template;


    @Override
    public void save(SampleData data) {

        //get matching SampleData by id from mongo
        Query query= new Query(Criteria.where("id").is(data.getId()));

        // build the update query by parsing the SampleData object to Document
        Document dbDoc= new Document();
        template.getConverter().write(data, dbDoc);
        Update update= fromDocumentExcludeNullFields(dbDoc);
        //execute upsert mongo query
        template.upsert(query,update,SampleData.class);
    }

    @Override
    public SampleData findById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<SampleData> findByMethod(String method) {
        return repo.findAllByMethod(method);
    }

    @Override
    public List<SampleData> findAll() {
        return repo.findAll();
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public List<SampleData> findByMethodAndBpId(String method, String bpId) {
        return repo.findAllByMethodAndBpId(method,bpId);
    }

    /**
     * Creates an Update Querey without null fields
     * @param document
     * @return update
     */
    private static Update fromDocumentExcludeNullFields(Document document) {
        Update update = new Update();
        for (String key : document.keySet()) {
            Object value = document.get(key);
            if(value!=null){
                update.set(key, value);
            }
        }
        return update;
    }
}
