package com.quicktutorialz.nio.endpoints.v1;

import com.mawashi.nio.annotations.Api;
import com.mawashi.nio.utils.Action;
import com.mawashi.nio.utils.Endpoints;
import com.quicktutorialz.nio.daos.v1.ToDoDao;
import com.quicktutorialz.nio.daos.v1.ToDoDaoImpl;
import com.quicktutorialz.nio.entities.ResponseDto;
import com.quicktutorialz.nio.entities.ToDo;
import com.quicktutorialz.nio.entities.ToDoDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

public class ToDoEndpoints extends Endpoints {

    ToDoDao toDoDao = ToDoDaoImpl.getInstance();
    // @Api annotation is just a description of the action method, it's not needed.
    @Api(path = "/api/v1/create", method = "POST", consumes = "application/json", produces = "application/json"
            , description = "")
    Action createToDo = (HttpServletRequest req, HttpServletResponse resp) -> {

        ToDoDto input = (ToDoDto) getDataFromJsonBodyRequest(req, ToDoDto.class);
        ToDo outPut = toDoDao.create(input);
        toJsonResponse(req, resp, new ResponseDto(200, outPut));
    };

    @Api(path = "/api/v1/read/{id}", method = "GET", produces = "application/json", description = "")
    Action readToDo = (HttpServletRequest req, HttpServletResponse resp) -> {
        String id = getPathVariables(req).get("id");
        Optional<ToDo> outPut = toDoDao.read(id);

        toJsonResponse(req, resp, new ResponseDto(200, outPut.isPresent() ?
                outPut.get() : "todo not found"));
    };

    @Api(path = "/api/v1/read", method = "GET", produces = "application/json", description = "")
    Action readAllToDos = (HttpServletRequest req, HttpServletResponse resp) -> {
        List<ToDo> outPutAll = toDoDao.readAll();

        toJsonResponse(req, resp, new ResponseDto(200, outPutAll));
    };

    @Api(path = "/api/v1/update", method = "POST", consumes = "application/json", produces = "application/json",
            description = "")
    Action updateToDo = (HttpServletRequest req, HttpServletResponse resp) -> {
         ToDo input = (ToDo) getDataFromJsonBodyRequest(req, ToDo.class);

         Optional<ToDo> outPut = toDoDao.update(input);
         toJsonResponse(req, resp, new ResponseDto(200, outPut.isPresent() ? outPut.get() : "to not updated"));
    };

    @Api(path = "/api/v1/delete/{id}", method = "GET", produces = "application/json", description = "")
    Action deleteToDo = (HttpServletRequest req, HttpServletResponse resp) -> {
        String id = getPathVariables(req).get("id");
        Optional<ToDo> outPut = toDoDao.read(id);

        toJsonResponse(req, resp, new ResponseDto(200, outPut.isPresent() ?
                toDoDao.delete(id) : "todo not deleted"));
    };

    public ToDoEndpoints() {
        setEndpoint("/api/v1/create", createToDo);
        setEndpoint("/api/v1/read/{id}", readToDo);
        setEndpoint("/api/v1/read", readAllToDos);
        setEndpoint("/api/v1/update", updateToDo);
        setEndpoint("/api/v1/delete", deleteToDo);
    }
}
