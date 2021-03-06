package tools.multitool;

import core.ServerConstants;
import game.content.commands.NormalCommand;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import tools.EconomyScan;
import utility.Misc;

public class PlayerAltScan {

	static void playerAltScanEnd() {
		RunecessorMultiTool.setStatusText("Accounts scanned: " + RunecessorMultiTool.totalScans + "/" + RunecessorMultiTool.getTotalCharacters());
		EconomyScan.runAltScan();
		ArrayList<String> sort = new ArrayList<String>();
		sort = NormalCommand.sort(RunecessorMultiTool.altData, "#");
		if (EconomyScan.lastTimeDonated > 0) {
			// About the 0.89: On average when it comes to my donations, 11% of the tokens are given for free because of my bonus tokens system.
			long daysAgo = (System.currentTimeMillis() - EconomyScan.lastTimeDonated) / Misc.getHoursToMilliseconds(24);
			String daysAgoText = daysAgo + " days ago";
			if (daysAgo <= 0) {
				daysAgoText = "today";
			}
			daysAgoText = daysAgoText + " on " + EconomyScan.lastDonationName;

			RunecessorMultiTool.updateOutputText("Donated: " + daysAgoText + ".", Color.BLACK, true);
		}
		for (int index = 0; index < sort.size(); index++) {
			String string[] = sort.get(index).split("#");
			RunecessorMultiTool.updateOutputText(string[0], Color.BLACK, true);
		}
		if (sort.isEmpty()) {
			RunecessorMultiTool.updateOutputText("No match found for ip or uid search.", Color.BLACK, true);
		}
	}

	/**
	 * Loop through all character files and find all the alts of the given ip and uid.
	 *
	 * @return
	 */
	static ArrayList<String> grabAltsStage2ScanCharacters(String flaggedIp, String flaggedUid, int alphabetIndex) {
		RunecessorMultiTool.scanThreadOnline = true;
		ArrayList<String> flaggedPlayers = new ArrayList<String>();
		final File folder = new File(ServerConstants.getCharacterLocationWithoutLastSlash());
		int size = RunecessorMultiTool.getTotalCharacters();
		for (final File fileEntry : folder.listFiles()) {
			if (alphabetIndex == -1) {
				if (!Character.isDigit(fileEntry.getName().charAt(0))) {
					break;
				}
			} else {
				if (!fileEntry.getName().toLowerCase().startsWith(RunecessorMultiTool.ALPHABET[alphabetIndex])) {
					continue;
				}
			}
			try {
				String playerName = "";
				String ip = "";
				String uid = "";

				BufferedReader file = new BufferedReader(new FileReader(ServerConstants.getCharacterLocationWithoutLastSlash() + "/" + fileEntry.getName()));
				String originalLine;
				while ((originalLine = file.readLine()) != null) {
					if (originalLine.contains("Username =")) {
						playerName = originalLine.substring(originalLine.indexOf("=") + 2);
					} else if (originalLine.contains("lastSavedIpAddress")) {
						String string = originalLine.substring(originalLine.lastIndexOf("=") + 2);
						if (string.isEmpty()) {
							continue;
						}
						ip = string;
					} else if (originalLine.contains("addressUid")) {
						String string = originalLine.substring(originalLine.lastIndexOf("=") + 2);
						if (string.isEmpty()) {
							continue;
						}
						uid = string;
						RunecessorMultiTool.totalScans++;
						if (RunecessorMultiTool.totalScans % 500 == 0) {
							RunecessorMultiTool.setStatusText("Accounts scanned: " + RunecessorMultiTool.totalScans + "/" + size);
						}
						break;
					}
				}
				file.close();

				if (ip.equals(flaggedIp) && !flaggedIp.isEmpty()) {
					flaggedPlayers.add(playerName.toLowerCase());
					continue;
				}
				if (Misc.uidMatches(flaggedUid, uid)) {
					flaggedPlayers.add(playerName.toLowerCase());
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		RunecessorMultiTool.scanThreadsActive--;
		return flaggedPlayers;
	}

}
