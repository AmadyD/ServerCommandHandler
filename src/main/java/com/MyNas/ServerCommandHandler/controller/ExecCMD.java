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
	
	@PostMapping(value = "/mkdir")
	public void createDirectory(@RequestParam String directory) {
		processBuilder.command("bash", "-c", "mkdir /home/ftpuser/ftp/"+directory);
		try {
			Process p = processBuilder.start();
			System.out.println("command executed: "+p.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
