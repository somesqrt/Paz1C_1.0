package com.example.demo2.daos;



import com.example.demo2.EntityNotFoundException;
import com.example.demo2.classes.Position;
import com.example.demo2.classes.Product;
import com.example.demo2.help.ProduktOnPositionHelp;

import java.util.List;
import java.util.Map;

public interface PositionDao {
    List<Position> getAll();
    Position save(Position position) throws EntityNotFoundException;
    Position delete(Long id ) throws EntityNotFoundException;
    Position getById(Long id) throws EntityNotFoundException;
    Map<Position,Double> fullnessOfPositionV();
    Double getСapacityOfPositionV(Long positionId);

    Position getByName(String name);

    void setProductOnPosition(Product product, Position position, int count);

    List<ProduktOnPositionHelp> getAllInfoAboutOrderOnPosition();

    List<String> getALlNames() throws EntityNotFoundException;

    ProduktOnPositionHelp deleteInfo(ProduktOnPositionHelp produktOnPositionHelp);
    Long getIdPositionByProduct(Product product,int pocet);
}
