package com.quicktutorialz.nio.endpoints.v2;

import com.mawashi.nio.annotations.Api;
import com.mawashi.nio.utils.Action;
import com.mawashi.nio.utils.Endpoints;
import com.quicktutorialz.nio.daos.v2.ToDoDao;
import com.quicktutorialz.nio.daos.v2.ToDoDaoImpl;
import com.quicktutorialz.nio.entities.ResponseDto;
import com.quicktutorialz.nio.entities.ToDo;
import com.quicktutorialz.nio.entities.ToDoDto;
import io.reactivex.Observable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ToDoEndpoints extends Endpoints {

    ToDoDao toDoDao = ToDoDaoImpl.getInstance();

    // @Api annotation is just a description of the action method, it's not needed.
    @Api(path = "/api/v1/create", method = "POST", consumes = "application/json", produces = "application/json"
            , description = "")
    Action createToDo = (HttpServletRequest req, HttpServletResponse resp) -> {
        Observable.just(req)
                .map(request -> (ToDoDto) getDataFromJsonBodyRequest(request, ToDoDto.class))
                .flatMap(input -> toDoDao.create(input))
                .subscribe(outPut -> toJsonResponse(req, resp, new ResponseDto(200, outPut)));
//        ToDoDto input = (ToDoDto) getDataFromJsonBodyRequest(req, ToDoDto.class);
//        ToDo outPut = toDoDao.create(input);
//        toJsonResponse(req, resp, new ResponseDto(200, outPut));
    };

    @Api(path = "/api/v1/read/{id}", method = "GET", produces = "application/json", description = "")
    Action readToDo = (HttpServletRequest req, HttpServletResponse resp) -> {
        Observable.just(req)
                .map(request -> getPathVariables(req).get("id"))
                .flatMap(id -> toDoDao.read(id))
                .subscribe(outPut -> toJsonResponse(req, resp, new ResponseDto(200, outPut.isPresent() ?
                        outPut.get() : "todo not found")));
//        String id = getPathVariables(req).get("id");
//        Optional<ToDo> outPut = toDoDao.read(id);
//
//        toJsonResponse(req, resp, new ResponseDto(200, outPut.isPresent() ?
//                outPut.get() : "todo not found"));
    };

    @Api(path = "/api/v1/read", method = "GET", produces = "application/json", description = "")
    Action readAllToDos = (HttpServletRequest req, HttpServletResponse resp) -> {
        Observable.just(req)
                .flatMap(request -> toDoDao.readAll())
                .subscribe(outPutAll -> toJsonResponse(req, resp, new ResponseDto(200, outPutAll)),
                        error -> toJsonResponse(req, resp, new ResponseDto(200, error)));
//        List<ToDo> outPutAll = toDoDao.readAll();
//
//        toJsonResponse(req, resp, new ResponseDto(200, outPutAll));
    };

    @Api(path = "/api/v1/update", method = "POST", consumes = "application/json", produces = "application/json",
            description = "")
    Action updateToDo = (HttpServletRequest req, HttpServletResponse resp) -> {
        Observable.just(req)
                .map(request -> (ToDo) getDataFromJsonBodyRequest(request, ToDo.class))
                .flatMap(input -> toDoDao.update(input))
                .subscribe(outPut -> toJsonResponse(req, resp,
                        new ResponseDto(200, outPut.isPresent() ? outPut.get() : "to not updated")));

//        ToDo input = (ToDo) getDataFromJsonBodyRequest(req, ToDo.class);
//
//        Optional<ToDo> outPut = toDoDao.update(input);
//        toJsonResponse(req, resp, new ResponseDto(200, outPut.isPresent() ? outPut.get() : "to not updated"));
    };

    @Api(path = "/api/v1/delete/{id}", method = "GET", produces = "application/json", description = "")
    Action deleteToDo = (HttpServletRequest req, HttpServletResponse resp) -> {
        String objId = getPathVariables(req).get("id");
        Observable.just(req)
                .map(request -> objId)
                .flatMap(id -> toDoDao.delete(id))
                .subscribe(outPut -> toJsonResponse(req, resp, new ResponseDto(200, outPut ?
                        "toDoDao deleted " + objId : "todo not deleted")));

//        String id = getPathVariables(req).get("id");
//
//        toJsonResponse(req, resp, new ResponseDto(200, toDoDao.delete(id) ?
//                "toDoDao deleted " + id : "todo not deleted"));
    };


    public ToDoEndpoints() {
        setEndpoint("/api/v2/create", createToDo);
        setEndpoint("/api/v2/read/{id}", readToDo);
        setEndpoint("/api/v2/read", readAllToDos);
        setEndpoint("/api/v2/update", updateToDo);
        setEndpoint("/api/v2/delete/{id}", deleteToDo);
    }
}
