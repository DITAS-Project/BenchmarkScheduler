package de.tub.benchmarkscheduler.service.workload;

import de.tub.benchmarkscheduler.data.WorkloadRepository;
import de.tub.benchmarkscheduler.model.Workload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class WorkloadDataServiceImpl implements WorkloadDataService {
    @Autowired
    WorkloadRepository repo;


    @Override
    public void save(Workload workload) {
        repo.save(workload);
    }

    @Override
    public void saveAll(Collection<Workload> workloads) {
        repo.saveAll(workloads);

    }

    @Override
    public List<Workload> getAll() {
        return repo.findAll();
    }

    @Override
    public void deleteAll() {
        repo.deleteAll();
    }

    @Override
    public Workload findByID(String id) {
        return repo.findById(id).get();
    }
}
