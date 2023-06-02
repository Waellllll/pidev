/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package Produit.gui;

import com.codename1.capture.Capture;
import com.codename1.components.ScaleImageLabel;
import com.codename1.datatransfer.DropTarget;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.ui.CheckBox;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.RoundBorder;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.util.Base64;
import com.mycompany.gui.BaseForm;
import com.mycompany.myapp.entities.Contrat;
import com.mycompany.myapp.entities.services.ServiceContrat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ModifierContrat extends BaseForm {
   String Imagecode;
   String filePath="";

    public ModifierContrat(Resources res,Form previous,Contrat fi) {
        super("Modifier Contrat", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Contrat");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
        
        
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);
        
                add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                    GridLayout.encloseIn(2, 
                            facebook, twitter
                    )
                )
        ));

                        
        TextComponent dateexpiration= new TextComponent().label("dateexpiration");
        dateexpiration.text(fi.getDateexpiration());
        add(dateexpiration);
                      
        TextComponent datesignature= new TextComponent().label("datesignature");
        datesignature.text(fi.getDatesignature());
        add(datesignature);
        TextComponent montant= new TextComponent().label("montant");
        montant.text(fi.getMontant());
        add(montant);
        
                        
   
        //IMAGE
        Font materialFont = FontImage.getMaterialDesignFont();
        FontImage fntImage = FontImage.createFixed("\uE871", materialFont, 0xffffff, 100, 100);
        Button b2 = new Button(fntImage);
        Style fabStyle = b2.getAllStyles();
        fabStyle.setBorder(RoundBorder.create().color(0xf05f5f).shadowOpacity(100));
        fabStyle.setFgColor(0xf15f5f);
        fabStyle.setBgTransparency(50);
        fabStyle.setBgColor(0xf05f5f);
           
        Label lbimg = new Label();

         if (DropTarget.isSupported()) {
        DropTarget dnd = DropTarget.create((evt)->{
        String srcFile = (String)evt.getSource();
        System.out.println("Src file is "+srcFile);
       
        String  maChaine = srcFile;
        filePath= maChaine.substring(19,srcFile.length());
               
        System.out.println(filePath);
        System.out.println("Location: "+evt.getX()+", "+evt.getY());
        if (srcFile != null) {
            try {
                Image imgg = Image.createImage(FileSystemStorage.getInstance().openInputStream(srcFile));
                imgg.scale(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayWidth());
                    lbimg.setIcon(imgg);
                // c3.removeComponent(imgv);
                revalidate();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        } 
    }, Display.GALLERY_IMAGE);
}
        
        
         add(b2);
         add(lbimg);
         
        Button Edit = new Button("Edit");
        Edit.addActionListener((evt) -> {
                              
            if (dateexpiration.getText().equals("")||(datesignature.getText().equals("")))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    ImageIO imgIO = ImageIO.getImageIO();
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] ba = out.toByteArray();
                    Imagecode = Base64.encode(ba);
                    System.out.println(filePath);

                    ServiceContrat sp = new ServiceContrat();
                    fi.setDatesignature(datesignature.getText());
            fi.setDateexpiration(dateexpiration.getText());
            fi.setMontant(montant.getText());
            if(!filePath.equals(""))
            {
              fi.setImagecontrat(filePath);
            }
            else
            {
              fi.setImagecontrat("");
            }
            
          
                
                    sp.editContrat(fi);
                    Dialog.show("Success","Contrat modifier avec success",new Command("OK"));
                    new AllContrats(res).show();
                }
        });
        addStringValue("", FlowLayout.encloseRightMiddle(Edit));
        
    }
    
    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
