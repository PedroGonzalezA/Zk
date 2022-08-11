package mx.com.controller;
//zk
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Datebox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;

public class Composer extends GenericForwardComposer<Component>{
	private Textbox nombres;
	private Textbox direccion;
	private Textbox edad;
	private Datebox fecha;
	private LocalDate todaysDate = LocalDate.now();
	private int anoActual = todaysDate.getYear();
    String filePath1 = "C:\\Users\\Pister\\Desktop\\Lista_Empleados_DDMMYYYY_HHMM.txt";
    File file1 = new File("C:\\Users\\Pister\\Desktop\\Lista_Empleados_DDMMYYYY_HHMM.txt");
    String filePath2 = "C:\\Users\\Pister\\Desktop\\Lista_Empleados_DDMMYYYY_HHMM_COMP.txt";
    File file2 = new File("C:\\Users\\Pister\\Desktop\\Lista_Empleados_DDMMYYYY_HHMM_COMP.txt");
	private static final long serialVersionUID = -2159669591652109433L;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
	}
	//funcion click
	public void onClick$btnAgregar() throws ParseException{
		String nombreC = nombres.getValue();
		String direcciones = direccion.getValue();
		String edades = edad.getValue();
		if(isNumeric(edades)==true) {
			if(nombreC.isEmpty()) {
				alert("El nombre esta vacio");
			}else {
				if(direcciones.isEmpty()) {
					alert("La direccion esta vacia");
				}else {
					if(edades.isEmpty()) {
						alert("La edad esta vacia");
					}else {
						try {
							int edadF = Integer.parseInt(edades);
							int anoNaci = CalculoA(edadF);
							String fechaI = fecha.getValue().toString();//variable fecha de ingreso
					        java.util.Date fechaIc = fecha.getValue(); //variable fecha de ingreso 
					        LocalDate localDate1 = fechaIc.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();  //convertir a localdate 
							int anoT = CalculoAnot(fechaI);//variable años de trabajo
							int montoF = CalculoMontoA(anoT);//variable monto de antiguedad
							 //condicion mayor a 22 años
							if(edadF>=22) {
								if(localDate1.isAfter(todaysDate)) {
									alert("La fecha seleccionada es superior ala fecha actual");
								}else {
									if(anoNaci>=1970 && anoNaci<=1990 ) {
										//metodo adentro del rango
										textoRango(nombreC,direcciones,edadF,anoNaci,anoT,montoF);
									}else {
										if(anoNaci<1970) {
											alert("Eres demasiado grande para este empleo");
										}else {
											//metodo fuera del rango
											int anT= edadF-anoT;
											if(anT>=22) {
												textoFueraR(nombreC,direcciones,edadF,anoNaci,anoT);
											}else {
												alert("Datos erroneos años y fecha de ingreso no son validos");
											}
										}
									}
								}
								
							}else {
								alert("La edad es menor a 22");
							}
						}
						catch(NullPointerException e) {
							alert("La fecha esta vacia");
						}	
					}
				}
			}
		}else {
			alert("La edad solo debe de ser numeros");
		}
			
		
	}
	//calcular monto antiguedad 
	public int CalculoA(int edadf) {
		int anoNacimiento = anoActual - edadf;
		return anoNacimiento;
	}	
	//calcularAños Antiguedad
	public int CalculoAnot(String fecha) {
		String numbers =fecha.substring(Math.max(0, fecha.length() - 4));
		int antiguedad  = Integer.parseInt(numbers);
		int antiguedadF= anoActual - antiguedad;
		return antiguedadF;
	}
	//calcular monto antiguedad 
	public int CalculoMontoA(int anoT) {
		int antiguedadF= anoT * 125;
		return antiguedadF;
	}
	//es numero
	public static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}
	//Txt15AÑOS
	public void textoRango(String nombres2,String direccion2, int edades,int anoNacimiento,int anoTrabaj,int montoF) {
		try
        {	
            FileWriter fw = new FileWriter(filePath1, true);
            BufferedReader br = new BufferedReader(new FileReader(file1)); 
            if(anoTrabaj>=15) {
            	String  lineToAppend = + br.lines().count()+ " | "+ nombres2+" | "+direccion2+" | "+edades+" | "+ anoNacimiento +" | "+ montoF+"\n" ;    
  	    		fw.write(lineToAppend);
  	            fw.close();
  	            alert("Agregado con exito");
            }else {
            	String  lineToAppend =+ br.lines().count()+" | "+ nombres2+" | "+direccion2+" | "+edades+" | "+ anoNacimiento +" | "+"\n" ;    
     			fw.write(lineToAppend);
                fw.close();
  	            alert("Agregado con exito");
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
	}
	//TXTMENOS DE 15
	public void textoFueraR(String nombres2,String direccion2, int edades,int anoNacimiento,int anoTrabaj) {
		try
        {
	        FileWriter fw = new FileWriter(filePath2, true);    
			BufferedReader br = new BufferedReader(new FileReader(file2)); 
	        String  lineToAppend =+ br.lines().count()+" | "+ nombres2+" | "+direccion2+" | "+edades+" | "+ anoNacimiento +" | "+"\n" ;    
	        fw.write(lineToAppend);
	        fw.close();
            alert("Agregado con exito");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
	}
}