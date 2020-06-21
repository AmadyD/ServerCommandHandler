/**
 * 
 */
package com.MyNas.ServerCommandHandler.controller;

import java.io.IOException;
import java.util.List;

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
	
	
	/*
	 * Initialise un répertoire au niveau du serveur ftp pour le user
	 */
	@PostMapping(value = "/initUserDir")
	public String initUserDirectory(@RequestParam String userName) {
		
		String result ="KO";
		
		String firstCmdToRun = "mkdir /home/ftpuser/ftp/"+userName;
		String secondCmdToRun = "sudo chown ftpuser:ftpuser /home/ftpuser/ftp/"+userName;
		
		try {
			executeCommande(firstCmdToRun);
			executeCommande(secondCmdToRun);
			result = "OK";
		}catch(Exception e) {
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 *  permet de rajouter un ou plusieurs sous-répertoires pour le user
	 *  au niveau du serveur ftp
	 */
	@PostMapping(value = "/addFolder")
	public String addSubFolder(@RequestParam String userName, @RequestParam List<String> folders) {
		
		String result ="KO";
		String finalFolder = "/home/ftpuser/ftp/"+userName;
		if(! folders.isEmpty()) {
			for(String folder : folders) {
				finalFolder = finalFolder+"/"+folder;
			}
		}else {
			return "Erreur: pas de répertoire fourni";
		}
		
		String firstCmdToRun = "mkdir -p "+finalFolder;
		String secondCmdToRun = "sudo chown ftpuser:ftpuser "+finalFolder;
		
		try {
			executeCommande(firstCmdToRun);
			executeCommande(secondCmdToRun);
			result = "OK";
		}catch(Exception e) {
			e.printStackTrace();	
		}
		
		return result;
	}
	
	@PostMapping(value = "/removeFolder")
	public String removeFolder(@RequestParam String userName, @RequestParam List<String> folders) {
		
		String result ="KO";
		String finalFolder = "/home/ftpuser/ftp/"+userName;
		if(! folders.isEmpty()) {
			for(String folder : folders) {
				finalFolder = finalFolder+"/"+folder;
			}
		}else {
			return "Erreur: pas de répertoire fourni";
		}
		
		String firstCmdToRun = "rm -rf "+finalFolder;
		
		try {
			executeCommande(firstCmdToRun);
			result = "OK";
		}catch(Exception e) {
			e.printStackTrace();	
		}
		
		return result;
	}
	
	
	/*
	 *  Permet d'exécuter la commande passer en argument au niveau du serveur linux
	 */
	
	public void executeCommande(String commande) throws IOException {
		processBuilder.command("bash", "-c", commande);
		try {
			Process p = processBuilder.start();
			System.out.println("command executed: "+p.getOutputStream());
		} catch (IOException e) {
			throw(e);
		}

	}

}
