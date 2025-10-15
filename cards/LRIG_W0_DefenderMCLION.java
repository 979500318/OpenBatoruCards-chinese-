package open.batoru.data.cards;

import open.batoru.data.Card;
import open.batoru.data.CardConst.*;

public final class LRIG_W0_DefenderMCLION extends Card {

    public LRIG_W0_DefenderMCLION()
    {
        setImageSets("WXDi-P15-024");

        setOriginalName("防衛者MC.LION");
        setAltNames("ボウエイシャエムシーリオン Boueisha Emu Shii Rion");

        setName("en", "Defender MC LION");
        
        
        setName("en_fan", "Defender MC.LION");

		setName("zh_simplified", "防卫者MC.LION");
        setType(CardType.LRIG);
        setLRIGType(CardLRIGType.LION);
        setLRIGTeam(CardLRIGTeam.CARD_JOCKEY);
        setColor(CardColor.WHITE);

        setPlayFormat(PlayFormat.KEY, PlayFormat.DIVA, PlayFormat.ENGLISH);
    }
}
