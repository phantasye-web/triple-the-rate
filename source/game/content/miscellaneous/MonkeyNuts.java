package game.content.miscellaneous;

import game.item.ItemAssistant;
import game.player.Player;
import utility.Misc;

public class MonkeyNuts{


	/**
	 * The chance of a common item
	 */
	private static final int COMMON = 100;

	/**
	 * The vote book items
	 */
	private final static int[][] MONKEY_LEWT =
			{
					{4012, COMMON, 1}, //rune full helm
			};


	public static void ClaimReward(Player player, int[][] book, int votebook) {
		if (ItemAssistant.getFreeInventorySlots(player) < 2) {
			player.getDH().sendStatement("You need at least two available inventory slots to do this.");
			return;
		}
		int random = Misc.random(book.length - 1);
		int amount = book[random][2];
		if (ItemAssistant.hasItemInInventory(player, 16902));
			if (Misc.random(1, 1) < book[random][1]) {
				ItemAssistant.addItem(player, book[random][0], amount);
				} 
			}


	public static boolean HandleReward(Player player, int book) {

		switch (book) {
			case 16902:
				ClaimReward(player, MONKEY_LEWT, book);
				return true;
		}
		return false;
	}
}
