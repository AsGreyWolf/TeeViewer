package com.asgreywolf.teeviewer;
import com.asgreywolf.teeviewer.ServerTransfer;
interface UpdateService 
{
        ServerTransfer GetServer(int id);
        ServerTransfer[] GetServers();
        void AddServer(String ip,int port);
        void EditServer(int id,String ip,int port);
        void RemoveServer(int id);
        void Check(int id);
        void CheckAll();
        void CheckSettings();
}