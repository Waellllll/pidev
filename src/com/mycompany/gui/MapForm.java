/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gui;

import Produit.gui.AllContrats;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;
import com.codename1.ui.Container;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import java.io.IOException;
import com.codename1.components.ToastBar;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.maps.Coord;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import java.io.IOException;
import java.util.List;
import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
/**
 *
 * @author wissa
 */
public class MapForm {
    Form f = new Form();
  MapContainer cnt = null;
public MapForm() {
        
    try{
        cnt = new MapContainer("AIzaSyCy-fMWerzvXcPCV0FDI07hW2DAzs_mnpY");
    }catch(Exception ex) {
        ex.printStackTrace();
    }

        Button btnMoveCamera = new Button("Mon Pays");
        btnMoveCamera.addActionListener(e->{
            cnt.setCameraPosition(new Coord(36.8189700, 10.1657900));
        });
        
        Style s = new Style();
        s.setFgColor(0xff0000);
        s.setBgTransparency(0);
        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, Display.getInstance().convertToPixels(3));

        
    


        cnt.addTapListener(e->{
    
            
                cnt.clearMapLayers();
                cnt.addMarker(
                        EncodedImage.createFromImage(markerImg, false),
                        cnt.getCoordAtPosition(e.getX(), e.getY()),
                        ""+cnt.getCameraPosition().toString(),
                        "",
                        e3->{
                                ToastBar.showMessage("You clicked "+cnt.getName(), FontImage.MATERIAL_PLACE);
                        }
                );
             ConnectionRequest r = new ConnectionRequest();
            r.setPost(false);
            r.setUrl("http://maps.google.com/maps/api/geocode/json?latlng="+cnt.getCameraPosition().getLatitude()+","+cnt.getCameraPosition().getLongitude()+"&oe=utf8&sensor=false");
                     NetworkManager.getInstance().addToQueueAndWait(r);

            JSONParser jsonp = new JSONParser();
         try {
               java.util.Map<String, Object> tasks = jsonp.parseJSON(new CharArrayReader(new String(r.getResponseData()).toCharArray()));
                              System.out.println("roooooot:" +tasks.get("results"));
                              List<java.util.Map<String, Object>> list1 = (List<java.util.Map<String, Object>>)tasks.get("results");
//                              java.util.Map<String, Object> list = (java.util.Map<String, Object>) list1.get(0);

  //                             List<java.util.Map<String, Object>> listf = (List<java.util.Map<String, Object>>) list.get("address_components");
//String ch="";
  //                       for (java.util.Map<String, Object> obj : listf) {
    //             ch=ch+obj.get("long_name").toString();
      //                   }
                       //
                         // b.setAdresse(ch);

                        

           } catch (IOException ex) {
           }

            
            
        });
        Container root = new Container();
         f.setLayout(new BorderLayout());
         f.addComponent(BorderLayout.CENTER, cnt);
         f.addComponent(BorderLayout.SOUTH, btnMoveCamera);
f.show();
 //f.getToolbar().addCommandToRightBar("back", null, (ev)->{ new AjoutReclamationForm(f).show()});

    
    }

    Command show() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    protected void addSideMenu(Resources res) {
        Toolbar tb = getToolbar();
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        
        tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                sl,
                FlowLayout.encloseCenterBottom(
                        new Label(res.getImage("azizpic.png"), "PictureWhiteBackgrond"))
        ));
        
   
        tb.addMaterialCommandToSideMenu("Gestion Contrat", FontImage.MATERIAL_PHOTO, e -> new AllContrats(res).show());
        tb.addMaterialCommandToSideMenu("Map", FontImage.MATERIAL_MAP, e -> new MapForm().show());
      
    }

    private Toolbar getToolbar() {
   
        return null;
   
    }
}
