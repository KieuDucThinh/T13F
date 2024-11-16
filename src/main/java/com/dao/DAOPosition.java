package com.dao;

import com.entity.Position;

import java.rmi.Remote;

public interface DAOPosition extends Remote{
    public void createPosition(Position p);
    public Position getPosition(short pos_id);
}
