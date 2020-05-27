/**
 * 
 */
package com.MyNas.ServerCommandHandler.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Amady
 *
 */
@RestController
@RequestMapping(value = "/cmd", method = RequestMethod.POST)
public class ExecCMD {
	ProcessBuilder processBuilder = new ProcessBuilder();
	
	@PostMapping(value = "/initUserDir")
	public String initUserDirectory(@RequestParam String userName) {
		
		String result ="";
		
		String firstCmdToRun = "mkdir /home/ftpuser/ftp/"+userName;
		String secondCmdToRun = "sudo chown ftpuser:ftpuser /home/ftpuser/ftp/"+userName;
		
		try {
			executeCommande(firstCmdToRun);
			executeCommande(secondCmdToRun);
			result = "Répertoire créé";
		}catch(Exception e) {
			e.printStackTrace();
			result ="Erreur: Répertoire non créé";
		}

		return result;
	}
	
	public void executeCommande(String commande) {
		processBuilder.command("bash", "-c", commande);
		try {
			Process p = processBuilder.start();
			System.out.println("command executed: "+p.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
