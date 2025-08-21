package com.myPackage.controller;

import com.myPackage.Model.Paths;

import java.io.*;
import java.util.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
public class MainController {

	@PostMapping("/align")
	public String alignment(@RequestBody Paths paths) throws IOException {

		String newFilePath = (paths.p2).split(".txt")[0].concat("_byPrittifier.txt");
		FileWriter fw = new FileWriter(newFilePath);
		try {
			Scanner correctTRAreader = new Scanner(new File(paths.p1));

			while (correctTRAreader.hasNextLine()) {
				int isExistInWrongTRA = 0;
				String line1 = correctTRAreader.nextLine();

				// if line is comment so put as it is in new file
				if (line1.startsWith("#")) {
					fw.write(line1);
					fw.write('\n');
				}
				// if line is empty so put as it is in new file
				else if (line1.isEmpty()) {
					fw.write('\n');
				} else {
					String key = line1.split("=")[0];
//					System.out.println(key + " is key");

					// searching the key into wrong TRA
					Scanner wrongTRAreader = new Scanner(new File(paths.p2));
					while (wrongTRAreader.hasNextLine()) {
						String line2 = wrongTRAreader.nextLine();

						// if exist then put whole line into new file
						if (line2.contains(key)) {
							fw.write(line2);
							fw.write('\n');
							isExistInWrongTRA = 1;
							break;
						}
					}
					if (isExistInWrongTRA == 0)
						System.out.println(key + " not found.");
					wrongTRAreader.close();
				}
			}
			correctTRAreader.close();
			fw.close();
			String result = "New file created at location : " + newFilePath;
			return result;
		} catch (FileNotFoundException e) {
			System.out.println("Error in reading correctTRA file");
			e.printStackTrace();
			fw.close();
			File newFile = new File(newFilePath);
			newFile.delete();
			return "New file is not created due to wrong path.";
		}
	}

}
