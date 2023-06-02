/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.messaging.Message;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.Contrat;

import com.mycompany.myapp.utils.Statics;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author msi
 */
public class ServiceContrat {

    public ArrayList<Contrat> Contrats;
    
    public static ServiceContrat instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ServiceContrat() {
         req = new ConnectionRequest();
    }

    public static ServiceContrat getInstance() {
        if (instance == null) {
            instance = new ServiceContrat();
        }
        return instance;
    }
    


    public ArrayList<Contrat> parseContrats(String jsonText){
                try {

                    System.out.println(jsonText);
            Contrats=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Contrat a = new Contrat();
                                
               a.setId((int)Float.parseFloat(obj.get("id").toString()));
                a.setDateexpiration(obj.get("dateexpiration").toString());
                a.setDatesignature(obj.get("datesignature").toString());
                a.setMontant(obj.get("montant").toString());
                a.setImagecontrat(obj.get("imagecontrat").toString());               
                Contrats.add(a);


            }
        } catch (IOException ex) {
            
        }
        return Contrats;
    }
    public ArrayList<Contrat> getAllContrats(){
        String url = Statics.BASE_URL+"/all";
        req.setUrl(url);
        req.setPost(false); 
        req.addResponseListener(new com.codename1.ui.events.ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Contrats = parseContrats(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        com.codename1.io.NetworkManager.getInstance().addToQueueAndWait(req);
        return Contrats;
    }

   

    public boolean addContrat(Contrat u) {
       String url = Statics.BASE_URL+"/addcontratJSON/new?datesignature" +u.getDatesignature() +"dateexpiration"+u.getDateexpiration()+"montant"+u.getMontant()+"image"+u.getImagecontrat();
       
    
        req.setUrl(url);
        req.setPost(false);
        //req.addArgument("title", f.getTitle());

      
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

        public boolean editContrat(Contrat u) {
           
      String url = "http://127.0.0.1:8000/updatecontratJSON/"+u.getId()  + "?datesignature" +u.getDatesignature() +"dateexpiration"+u.getDateexpiration()+"montant"+u.getMontant()+"image"+u.getImagecontrat();
               req.setUrl(url);
               req.setPost(false); 
               System.out.println(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public boolean deleteContrat(int id) {
        String url = "http://127.0.0.1:8000/deletecontratJSON/" + id;
               req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

}

