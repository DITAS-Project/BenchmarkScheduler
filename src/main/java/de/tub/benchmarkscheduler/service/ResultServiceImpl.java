/*
 *
 *  * Copyright 2018 Information Systems Engineering, TU Berlin, Germany
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *                       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *  * This is being developed for the DITAS Project: https://www.ditas-project.eu/
 *
 */

package de.tub.benchmarkscheduler.service;

import de.tub.benchmarkscheduler.model.RawResult;
import de.tub.benchmarkscheduler.repo.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    ResultRepository repo;


    @Override
    public void save(RawResult result) {
        result.setDate(new Date());
        repo.save(result);
    }

    @Override
    public RawResult getResultById(String id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<RawResult> getAll() {
        return repo.findAll();
    }

    @Override
    public List<RawResult> getBetweenDates(Date start, Date end) {
        return repo.findAllByDateBetween(start,end);
    }


}
